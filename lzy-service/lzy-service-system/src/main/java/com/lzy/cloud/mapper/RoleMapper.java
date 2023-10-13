package com.lzy.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.cloud.dto.QueryRoleDTO;
import com.lzy.cloud.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色列表
     * @param page
     * @param queryRoleDTO
     * @return
     */
    Page<Role> selectRoleList(Page<Role> page, @Param("role") QueryRoleDTO queryRoleDTO);
}
