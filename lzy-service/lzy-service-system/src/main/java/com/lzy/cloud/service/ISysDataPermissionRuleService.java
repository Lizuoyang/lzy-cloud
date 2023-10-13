package com.lzy.cloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzy.cloud.dto.CreateDataPermissionRuleDTO;
import com.lzy.cloud.dto.DataPermissionRuleDTO;
import com.lzy.cloud.dto.QueryDataPermissionRuleDTO;
import com.lzy.cloud.dto.UpdateDataPermissionRuleDTO;
import com.lzy.cloud.entity.SysDataPermissionRule;

import java.util.List;

/**
 * <p>
 * 数据权限配置表 服务类
 * </p>
 *
 * @author lzy
 * @since 2023-05-11
 */
public interface ISysDataPermissionRuleService extends IService<SysDataPermissionRule> {
    /**
     * 分页查询数据权限配置表列表
     * @param page
     * @param queryDataPermissionRoleDTO
     * @return
     */
    Page<DataPermissionRuleDTO> queryDataPermissionRoleList(Page<DataPermissionRuleDTO> page, QueryDataPermissionRuleDTO queryDataPermissionRoleDTO);

    /**
     * 查询数据权限配置表详情
     * @param queryDataPermissionRoleDTO
     * @return
     */
    DataPermissionRuleDTO queryDataPermissionRole(QueryDataPermissionRuleDTO queryDataPermissionRoleDTO);

    /**
     * 创建数据权限配置表
     * @param dataPermissionRole
     * @return
     */
    boolean createDataPermissionRole(CreateDataPermissionRuleDTO dataPermissionRole);

    /**
     * 更新数据权限配置表
     * @param dataPermissionRole
     * @return
     */
    boolean updateDataPermissionRole(UpdateDataPermissionRuleDTO dataPermissionRole);

    /**
     * 删除数据权限配置表
     * @param dataPermissionRoleId
     * @return
     */
    boolean deleteDataPermissionRole(Long dataPermissionRoleId);

    /**
     * 批量删除数据权限配置表
     * @param dataPermissionRoleIds
     * @return
     */
    boolean batchDeleteDataPermissionRole(List<Long> dataPermissionRoleIds);

    /**
     * 初始化系统数据权限权限
     */
    void initDataRolePermissions();
}
