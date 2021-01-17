package com.kgc.kmall01.user.service;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall01.bean.Member;
import com.kgc.kmall01.bean.MemberExample;
import com.kgc.kmall01.bean.MemberReceiveAddress;
import com.kgc.kmall01.bean.MemberReceiveAddressExample;
import com.kgc.kmall01.service.MemberService;
import com.kgc.kmall01.user.mapper.MemberMapper;
import com.kgc.kmall01.user.mapper.MemberReceiveAddressMapper;
import com.kgc.kmall01.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-15 19:40
 */
@Component
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberMapper memberMapper;
    @Resource
    RedisUtils redisUtils;
    @Resource
    MemberReceiveAddressMapper memberReceiveAddressMapper;



    @Override
    public List<Member> selectAllMember() {
        return memberMapper.selectByExample(null);
    }

    @Override
    public void addUserToken(String token, Long id) {
        Jedis jedis = redisUtils.getJedis();

        jedis.setex("user:"+id+":token",60*60*2,token);

        jedis.close();
    }

    @Override
    public Member login(Member member) {
        Jedis jedis = null;
        try {
            jedis = redisUtils.getJedis();
            if(jedis!=null){
                String s = jedis.get("user:" + member.getUsername() + ":info");
                if(StringUtils.isNotBlank(s)){
                    Member member1 = JSON.parseObject(s, Member.class);
                    return member1;
                }
            }
            // 链接redis失败，开启数据库
            Member umsMemberFromDb =loginFromDb(member);
            if(umsMemberFromDb!=null){
                jedis.setex("user:" + umsMemberFromDb.getId() + ":info",60*60*24, JSON.toJSONString(umsMemberFromDb));
            }
            return umsMemberFromDb;
        }finally {
            jedis.close();
        }
    }


    @Override
    public Member checkOauthUser(Long sourceUid) {
        MemberExample example = new MemberExample();
        MemberExample.Criteria criteria = example.createCriteria();
        criteria.andSourceUidEqualTo(sourceUid);
        List<Member> members = memberMapper.selectByExample(example);
        if(members!=null&&members.size()>0){
            return members.get(0);
        }
        return null;
    }

    @Override
    public void addOauthUser(Member umsMember) {
        memberMapper.insertSelective(umsMember);
    }

    @Override
    public List<MemberReceiveAddress> getReceiveAddressByMemberId(Long memberId) {
        MemberReceiveAddressExample example = new MemberReceiveAddressExample();
        MemberReceiveAddressExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        List<MemberReceiveAddress> memberReceiveAddresses = memberReceiveAddressMapper.selectByExample(example);
        return memberReceiveAddresses;
    }


    private Member loginFromDb(Member member) {
        MemberExample example = new MemberExample();
        MemberExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(member.getUsername());
        criteria.andPasswordEqualTo(member.getPassword());
        List<Member> members = memberMapper.selectByExample(example);
        if (members.size()>0) {
            return members.get(0);
        }
        return null;
    }
}
