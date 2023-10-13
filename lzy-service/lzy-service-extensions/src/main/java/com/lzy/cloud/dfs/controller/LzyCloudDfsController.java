package com.lzy.cloud.dfs.controller;

import com.lzy.cloud.dfs.service.ILzyCloudDfsService;
import com.lzy.platform.base.exception.BusinessException;
import com.lzy.platform.base.result.Result;
import com.lzy.platform.domain.LzyCloudDfsFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分布式存储上传 前端控制器
 * </p>
 *
 * @author lzy
 * @since 2023/8/3 13:45
 */
@RestController
@RequestMapping("/extension")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(value = "LzyCloudDfsController|文件上传前端控制器", tags = {"文件上传"})
@RefreshScope
public class LzyCloudDfsController {
    private final ILzyCloudDfsService lzyCloudDfsService;

    /**
     * 上传文件
     * @param uploadFile
     * @param dfsCode
     * @return
     */
    @PostMapping("/upload/file")
    @ApiOperation("上传文件")
    public Result<?> uploadFile(@RequestParam("uploadFile") MultipartFile[] uploadFile, String dfsCode) {
        List<LzyCloudDfsFile> lzyCloudDfsFiles = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(uploadFile))
        {
            for (MultipartFile file : uploadFile) {
                LzyCloudDfsFile lzyCloudDfsFile = lzyCloudDfsService.uploadFile(dfsCode, file);
                // 查询文件访问链接
                String fileUrl = lzyCloudDfsService.getFileUrl(dfsCode, lzyCloudDfsFile.getEncodedFileName());
                lzyCloudDfsFile.setFileUrl(fileUrl);
                lzyCloudDfsFiles.add(lzyCloudDfsFile);
            }
        }
        else
        {
            throw new BusinessException("没有选择上传文件");
        }
        return Result.data(lzyCloudDfsFiles);
    }
}
