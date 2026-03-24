package com.hmall.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // 获取请求信息
                ServerHttpRequest request = exchange.getRequest();
                // 处理过滤业务逻辑
                System.out.println("PrintAnyGatewayFilterFactory 执行了");
                // 放行
                return chain.filter(exchange);
                //拦截 返回401状态码
                //exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                //return exchange.getResponse().setComplete();
            }
        };
    }
}