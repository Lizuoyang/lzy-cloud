package com.lzy.cloud.dfs.factory;

import com.lzy.cloud.dfs.dto.DfsDTO;
import com.lzy.platform.props.QiniuDfsProperties;
import com.lzy.platform.service.IDfsBaseService;
import com.lzy.platform.service.impl.QiniuDfsServiceImpl;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛云上传服务接口工厂类
 */
public class DfsQiniuFactory {

    public static IDfsBaseService getDfsBaseService(DfsDTO dfsDTO) {
        Auth auth = Auth.create(dfsDTO.getAccessKey(), dfsDTO.getSecretKey());
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        QiniuDfsProperties qiNiuDfsProperties = new QiniuDfsProperties();
        qiNiuDfsProperties.setAccessKey(dfsDTO.getAccessKey());
        qiNiuDfsProperties.setSecretKey(dfsDTO.getSecretKey());
        qiNiuDfsProperties.setRegion(dfsDTO.getRegion());
        qiNiuDfsProperties.setBucket(dfsDTO.getBucket());
        qiNiuDfsProperties.setUploadUrl(dfsDTO.getUploadUrl());
        qiNiuDfsProperties.setAccessUrlPrefix(dfsDTO.getAccessUrlPrefix());
        qiNiuDfsProperties.setAccessControl(dfsDTO.getAccessControl());
        return new QiniuDfsServiceImpl(auth, uploadManager, bucketManager, qiNiuDfsProperties);
    }
}
