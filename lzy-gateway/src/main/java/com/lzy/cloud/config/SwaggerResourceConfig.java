package com.lzy.cloud.config;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 * @ClassName SwaggerResourceConfig
 * @Description swagger 配置
 * @Author LiZuoYang
 * @Date 2022/6/16 14:44
 **/
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = Lists.newArrayList();
        List<String> routes = Lists.newArrayList();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(route -> {
                    route.getPredicates().stream().filter(predicateDefinition -> "Path".equals(predicateDefinition.getName()))
                            .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(), predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                    .replace("**", "v2/api-docs?group=1.X版本"))));
                });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("1.0.0");
        return swaggerResource;
    }
}
