package com.lzy.cloud.dfs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.dfs.dto.CreateDfsDTO;
import com.lzy.cloud.dfs.dto.DfsDTO;
import com.lzy.cloud.dfs.dto.QueryDfsDTO;
import com.lzy.cloud.dfs.dto.UpdateDfsDTO;
import com.lzy.cloud.dfs.entity.SysDfs;
import com.lzy.cloud.dfs.mapper.DfsMapper;
import com.lzy.cloud.dfs.service.IDfsService;
import com.lzy.platform.base.constant.LzyCloudConstant;
import com.lzy.platform.base.utils.BeanCopierUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分布式存储配置表 服务实现类
 * </p>
 *
 * @author lizuoyang
 * @since 2023-08-03
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DfsServiceImpl extends ServiceImpl<DfsMapper, SysDfs> implements IDfsService {
    private final DfsMapper dfsMapper;

    @Override
    public Page<DfsDTO> queryDfsList(Page<DfsDTO> page, QueryDfsDTO queryDfsDTO) {
        Page<DfsDTO> dfsInfoList = dfsMapper.queryDfsList(page, queryDfsDTO);
        return dfsInfoList;
    }

    /**
     * 查询分布式存储配置表详情
     * @param queryDfsDTO
     * @return
     */
    @Override
    public DfsDTO queryDfs(QueryDfsDTO queryDfsDTO) {
        DfsDTO dfsDTO = dfsMapper.queryDfs(queryDfsDTO);
        return dfsDTO;
    }

    /**
     * 创建分布式存储配置表
     * @param dfs
     * @return
     */
    @Override
    public boolean createDfs(CreateDfsDTO dfs) {
        SysDfs dfsEntity = BeanCopierUtils.copyByClass(dfs, SysDfs.class);
        boolean result = this.save(dfsEntity);
        return result;
    }

    /**
     * 更新分布式存储配置表
     * @param dfs
     * @return
     */
    @Override
    public boolean updateDfs(UpdateDfsDTO dfs) {
        SysDfs dfsEntity = BeanCopierUtils.copyByClass(dfs, SysDfs.class);
        LambdaQueryWrapper<SysDfs> lqw = SysDfs.lqw();
        lqw.eq(SysDfs::getId, dfsEntity.getId());
        boolean result = this.update(dfsEntity, lqw);
        return result;
    }

    /**
     * 更新分布式存储配置默认
     * @param dfsId
     * @return
     */
    @Override
    public boolean updateDfsDefault(Long dfsId) {
        //将选择的记录改为默认，其他值改为非默认
        LambdaUpdateWrapper<SysDfs> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysDfs::getId, dfsId).set(SysDfs::getDfsDefault, LzyCloudConstant.ENABLE);
        boolean result = this.update(lambdaUpdateWrapper);
        if (result) {
            SysDfs dfs = this.getById(dfsId);
            LambdaQueryWrapper<SysDfs> lambdaQueryWrapperNe = new LambdaQueryWrapper<>();
            lambdaQueryWrapperNe.ne(SysDfs::getId, dfsId).eq(SysDfs::getAccessControl, dfs.getAccessControl());

            LambdaUpdateWrapper<SysDfs> lambdaUpdateWrapperNe = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapperNe.ne(SysDfs::getId, dfsId).eq(SysDfs::getAccessControl, dfs.getAccessControl()).set(SysDfs::getDfsDefault, LzyCloudConstant.DISABLE);
            // 防止只有一条数据报错
            if (this.count(lambdaQueryWrapperNe) > 0) {
                result = this.update(lambdaUpdateWrapperNe);
            }

        }
        return result;
    }

    /**
     * 删除分布式存储配置表
     * @param dfsId
     * @return
     */
    @Override
    public boolean deleteDfs(Long dfsId) {
        boolean result = this.removeById(dfsId);
        return result;
    }

    /**
     * 批量删除分布式存储配置表
     * @param dfsIds
     * @return
     */
    @Override
    public boolean batchDeleteDfs(List<Long> dfsIds) {
        boolean result = this.removeByIds(dfsIds);
        return result;
    }

    /**
     * 查询默认配置
     * @return
     */
    @Override
    public DfsDTO queryDefaultDfs(QueryDfsDTO queryDfsDTO) {
        DfsDTO dfsDTO = dfsMapper.queryDefaultDfs(queryDfsDTO);
        return dfsDTO;
    }
}
