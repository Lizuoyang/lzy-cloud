package com.lzy.cloud.dfs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dfs.dto.DfsDTO;
import com.lzy.cloud.dfs.dto.QueryDfsDTO;
import com.lzy.cloud.dfs.entity.SysDfs;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 分布式存储配置表 Mapper 接口
 * </p>
 *
 * @author lizuoyang
 * @since 2023-08-03
 */
public interface DfsMapper extends BaseMapper<SysDfs> {
    /**
     * 查询分布式存储配置表列表
     * @param page
     * @param dfsDTO
     * @return
     */
    Page<DfsDTO> queryDfsList(Page<DfsDTO> page, @Param("dfs") QueryDfsDTO dfsDTO);

    /**
     * 查询分布式存储配置表信息
     * @param dfsDTO
     * @return
     */
    DfsDTO queryDfs(@Param("dfs") QueryDfsDTO dfsDTO);

    /**
     * 查询默认配置
     * @return
     */
    DfsDTO queryDefaultDfs(@Param("dfs") QueryDfsDTO dfsDTO);
}
