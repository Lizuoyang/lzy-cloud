package com.lzy.cloud.handler;

import cn.hutool.json.JSONUtil;
import com.lzy.platform.base.enums.ResultCodeEnum;
import com.lzy.platform.base.result.Result;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * 无效token/token过期 自定义响应
 *
 * @author 2428
 * @date 2022/06/23
 */
@Component
public class AuthServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String body = JSONUtil.toJsonStr(Result.error(ResultCodeEnum.UNAUTHORIZED));

        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer));
    }

}

