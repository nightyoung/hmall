package com.hmall.cart;

import com.hmall.api.config.DefaultFeignConfig;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
/*
docker run --name seata \
-p 8099:8099 \
-p 7099:7099 \
-e SEATA_IP=192.168.100.129 \
-v ./seata:/seata-server/resources \
--privileged=true \
--network hm-net \
-d \
seataio/seata-server:1.5.2



* */

@SpringBootApplication(scanBasePackages = {"com.hmall.cart","com.hmall.api"})
@EnableFeignClients(basePackages = "com.hmall.api.client",defaultConfiguration = DefaultFeignConfig.class)
@MapperScan("com.hmall.cart.mapper")
public class CartApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(CartApplication.class, args);
    }

}
