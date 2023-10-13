package com.lzy.cloud.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.dto.CreateDataPermissionRuleDTO;
import com.lzy.cloud.dto.DataPermissionRuleDTO;
import com.lzy.cloud.dto.QueryDataPermissionRuleDTO;
import com.lzy.cloud.dto.UpdateDataPermissionRuleDTO;
import com.lzy.cloud.entity.SysDataPermissionRule;
import com.lzy.cloud.mapper.SysDataPermissionRuleMapper;
import com.lzy.cloud.service.ISysDataPermissionRuleService;
import com.lzy.platform.base.exception.BusinessException;
import com.lzy.platform.base.utils.BeanCopierUtils;
import com.lzy.platform.mybatis.constant.DataPermissionConstant;
import com.lzy.platform.mybatis.entity.DataPermissionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 数据权限配置表 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2023-05-11
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysDataPermissionRuleServiceImpl extends ServiceImpl<SysDataPermissionRuleMapper, SysDataPermissionRule> implements ISysDataPermissionRuleService {
    private final SysDataPermissionRuleMapper dataPermissionRuleMapper;

    private final RedisTemplate redisTemplate;

    /**
     * 分页查询数据权限配置表列表
     * @param page
     * @param queryDataPermissionRoleDTO
     * @return
     */
    @Override
    public Page<DataPermissionRuleDTO> queryDataPermissionRoleList(Page<DataPermissionRuleDTO> page, QueryDataPermissionRuleDTO queryDataPermissionRoleDTO) {
        Page<DataPermissionRuleDTO> dataPermissionRoleInfoList = dataPermissionRuleMapper.queryDataPermissionRoleList(page, queryDataPermissionRoleDTO);
        return dataPermissionRoleInfoList;
    }

    /**
     * 查询数据权限配置表详情
     * @param queryDataPermissionRoleDTO
     * @return
     */
    @Override
    public DataPermissionRuleDTO queryDataPermissionRole(QueryDataPermissionRuleDTO queryDataPermissionRoleDTO) {
        DataPermissionRuleDTO dataPermissionRoleDTO = dataPermissionRuleMapper.queryDataPermissionRole(queryDataPermissionRoleDTO);
        return dataPermissionRoleDTO;
    }

    /**
     * 创建数据权限配置表
     * @param dataPermissionRole
     * @return
     */
    @Override
    public boolean createDataPermissionRole(CreateDataPermissionRuleDTO dataPermissionRole) {
        SysDataPermissionRule dataPermissionRoleEntity = BeanCopierUtils.copyByClass(dataPermissionRole, SysDataPermissionRule.class);
        boolean result = this.save(dataPermissionRoleEntity);
        if (result)
        {
            // 新增到缓存
            SysDataPermissionRule dataPermissionRoleCache = this.getById(dataPermissionRoleEntity.getId());
            this.addOrUpdateDataRolePermissionsCache(dataPermissionRoleCache);
        }
        return result;
    }

    /**
     * 更新数据权限配置表
     * @param dataPermissionRole
     * @return
     */
    @Override
    public boolean updateDataPermissionRole(UpdateDataPermissionRuleDTO dataPermissionRole) {
        SysDataPermissionRule dataPermissionRoleEntity = BeanCopierUtils.copyByClass(dataPermissionRole, SysDataPermissionRule.class);

        // 更新到缓存
        SysDataPermissionRule dataPermissionRoleCache = this.getById(dataPermissionRoleEntity.getId());
        this.addOrUpdateDataRolePermissionsCache(dataPermissionRoleCache);

        boolean result = this.updateById(dataPermissionRoleEntity);
        return result;
    }

    /**
     * 删除数据权限配置表
     * @param dataPermissionRoleId
     * @return
     */
    @Override
    public boolean deleteDataPermissionRole(Long dataPermissionRoleId) {
        // 更新到缓存
        SysDataPermissionRule dataPermissionRoleCache = this.getById(dataPermissionRoleId);
        this.deleteDataRolePermissionsCache(dataPermissionRoleCache);
        boolean result = this.removeById(dataPermissionRoleId);
        return result;
    }

    /**
     * 批量删除数据权限配置表
     * @param dataPermissionRoleIds
     * @return
     */
    @Override
    public boolean batchDeleteDataPermissionRole(List<Long> dataPermissionRoleIds) {
        List<SysDataPermissionRule> dataPermissionRoleCaches = this.listByIds(dataPermissionRoleIds);
        this.batchDeleteDataRolePermissionsCache(dataPermissionRoleCaches);
        boolean result = this.removeByIds(dataPermissionRoleIds);
        return result;
    }

    @Override
    public void initDataRolePermissions() {
        List<DataPermissionRuleDTO> dataPermissionRoleList = dataPermissionRuleMapper.queryDataPermissionRoleListAll();
        redisTemplate.delete(DataPermissionConstant.DATA_PERMISSION_KEY);
        // auth:data:permission
        addDataRolePermissions(DataPermissionConstant.DATA_PERMISSION_KEY, dataPermissionRoleList);
    }

    private void addDataRolePermissions(String key, List<DataPermissionRuleDTO> dataPermissionRoleList) {
        Map<String, DataPermissionEntity> dataPermissionMap = new TreeMap<>();
        Optional.ofNullable(dataPermissionRoleList).orElse(new ArrayList<>()).forEach(dataPermissionRole -> {
            String dataRolePermissionCache = new StringBuffer(DataPermissionConstant.DATA_PERMISSION_KEY_MAPPER)
                    .append(dataPermissionRole.getDataMapperFunction()).append(DataPermissionConstant.DATA_PERMISSION_KEY_TYPE)
                    .append(dataPermissionRole.getDataPermissionType()).toString();
            DataPermissionEntity dataPermissionEntity = BeanCopierUtils.copyByClass(dataPermissionRole, DataPermissionEntity.class);
            dataPermissionMap.put(dataRolePermissionCache, dataPermissionEntity);
        });
        redisTemplate.boundHashOps(key).putAll(dataPermissionMap);
    }

    private void addOrUpdateDataRolePermissionsCache(SysDataPermissionRule dataPermissionRule) {
        try {
            DataPermissionEntity dataPermissionEntity = BeanCopierUtils.copyByClass(dataPermissionRule, DataPermissionEntity.class);
            // 获取redis的map
            String dataPermissionKey = this.genDataPermissionMapKey(dataPermissionRule);
            // 获取redis的map中字段的key
            String mappedStatementIdKey = this.genDataPermissionEntityKey(dataPermissionRule);
            redisTemplate.boundHashOps(dataPermissionKey).put(mappedStatementIdKey, dataPermissionEntity);
        } catch (Exception e) {
            log.error("修改数据权限配置缓存失败：{}" , e);
            throw new BusinessException("修改数据权限配置缓存失败：" , e);
        }
    }

    private void deleteDataRolePermissionsCache(SysDataPermissionRule dataPermissionRule) {
        try {
            // 获取redis的map
            String dataPermissionKey = this.genDataPermissionMapKey(dataPermissionRule);
            // 获取redis的map中字段的key
            String mappedStatementIdKey = this.genDataPermissionEntityKey(dataPermissionRule);
            redisTemplate.boundHashOps(dataPermissionKey).delete(mappedStatementIdKey);
        } catch (Exception e) {
            log.error("删除数据权限配置缓存失败：{}" , e);
            throw new BusinessException("删除数据权限配置缓存失败：" , e);
        }
    }

    private void batchDeleteDataRolePermissionsCache(List<SysDataPermissionRule> dataPermissionRules) {
        for (SysDataPermissionRule dataPermissionRule : dataPermissionRules)
        {
            this.deleteDataRolePermissionsCache(dataPermissionRule);
        }
    }

    private String genDataPermissionMapKey(SysDataPermissionRule dataPermissionRule)
    {
        // 1 根据系统配置的数据权限拼装sql
        StringBuffer statementSb = new StringBuffer();
        statementSb.append(DataPermissionConstant.DATA_PERMISSION_KEY);
        // 获取redis的map
        return statementSb.toString();
    }

    private String genDataPermissionEntityKey(SysDataPermissionRule dataPermissionRule)
    {
        // 获取redis的map中字段的key
        StringBuffer statementSbt = new StringBuffer(DataPermissionConstant.DATA_PERMISSION_KEY_MAPPER);
        statementSbt.append(dataPermissionRule.getDataMapperFunction()).append(DataPermissionConstant.DATA_PERMISSION_KEY_TYPE).append(dataPermissionRule.getDataPermissionType());
        return statementSbt.toString();
    }
}
