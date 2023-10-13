package com.lzy.cloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.cloud.dto.UpdateRoleResourceDTO;
import com.lzy.cloud.entity.Resource;
import com.lzy.cloud.entity.Role;
import com.lzy.cloud.entity.RoleResource;
import com.lzy.cloud.mapper.RoleResourceMapper;
import com.lzy.cloud.service.IResourceService;
import com.lzy.cloud.service.IRoleResourceService;
import com.lzy.cloud.service.IRoleService;
import com.lzy.platform.base.constant.AuthConstant;
import com.lzy.platform.base.constant.LzyCloudConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource>
        implements IRoleResourceService {

    private final IResourceService resourceService;

    private final RedisTemplate redisTemplate;

    private IRoleService roleService;

    @Autowired
    public void setFieldService(@Lazy IRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<Resource> queryResourceByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleResource> ew = RoleResource.lqw();
        ew.eq(RoleResource::getRoleId, roleId);
        List<RoleResource> roleResourceList = this.list(ew);
        if (!CollectionUtils.isEmpty(roleResourceList)) {
            List<Long> resourceIds = new ArrayList<>();
            for (RoleResource roleResource : roleResourceList) {
                resourceIds.add(roleResource.getResourceId());
            }
            List<Resource> resourceList = resourceService.listByIds(resourceIds);
            return resourceList;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateList(UpdateRoleResourceDTO updateRoleResource) {
        List<RoleResource> addList = updateRoleResource.getAddResources();
        if (!CollectionUtils.isEmpty(addList)) {
            this.saveBatch(addList);
            this.genRoleResources(addList, true);
        }
        List<RoleResource> delList = updateRoleResource.getDelResources();
        if (!CollectionUtils.isEmpty(delList)) {
            List<Long> resIdList = new ArrayList<>();
            for (RoleResource rr : delList) {
                resIdList.add(rr.getResourceId());
            }
            Long roleId = updateRoleResource.getRoleId();
            LambdaQueryWrapper<RoleResource> ewResource = RoleResource.lqw();
            ewResource.eq(RoleResource::getRoleId, roleId).in(RoleResource::getResourceId, resIdList);
            this.remove(ewResource);
            this.genRoleResources(delList, false);
        }
        return true;
    }

    /**
     * 执行角色权限初始化
     */
    @Override
    public void initResourceRoles() {
        // 查询系统角色和权限的关系
        List<Resource> resourceList = resourceService.queryResourceRoles();
        redisTemplate.delete(AuthConstant.RESOURCE_ROLES_KEY);
        addRoleResource(AuthConstant.RESOURCE_ROLES_KEY, resourceList);
    }

    private void addRoleResource(String key, List<Resource> resourceList) {
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        Optional.ofNullable(resourceList).orElse(new ArrayList<>()).forEach(resource -> {
            // roleKey -> ROLE_{roleKey}
            List<String> roleKeys = Optional.ofNullable(resource.getRoles()).orElse(new ArrayList<>()).stream().map(Role::getRoleKey)
                    .distinct().map(roleKey -> AuthConstant.AUTHORITY_PREFIX + roleKey).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(roleKeys)) {
                resourceRolesMap.put(resource.getResourceUrl(), roleKeys);
            }
        });
        redisTemplate.opsForHash().putAll(key, resourceRolesMap);
    }

    /**
     * 更新资源权限的缓存
     */
    @Override
    public void updateResourceRoles(Resource oldResource, Resource newResource) {
        String redisRoleKey = AuthConstant.RESOURCE_ROLES_KEY;
        // 缓存取资源权限角色关系列表
        Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(redisRoleKey);
        List<String> roleKeys = (List<String>) resourceRolesMap.get(oldResource.getResourceUrl());
        if (ObjectUtil.isNotNull(newResource.getResourceUrl())) {
            resourceRolesMap.put(newResource.getResourceUrl(), roleKeys);
            resourceRolesMap.remove(oldResource.getResourceUrl());
            redisTemplate.opsForHash().putAll(redisRoleKey, resourceRolesMap);
        }
    }

    /**
     * 删除资源权限的缓存
     */
    @Override
    public void removeResourceRoles(Resource resource) {
        String redisRoleKey = AuthConstant.RESOURCE_ROLES_KEY;
        // 缓存取资源权限角色关系列表
        Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(redisRoleKey);
        resourceRolesMap.remove(resource.getResourceUrl());
        redisTemplate.opsForHash().putAll(redisRoleKey, resourceRolesMap);
    }

    /**
     * 批量删除资源权限的缓存
     */
    @Override
    public void removeBatchResourceRoles(List<Resource> resources) {
        if (!CollectionUtils.isEmpty(resources))
        {
            String redisRoleKey = AuthConstant.RESOURCE_ROLES_KEY;
            // 缓存取资源权限角色关系列表
            Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(redisRoleKey);
            for (Resource resource: resources)
            {
                resourceRolesMap.remove(resource.getResourceUrl());
            }
            redisTemplate.opsForHash().putAll(redisRoleKey, resourceRolesMap);
        }
    }

    /**
     * 批量删除资源权限的角色缓存
     */
    @Override
    public void removeBatchRoles(List<Role> roles) {
        if (!CollectionUtils.isEmpty(roles))
        {
            String redisRoleKey = AuthConstant.RESOURCE_ROLES_KEY;
            // 缓存取资源权限角色关系列表
            Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(redisRoleKey);
            Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
            List<String> roleKeyList = roles.stream().map(Role::getRoleKey)
                    .distinct().map(roleKey -> AuthConstant.AUTHORITY_PREFIX + roleKey).collect(Collectors.toList());
            while (iterator.hasNext()) {
                String resourceUrl = (String) iterator.next();
                List<String> roleKeys = (List<String>) resourceRolesMap.get(resourceUrl);
                roleKeys.removeAll(roleKeyList);
                resourceRolesMap.put(resourceUrl, roleKeys);
            }
            redisTemplate.opsForHash().putAll(redisRoleKey, resourceRolesMap);
        }
    }

    /**
     * 新增或删除角色的权限缓存
     */
    @Override
    public void genRoleResources(List<RoleResource> roleResourceList, boolean addFlag) {
        String redisRoleKey = AuthConstant.RESOURCE_ROLES_KEY;
        List<Long> resourceIdList = roleResourceList.stream().map(RoleResource::getResourceId).collect(Collectors.toList());
        List<Resource> resources = resourceService.listByIds(resourceIdList);

        Long roleId = roleResourceList.get(LzyCloudConstant.Number.ZERO).getRoleId();
        Role role = roleService.getById(roleId);
        String roleKey = AuthConstant.AUTHORITY_PREFIX + role.getRoleKey();

        if (!CollectionUtils.isEmpty(resources))
        {
            // 缓存取资源权限角色关系列表
            Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(redisRoleKey);

            for (Resource resource : resources)
            {
                List<String> roleKeys = (List<String>) resourceRolesMap.get(resource.getResourceUrl());
                if (CollectionUtils.isEmpty(roleKeys))
                {
                    roleKeys = new ArrayList<>();
                }
                // 新增数据权限
                if (addFlag && !roleKeys.contains(roleKey))
                {
                    roleKeys.add(roleKey);
                    resourceRolesMap.put(resource.getResourceUrl(), roleKeys);
                }
                // 删除数据权限
                else if (!addFlag && roleKeys.contains(roleKey))
                {
                    roleKeys.remove(roleKey);
                    resourceRolesMap.put(resource.getResourceUrl(), roleKeys);
                }
            }
            redisTemplate.opsForHash().putAll(redisRoleKey, resourceRolesMap);
        }
    }
}
