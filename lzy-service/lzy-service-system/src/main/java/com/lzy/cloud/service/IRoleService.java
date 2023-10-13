package com.lzy.cloud.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzy.cloud.dto.CreateRoleDTO;
import com.lzy.cloud.dto.QueryRoleDTO;
import com.lzy.cloud.dto.UpdateRoleDTO;
import com.lzy.cloud.entity.Role;

import java.util.List;

/**
 * @ClassName: IRoleService
 * @Description: 角色相关操作接口
 */
public interface IRoleService extends IService<Role> {

    /**
     * 分页查询角色列表
     * @param page
     * @param queryRoleDTO
     * @return
     */
    Page<Role> selectRoleList(Page<Role> page, QueryRoleDTO queryRoleDTO);

    /**
     * 创建角色
     * @param role
     * @return
     */
    boolean createRole(CreateRoleDTO role);

    /**
     * 更新角色
     * @param role
     * @return
     */
    boolean updateRole(UpdateRoleDTO role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    boolean deleteRole(Long roleId);

    /**
     * 批量删除角色
     * @param roleIds
     * @return
     */
    boolean batchDeleteRole(List<Long> roleIds);
}
