package com.lzy.cloud.dfs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzy.cloud.dfs.dto.CreateDfsFileDTO;
import com.lzy.cloud.dfs.dto.DfsFileDTO;
import com.lzy.cloud.dfs.dto.QueryDfsFileDTO;
import com.lzy.cloud.dfs.dto.UpdateDfsFileDTO;
import com.lzy.cloud.dfs.entity.SysDfsFile;

import java.util.List;

/**
 * <p>
 * 分布式存储文件记录表 服务类
 * </p>
 *
 * @author lizuoyang
 * @since 2023-08-03
 */
public interface IDfsFileService extends IService<SysDfsFile> {
    /**
     * 分页查询分布式存储文件记录表列表
     * @param page
     * @param queryDfsFileDTO
     * @return
     */
    Page<DfsFileDTO> queryDfsFileList(Page<DfsFileDTO> page, QueryDfsFileDTO queryDfsFileDTO);

    /**
     * 查询分布式存储文件记录表详情
     * @param queryDfsFileDTO
     * @return
     */
    DfsFileDTO queryDfsFile(QueryDfsFileDTO queryDfsFileDTO);

    /**
     * 创建分布式存储文件记录表
     * @param dfsFile
     * @return
     */
    boolean createDfsFile(CreateDfsFileDTO dfsFile);

    /**
     * 更新分布式存储文件记录表
     * @param dfsFile
     * @return
     */
    boolean updateDfsFile(UpdateDfsFileDTO dfsFile);

    /**
     * 删除分布式存储文件记录表
     * @param dfsFileId
     * @return
     */
    boolean deleteDfsFile(Long dfsFileId);

    /**
     * 批量删除分布式存储文件记录表
     * @param dfsFileIds
     * @return
     */
    boolean batchDeleteDfsFile(List<Long> dfsFileIds);
}
