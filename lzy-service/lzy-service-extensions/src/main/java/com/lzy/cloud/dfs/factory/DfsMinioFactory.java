package com.lzy.cloud.dfs.factory;

import com.lzy.cloud.dfs.dto.DfsDTO;
import com.lzy.platform.props.MinioDfsProperties;
import com.lzy.platform.service.IDfsBaseService;
import com.lzy.platform.service.impl.MinioDfsServiceImpl;
import io.minio.MinioClient;

/**
 * MINIO上传服务接口工厂类
 */
public class DfsMinioFactory {

    public static IDfsBaseService getDfsBaseService(DfsDTO dfsDTO) {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(dfsDTO.getUploadUrl())
                        .credentials(dfsDTO.getAccessKey(), dfsDTO.getSecretKey()).build();;
        MinioDfsProperties minioDfsProperties = new MinioDfsProperties();
        minioDfsProperties.setAccessKey(dfsDTO.getAccessKey());
        minioDfsProperties.setSecretKey(dfsDTO.getSecretKey());
        minioDfsProperties.setRegion(dfsDTO.getRegion());
        minioDfsProperties.setBucket(dfsDTO.getBucket());
        minioDfsProperties.setUploadUrl(dfsDTO.getUploadUrl());
        minioDfsProperties.setAccessUrlPrefix(dfsDTO.getAccessUrlPrefix());
        minioDfsProperties.setAccessControl(dfsDTO.getAccessControl());

        return new MinioDfsServiceImpl(minioClient, minioDfsProperties);
    }
}
