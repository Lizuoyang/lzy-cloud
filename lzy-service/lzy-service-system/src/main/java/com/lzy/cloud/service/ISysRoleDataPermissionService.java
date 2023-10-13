package com.lzy.cloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzy.cloud.dto.CreateRoleDataPermissionDTO;
import com.lzy.cloud.dto.QueryRoleDataPermissionDTO;
import com.lzy.cloud.dto.RoleDataPermissionDTO;
import com.lzy.cloud.dto.UpdateRoleDataPermissionDTO;
import com.lzy.cloud.entity.SysRoleDataPermission;

import java.util.List;

/**
 * <p>
 * 角色和数据权限关联表 服务类
 * </p>
 *
 * @author lzy
 * @since 2023-05-11
 */
public interface ISysRoleDataPermissionService extends IService<SysRoleDataPermission> {
    /**
     * 分页查询角色和数据权限关联表列表
     * @param page
     * @param queryRoleDataPermissionDTO
     * @return
     */
    Page<RoleDataPermissionDTO> queryRoleDataPermissionList(Page<RoleDataPermissionDTO> page, QueryRoleDataPermissionDTO queryRoleDataPermissionDTO);

    /**
     * 查询角色和数据权限关联表详情
     * @param queryRoleDataPermissionDTO
     * @return
     */
    RoleDataPermissionDTO queryRoleDataPermission(QueryRoleDataPermissionDTO queryRoleDataPermissionDTO);

    /**
     * 创建角色和数据权限关联表
     * @param roleDataPermission
     * @return
     */
    boolean createRoleDataPermission(CreateRoleDataPermissionDTO roleDataPermission);

    /**
     * 更新角色和数据权限关联表
     * @param roleDataPermission
     * @return
     */
    boolean updateRoleDataPermission(UpdateRoleDataPermissionDTO roleDataPermission);

    /**
     * 删除角色和数据权限关联表
     * @param roleDataPermissionId
     * @return
     */
    boolean deleteRoleDataPermission(Long roleDataPermissionId);

    /**
     * 批量删除角色和数据权限关联表
     * @param roleDataPermissionIds
     * @return
     */
    boolean batchDeleteRoleDataPermission(List<Long> roleDataPermissionIds);

    /**
     * 批量更新数据权限和角色的关系
     * @param updateRoleDataPermissionDTO
     * @return
     */
    boolean updateDataPermissionRoleList(UpdateRoleDataPermissionDTO updateRoleDataPermissionDTO);
}
