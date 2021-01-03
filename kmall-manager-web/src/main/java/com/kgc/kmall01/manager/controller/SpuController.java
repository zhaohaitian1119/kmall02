package com.kgc.kmall01.manager.controller;

import com.kgc.kmall01.bean.PmsBaseSaleAttr;
import com.kgc.kmall01.bean.PmsProductImage;
import com.kgc.kmall01.bean.PmsProductInfo;
import com.kgc.kmall01.bean.PmsProductSaleAttr;
import com.kgc.kmall01.service.SpuService;
import org.apache.commons.io.FilenameUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 18:55
 */
@RestController
@CrossOrigin
public class SpuController {

    @Value("${fileServer.url}")
    String fileUrl;

    @Reference
    SpuService spuService;

    @RequestMapping("/spuList")
    public List<PmsProductInfo> spuList(Long catalog3Id){
        List<PmsProductInfo> infoList = spuService.spuList(catalog3Id);
        return infoList;
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file")MultipartFile file){
        try {
            //文件上传
            //返回文件上传后的路径
            String confFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(confFile);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            StorageClient storageClient=new StorageClient(trackerServer,null);

            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            String[] upload_file = storageClient.upload_file(file.getBytes(), extension, null);
            String path=fileUrl;
            for (int i = 0; i < upload_file.length; i++) {
                String s = upload_file[i];
                System.out.println("s = " + s);
                path+="/"+s;
            }
            System.out.println(path);
            return path;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> saleAttrList = spuService.baseSaleAttrList();
        return saleAttrList;
    }

    @RequestMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        Integer integer = spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }


    @RequestMapping("/spuSaleAttrList")
    public List<PmsProductSaleAttr> spuSaleAttrList(Long spuId){
        List<PmsProductSaleAttr> pmsProductSaleAttrList=spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrList;
    }



    @RequestMapping("/spuImageList")
    public List<PmsProductImage> spuImageList(Long spuId){
        List<PmsProductImage> pmsProductImageList = spuService.spuImageList(spuId);
        return pmsProductImageList;
    }
}
