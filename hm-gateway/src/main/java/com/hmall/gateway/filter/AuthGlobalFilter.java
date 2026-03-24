package com.hmall.gateway.filter;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;
    private final JwtTool jwtTool;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求路径
        String path = exchange.getRequest().getPath().toString();

        // 2.判断是否在白名单内；使用 antPathMatcher 进行匹配；如果在则放行
        //获取白名单
        List<String> excludePaths = authProperties.getExcludePaths();
        //使用 antPathMatcher 进行匹配；如果在则放行
        for (String excludePath : excludePaths) {
            if (antPathMatcher.match(excludePath, path)) {
                return chain.filter(exchange);
            }
        }

        // 3.判断请求头是否包含authorization得到token令牌，包含则通过jwt校验，通过则放行，否则返回401
        String token = exchange.getRequest().getHeaders().getFirst("authorization");
        if (StrUtil.isBlank(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //校验令牌
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);

        } catch (Exception e) {
            //令牌无效
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 4.传递用户信息 TODO 暂时先输出
        exchange.getRequest().mutate().header("user-info", userId.toString());
        System.out.println("userId = " + userId);

        // 5.放行
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}