package com.lzy.cloud.dfs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dfs.dto.DfsFileDTO;
import com.lzy.cloud.dfs.dto.QueryDfsFileDTO;
import com.lzy.cloud.dfs.entity.SysDfsFile;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 分布式存储文件记录表 Mapper 接口
 * </p>
 *
 * @author lizuoyang
 * @since 2023-08-03
 */
public interface DfsFileMapper extends BaseMapper<SysDfsFile> {
    /**
     * 查询分布式存储文件记录表列表
     * @param page
     * @param dfsFileDTO
     * @return
     */
    Page<DfsFileDTO> queryDfsFileList(Page<DfsFileDTO> page, @Param("dfsFile") QueryDfsFileDTO dfsFileDTO);

    /**
     * 查询分布式存储文件记录表信息
     * @param dfsFileDTO
     * @return
     */
    DfsFileDTO queryDfsFile(@Param("dfsFile") QueryDfsFileDTO dfsFileDTO);
}
