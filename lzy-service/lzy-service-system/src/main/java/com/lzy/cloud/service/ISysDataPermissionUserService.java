package com.lzy.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzy.cloud.dto.UpdateDataPermissionUserDTO;
import com.lzy.cloud.entity.SysDataPermissionUser;

/**
 * <p>
 * 数据权限多部门 服务类
 * </p>
 *
 * @author lzy
 * @since 2023-05-10
 */
public interface ISysDataPermissionUserService extends IService<SysDataPermissionUser> {
    boolean updateUserOrganizationDataPermission(UpdateDataPermissionUserDTO updateDataPermission);
}
