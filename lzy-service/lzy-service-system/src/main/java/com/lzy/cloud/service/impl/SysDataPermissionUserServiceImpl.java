package com.lzy.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.dto.UpdateDataPermissionUserDTO;
import com.lzy.cloud.entity.SysDataPermissionUser;
import com.lzy.cloud.mapper.SysDataPermissionUserMapper;
import com.lzy.cloud.service.ISysDataPermissionUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据权限多部门 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2023-05-10
 */
@Service
public class SysDataPermissionUserServiceImpl extends ServiceImpl<SysDataPermissionUserMapper, SysDataPermissionUser> implements ISysDataPermissionUserService {

    @Override
    public boolean updateUserOrganizationDataPermission(UpdateDataPermissionUserDTO updateDataPermission) {
        boolean result = false;
        Long userId = updateDataPermission.getUserId();

        List<Long> removeDataPermissions = updateDataPermission.getRemoveDataPermissions();
        if (!CollectionUtils.isEmpty(removeDataPermissions) && null != userId)
        {
            LambdaQueryWrapper<SysDataPermissionUser> removeWrapper = SysDataPermissionUser.lqw();
            removeWrapper.eq(SysDataPermissionUser::getUserId, userId).in(SysDataPermissionUser::getOrganizationId, removeDataPermissions);
            result = this.remove(removeWrapper);
        }

        List<Long> addDataPermissions = updateDataPermission.getAddDataPermissions();
        if (!CollectionUtils.isEmpty(addDataPermissions) && null != userId)
        {
            List<SysDataPermissionUser> dataPermissionList = new ArrayList<>();
            for (Long dataId: addDataPermissions)
            {
                SysDataPermissionUser dataPermission = new SysDataPermissionUser();
                dataPermission.setOrganizationId(dataId);
                dataPermission.setUserId(userId);
                dataPermissionList.add(dataPermission);
            }
            result = this.saveBatch(dataPermissionList);
        }
        return result;
    }
}
