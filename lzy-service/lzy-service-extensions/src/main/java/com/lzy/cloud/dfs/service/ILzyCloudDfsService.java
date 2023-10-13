package com.lzy.cloud.dfs.service;

import com.lzy.platform.domain.LzyCloudDfsFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * 业务文件上传下载接口实现
 *
 * @author lizuoyang
 * @date 2023/08/03
 */
public interface ILzyCloudDfsService {
    /**
     * 获取文件上传的 token
     * @param dfsCode
     * @return
     */
    String uploadToken(String dfsCode);


    /**
     * 上传文件
     *
     * @param dfsCode
     * @param file
     * @return
     */
    LzyCloudDfsFile uploadFile(String dfsCode, MultipartFile file);

    /**
     * 获取文件访问链接
     * @param dfsCode
     * @param fileName
     * @return
     */
    String getFileUrl(String dfsCode, String fileName);


    /**
     * 下载文件
     * @param dfsCode
     * @param fileName
     * @return
     */
    OutputStream downloadFile(String dfsCode, String fileName, OutputStream outputStream);
}
