package com.lzy.cloud.dfs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.dfs.dto.CreateDfsFileDTO;
import com.lzy.cloud.dfs.dto.DfsFileDTO;
import com.lzy.cloud.dfs.dto.QueryDfsFileDTO;
import com.lzy.cloud.dfs.dto.UpdateDfsFileDTO;
import com.lzy.cloud.dfs.entity.SysDfsFile;
import com.lzy.cloud.dfs.mapper.DfsFileMapper;
import com.lzy.cloud.dfs.service.IDfsFileService;
import com.lzy.platform.base.utils.BeanCopierUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分布式存储文件记录表 服务实现类
 * </p>
 *
 * @author lizuoyang
 * @since 2023-08-03
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DfsFileServiceImpl extends ServiceImpl<DfsFileMapper, SysDfsFile> implements IDfsFileService {
    private final DfsFileMapper dfsFileMapper;

    /**
     * 分页查询分布式存储文件记录表列表
     * @param page
     * @param queryDfsFileDTO
     * @return
     */
    @Override
    public Page<DfsFileDTO> queryDfsFileList(Page<DfsFileDTO> page, QueryDfsFileDTO queryDfsFileDTO) {
        Page<DfsFileDTO> dfsFileInfoList = dfsFileMapper.queryDfsFileList(page, queryDfsFileDTO);
        return dfsFileInfoList;
    }

    /**
     * 查询分布式存储文件记录表详情
     * @param queryDfsFileDTO
     * @return
     */
    @Override
    public DfsFileDTO queryDfsFile(QueryDfsFileDTO queryDfsFileDTO) {
        DfsFileDTO dfsFileDTO = dfsFileMapper.queryDfsFile(queryDfsFileDTO);
        return dfsFileDTO;
    }

    /**
     * 创建分布式存储文件记录表
     * @param dfsFile
     * @return
     */
    @Override
    public boolean createDfsFile(CreateDfsFileDTO dfsFile) {
        SysDfsFile dfsFileEntity = BeanCopierUtils.copyByClass(dfsFile, SysDfsFile.class);
        boolean result = this.save(dfsFileEntity);
        return result;
    }

    /**
     * 更新分布式存储文件记录表
     * @param dfsFile
     * @return
     */
    @Override
    public boolean updateDfsFile(UpdateDfsFileDTO dfsFile) {
        SysDfsFile dfsFileEntity = BeanCopierUtils.copyByClass(dfsFile, SysDfsFile.class);
        LambdaQueryWrapper<SysDfsFile> lqw = SysDfsFile.lqw();
        lqw.eq(SysDfsFile::getId, dfsFileEntity.getId());
        boolean result = this.update(dfsFileEntity, lqw);
        return result;
    }

    /**
     * 删除分布式存储文件记录表
     * @param dfsFileId
     * @return
     */
    @Override
    public boolean deleteDfsFile(Long dfsFileId) {
        boolean result = this.removeById(dfsFileId);
        return result;
    }

    /**
     * 批量删除分布式存储文件记录表
     * @param dfsFileIds
     * @return
     */
    @Override
    public boolean batchDeleteDfsFile(List<Long> dfsFileIds) {
        boolean result = this.removeByIds(dfsFileIds);
        return result;
    }
}
