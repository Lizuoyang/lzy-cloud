package com.lzy.cloud.components;

import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import com.lzy.platform.generator.utils.GeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 初始化代码生成配置类
 * 项目运行成功后，访问ip:8068即可
 * </p>
 *
 * @author lzy
 * @since 2023/5/8 10:02
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class InitGeneratorConfigRunner implements CommandLineRunner {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String passWord;
    @Override
    public void run(String... args) throws Exception {
        String basePackage = "com.lzy.cloud";
        String schemaName = "lzy_cloud";
        GeneratorConfig generatorConfig = GeneratorUtil.getGeneratorConfig(jdbcUrl, driverClassName, userName, passWord, schemaName, basePackage, null);
        MybatisPlusToolsApplication.run(generatorConfig);
    }
}
