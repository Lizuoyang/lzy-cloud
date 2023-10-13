package com.lzy.cloud.dfs.factory;

import com.aliyun.oss.OSSClient;
import com.lzy.cloud.dfs.dto.DfsDTO;
import com.lzy.platform.props.AliyunDfsProperties;
import com.lzy.platform.service.IDfsBaseService;
import com.lzy.platform.service.impl.AliyunDfsServiceImpl;

/**
 * 阿里云OSS上传服务接口工厂类
 */
public class DfsAliyunFactory {

    public static IDfsBaseService getDfsBaseService(DfsDTO dfsDTO) {
        AliyunDfsProperties aliyunDfsProperties = new AliyunDfsProperties();
        aliyunDfsProperties.setAccessKey(dfsDTO.getAccessKey());
        aliyunDfsProperties.setSecretKey(dfsDTO.getSecretKey());
        aliyunDfsProperties.setRegion(dfsDTO.getRegion());
        aliyunDfsProperties.setBucket(dfsDTO.getBucket());
        aliyunDfsProperties.setUploadUrl(dfsDTO.getUploadUrl());
        aliyunDfsProperties.setAccessUrlPrefix(dfsDTO.getAccessUrlPrefix());
        aliyunDfsProperties.setAccessControl(dfsDTO.getAccessControl());
        OSSClient ossClient = new OSSClient(aliyunDfsProperties.getUploadUrl(), aliyunDfsProperties.getAccessKey(), aliyunDfsProperties.getSecretKey());
        return new AliyunDfsServiceImpl(ossClient, aliyunDfsProperties);
    }

}
