package com.lzy.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.dto.CreateRoleDataPermissionDTO;
import com.lzy.cloud.dto.QueryRoleDataPermissionDTO;
import com.lzy.cloud.dto.RoleDataPermissionDTO;
import com.lzy.cloud.dto.UpdateRoleDataPermissionDTO;
import com.lzy.cloud.entity.SysRoleDataPermission;
import com.lzy.cloud.mapper.SysRoleDataPermissionMapper;
import com.lzy.cloud.service.ISysRoleDataPermissionService;
import com.lzy.platform.base.utils.BeanCopierUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色和数据权限关联表 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2023-05-11
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysRoleDataPermissionServiceImpl extends ServiceImpl<SysRoleDataPermissionMapper, SysRoleDataPermission> implements ISysRoleDataPermissionService {
    private final SysRoleDataPermissionMapper roleDataPermissionMapper;

    /**
     * 分页查询角色和数据权限关联表列表
     * @param page
     * @param queryRoleDataPermissionDTO
     * @return
     */
    @Override
    public Page<RoleDataPermissionDTO> queryRoleDataPermissionList(Page<RoleDataPermissionDTO> page, QueryRoleDataPermissionDTO queryRoleDataPermissionDTO) {
        Page<RoleDataPermissionDTO> roleDataPermissionInfoList = roleDataPermissionMapper.queryRoleDataPermissionList(page, queryRoleDataPermissionDTO);
        return roleDataPermissionInfoList;
    }

    /**
     * 查询角色和数据权限关联表详情
     * @param queryRoleDataPermissionDTO
     * @return
     */
    @Override
    public RoleDataPermissionDTO queryRoleDataPermission(QueryRoleDataPermissionDTO queryRoleDataPermissionDTO) {
        RoleDataPermissionDTO roleDataPermissionDTO = roleDataPermissionMapper.queryRoleDataPermission(queryRoleDataPermissionDTO);
        return roleDataPermissionDTO;
    }

    /**
     * 创建角色和数据权限关联表
     * @param roleDataPermission
     * @return
     */
    @Override
    public boolean createRoleDataPermission(CreateRoleDataPermissionDTO roleDataPermission) {
        SysRoleDataPermission roleDataPermissionEntity = BeanCopierUtils.copyByClass(roleDataPermission, SysRoleDataPermission.class);
        boolean result = this.save(roleDataPermissionEntity);
        return result;
    }

    /**
     * 更新角色和数据权限关联表
     * @param roleDataPermission
     * @return
     */
    @Override
    public boolean updateRoleDataPermission(UpdateRoleDataPermissionDTO roleDataPermission) {
        SysRoleDataPermission roleDataPermissionEntity = BeanCopierUtils.copyByClass(roleDataPermission, SysRoleDataPermission.class);
        boolean result = this.updateById(roleDataPermissionEntity);
        return result;
    }

    /**
     * 删除角色和数据权限关联表
     * @param roleDataPermissionId
     * @return
     */
    @Override
    public boolean deleteRoleDataPermission(Long roleDataPermissionId) {
        boolean result = this.removeById(roleDataPermissionId);
        return result;
    }

    /**
     * 批量删除角色和数据权限关联表
     * @param roleDataPermissionIds
     * @return
     */
    @Override
    public boolean batchDeleteRoleDataPermission(List<Long> roleDataPermissionIds) {
        boolean result = this.removeByIds(roleDataPermissionIds);
        return result;
    }


    @Override
    public boolean updateDataPermissionRoleList(UpdateRoleDataPermissionDTO updateRoleDataPermissionDTO) {
        List<SysRoleDataPermission> addList = updateRoleDataPermissionDTO.getAddRoles();
        if (!CollectionUtils.isEmpty(addList)) {
            this.saveBatch(addList);
        }
        List<SysRoleDataPermission> delList = updateRoleDataPermissionDTO.getDelRoles();
        if (!CollectionUtils.isEmpty(delList)) {
            List<Long> roleIdList = new ArrayList<>();
            for (SysRoleDataPermission roleDataPermission : delList) {
                roleIdList.add(roleDataPermission.getRoleId());
            }
            Long dataPermissionId = updateRoleDataPermissionDTO.getDataPermissionId();
            QueryWrapper<SysRoleDataPermission> ewResource = SysRoleDataPermission.qw();
            ewResource.eq("data_permission_id", dataPermissionId).in("role_id", roleIdList);
            this.remove(ewResource);
        }
        return true;
    }
}
