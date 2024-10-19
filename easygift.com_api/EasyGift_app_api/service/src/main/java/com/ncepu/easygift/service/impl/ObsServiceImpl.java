package com.ncepu.easygift.service.impl;

import com.ncepu.easygift.prop.ObsProperties;
import com.ncepu.easygift.service.ObsService;
import com.obs.services.ObsClient;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class ObsServiceImpl implements ObsService {

    @Autowired
    private ObsProperties obsProperties;

    @Override
    public String upload(MultipartFile file) {
        // 填写一些必要的信息
        String endPoint = obsProperties.getEndpoint();
        String ak = obsProperties.getAccessKey();
        String sk = obsProperties.getSecretKey();
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        try {
            // 获取文件流
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            // 根据年月日创建一个文件夹，方便管理图片信息
            String fileName = new DateTime().toString("yyyy/MM/dd") + UUID.randomUUID().toString().replace("-", "") + originalFilename;
            // 使用文件流的方式上传 -- 创建PutObject请求
            obsClient.putObject(obsProperties.getBucketName(), fileName, inputStream);
            // 返回图片地址进行保存
            // https://xianzeng.obs.cn-north-4.myhuaweicloud.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20220908100029.jpg
            System.out.println("https://" + obsProperties.getBucketName() + "." + obsProperties.getEndpoint() + "/" + fileName);
            return "https://" + obsProperties.getBucketName() + "." + obsProperties.getEndpoint() + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if(obsClient != null) {
                try {
                    obsClient.close(); // 关闭资源
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
