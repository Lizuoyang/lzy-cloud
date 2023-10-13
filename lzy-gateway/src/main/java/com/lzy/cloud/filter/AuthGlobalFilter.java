package com.lzy.cloud.filter;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.lzy.platform.base.constant.AuthConstant;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @ClassName AuthGlobalFilter
 * @Description 将登录用户的JWT转化成用户信息的全局过滤器
 * @Author LiZuoYang
 * @Date 2022/6/17 16:38
 **/
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);

        Map<String, String> addHeaders = Maps.newHashMap();

        if (StrUtil.isNotEmpty(token) && token.startsWith(AuthConstant.JWT_TOKEN_PREFIX)) {
            try {
                //从token中解析用户信息并设置到Header中去
                String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
                JWSObject jwsObject = JWSObject.parse(realToken);
                String userStr = jwsObject.getPayload().toString();
                log.info("AuthGlobalFilter.filter() User:{}", userStr);
                addHeaders.put(AuthConstant.HEADER_USER, URLEncoder.encode(userStr, "UTF-8"));

            } catch (ParseException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            addHeaders.forEach((k, v) -> {
                httpHeader.set(k, v);
            });
        };

        ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders).build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
