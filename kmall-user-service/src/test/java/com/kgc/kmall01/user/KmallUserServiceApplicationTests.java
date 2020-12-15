package com.kgc.kmall01.user;

import com.kgc.kmall01.bean.Member;
import com.kgc.kmall01.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class KmallUserServiceApplicationTests {

	@Resource
	MemberService memberService;

	@Test
	void contextLoads() {
        List<Member> members = memberService.selAll();
        for (Member member : members) {
            System.out.println(member);
        }
    }

}
