package com.lzy.cloud.service.impl;

import com.lzy.cloud.entity.SysOrganizationRole;
import com.lzy.cloud.mapper.SysOrganizationroleMapper;
import com.lzy.cloud.service.ISysOrganizationroleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 可以给组织权限，在该组织下的所有用户都有此权限 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2023-05-08
 */
@Service
public class SysOrganizationroleServiceImpl extends ServiceImpl<SysOrganizationroleMapper, SysOrganizationRole> implements ISysOrganizationroleService {

}
