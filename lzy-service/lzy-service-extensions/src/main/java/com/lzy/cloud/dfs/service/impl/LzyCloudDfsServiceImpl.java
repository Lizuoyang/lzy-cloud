package com.lzy.cloud.dfs.service.impl;

import com.lzy.cloud.dfs.dto.DfsDTO;
import com.lzy.cloud.dfs.dto.QueryDfsDTO;
import com.lzy.cloud.dfs.entity.SysDfsFile;
import com.lzy.cloud.dfs.factory.DfsFactory;
import com.lzy.cloud.dfs.service.IDfsFileService;
import com.lzy.cloud.dfs.service.IDfsService;
import com.lzy.cloud.dfs.service.ILzyCloudDfsService;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.domain.LzyCloudDfsFile;
import com.lzy.platform.service.IDfsBaseService;
import com.qiniu.util.Etag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * <p>
 * 分布式文件存储 接口实现类
 * </p>
 *
 * @author lzy
 * @since 2023/8/3 13:49
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LzyCloudDfsServiceImpl implements ILzyCloudDfsService {
    private final DfsFactory dfsFactory;
    private final IDfsFileService dfsFileService;
    private final IDfsService dfsService;

    @Override
    public String uploadToken(String dfsCode) {
        return null;
    }

    @Override
    public LzyCloudDfsFile uploadFile(String dfsCode, MultipartFile file) {
        QueryDfsDTO queryDfsDTO = new QueryDfsDTO();
        DfsDTO dfsDTO = null;

        // 如果上传时没有选择存储方式，那么取默认存储方式
        if(StringUtils.isEmpty(dfsCode)) {
            queryDfsDTO.setDfsDefault(LzyCloudConstant.ENABLE);
        }
        else {
            queryDfsDTO.setDfsCode(dfsCode);
        }

        LzyCloudDfsFile lzyCloudDfsFile = null;
        SysDfsFile dfsFile = new SysDfsFile();


        try {
            dfsDTO = dfsService.queryDfs(queryDfsDTO);
            IDfsBaseService dfsBaseService = dfsFactory.getDfsBaseService(dfsDTO);

            // 获取文件信息
            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            String hash = Etag.stream(file.getInputStream(), file.getSize());
            String fileName = hash + "." + extension;

            // 保存文件上传记录
            dfsFile.setDfsId(dfsDTO.getId());
            dfsFile.setOriginalName(originalFilename);
            dfsFile.setFileName(fileName);
            dfsFile.setFileExtension(extension);
            dfsFile.setFileSize(file.getSize());
            dfsFile.setFileStatus(LzyCloudConstant.ENABLE);

            //执行文件上传操作
            lzyCloudDfsFile = dfsBaseService.uploadFile(file.getInputStream(), fileName);

            if (lzyCloudDfsFile != null)
            {
                lzyCloudDfsFile.setFileName(originalFilename);
                lzyCloudDfsFile.setKey(hash);
                lzyCloudDfsFile.setHash(hash);
                lzyCloudDfsFile.setFileSize(file.getSize());
            }

            dfsFile.setAccessUrl(lzyCloudDfsFile.getFileUrl());
        } catch (Exception e) {
            log.error("文件上传失败：{}", e);
            dfsFile.setFileStatus(LzyCloudConstant.DISABLE);
            dfsFile.setComments(String.valueOf(e));
        } finally {
            dfsFileService.save(dfsFile);
        }

        return null;
    }

    @Override
    public String getFileUrl(String dfsCode, String fileName) {
        String fileUrl = null;

        QueryDfsDTO queryDfsDTO = new QueryDfsDTO();
        DfsDTO dfsDTO = null;
        // 如果上传时没有选择存储方式，那么取默认存储方式
        if(StringUtils.isEmpty(dfsCode)) {
            queryDfsDTO.setDfsDefault(LzyCloudConstant.ENABLE);
        }
        else {
            queryDfsDTO.setDfsCode(dfsCode);
        }

        try {
            dfsDTO = dfsService.queryDfs(queryDfsDTO);
            IDfsBaseService dfsBaseService = dfsFactory.getDfsBaseService(dfsDTO);
            fileUrl = dfsBaseService.getFileUrl(fileName);
        }
        catch (Exception e)
        {
            log.error("获取文件url失败：{}", e);
        }
        return fileUrl;
    }

    @Override
    public OutputStream downloadFile(String dfsCode, String fileName, OutputStream outputStream) {
        QueryDfsDTO queryDfsDTO = new QueryDfsDTO();
        DfsDTO dfsDTO = null;
        // 如果上传时没有选择存储方式，那么取默认存储方式
        if(StringUtils.isEmpty(dfsCode)) {
            queryDfsDTO.setDfsDefault(LzyCloudConstant.ENABLE);
        }
        else {
            queryDfsDTO.setDfsCode(dfsCode);
        }

        try {
            dfsDTO = dfsService.queryDfs(queryDfsDTO);
            IDfsBaseService dfsBaseService = dfsFactory.getDfsBaseService(dfsDTO);
            outputStream = dfsBaseService.getFileObject(fileName, outputStream);
        }
        catch (Exception e)
        {
            log.error("文件下载失败：{}", e);
        }
        return outputStream;
    }
}
