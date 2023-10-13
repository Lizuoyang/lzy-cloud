package com.lzy.cloud.component;

import com.lzy.cloud.service.IRoleResourceService;
import com.lzy.cloud.service.ISysDataPermissionRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName InitResourceRolesCacheRunner
 * @Description 容器启动完成加载资源权限数据到缓存
 * @Author LiZuoYang
 * @Date 2022/6/22 16:57
 **/
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class InitSystemCacheRunner implements CommandLineRunner {
    private final IRoleResourceService roleResourceService;

    private final ISysDataPermissionRuleService dataPermissionRuleService;

    @Override
    public void run(String... args) throws Exception {
        log.info("InitSystemCacheRunner running");

        // 初始化系统权限和角色的关系缓存
        roleResourceService.initResourceRoles();

        // 初始化数据权限缓存
        dataPermissionRuleService.initDataRolePermissions();
    }
}
