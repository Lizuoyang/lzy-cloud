package com.lzy.cloud.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dto.DataPermissionRuleDTO;
import com.lzy.cloud.dto.QueryDataPermissionRuleDTO;
import com.lzy.cloud.entity.SysDataPermissionRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据权限配置表 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2023-05-11
 */
public interface SysDataPermissionRuleMapper extends BaseMapper<SysDataPermissionRule> {
    /**
     * 查询数据权限配置表列表
     * @param page
     * @param dataPermissionRoleDTO
     * @return
     */
    Page<DataPermissionRuleDTO> queryDataPermissionRoleList(Page<DataPermissionRuleDTO> page, @Param("dataPermissionRole") QueryDataPermissionRuleDTO dataPermissionRoleDTO);

    /**
     * 查询数据权限配置表信息
     * @param dataPermissionRoleDTO
     * @return
     */
    DataPermissionRuleDTO queryDataPermissionRole(@Param("dataPermissionRole") QueryDataPermissionRuleDTO dataPermissionRoleDTO);

    /**
     * 初始化数据权限缓存, 添加InterceptorIgnore注解，初始化时忽略租户拦截
     * @return
     */
    @InterceptorIgnore(tenantLine = "true")
    List<DataPermissionRuleDTO> queryDataPermissionRoleListAll();
}
