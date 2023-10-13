package com.lzy.cloud.components;

import com.lzy.cloud.service.IDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 容器启动完成加载基础数据到缓存
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class InitBaseCacheRunner implements CommandLineRunner {

    /**
     * 系统数据字典表DictConstant
     */
    private final IDictService dictService;

    @Override
    public void run(String... args) {

        log.info("InitBaseCacheRunner running");

        // 初始化系统数据字典
        dictService.initDictConfig();

    }
}

