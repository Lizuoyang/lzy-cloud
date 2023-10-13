package com.lzy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dto.QueryRoleDataPermissionDTO;
import com.lzy.cloud.dto.RoleDataPermissionDTO;
import com.lzy.cloud.entity.SysRoleDataPermission;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色和数据权限关联表 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2023-05-11
 */
public interface SysRoleDataPermissionMapper extends BaseMapper<SysRoleDataPermission> {
    /**
     * 查询角色和数据权限关联表列表
     * @param page
     * @param roleDataPermissionDTO
     * @return
     */
    Page<RoleDataPermissionDTO> queryRoleDataPermissionList(Page<RoleDataPermissionDTO> page, @Param("roleDataPermission") QueryRoleDataPermissionDTO roleDataPermissionDTO);

    /**
     * 查询角色和数据权限关联表信息
     * @param roleDataPermissionDTO
     * @return
     */
    RoleDataPermissionDTO queryRoleDataPermission(@Param("roleDataPermission") QueryRoleDataPermissionDTO roleDataPermissionDTO);
}
