package com.hmall.api.fallback;

import com.hmall.api.client.ItemClient;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {
    @Override
    public ItemClient create(Throwable cause) {
        return new ItemClient() {
            @Override
            public List<ItemDTO> queryItemByIds(Set<Long> ids) { // 将 Collection 改为 Set
                log.error("远程调用ItemClient#queryItemByIds方法出现异常，参数：{}", ids, cause);
                cause.printStackTrace();
                return List.of();
            }
            public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
                log.error("远程调用ItemClient#queryItemByIds方法出现异常，参数：{}", ids, cause);
                cause.printStackTrace();
                return List.of();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                log.error("远程调用ItemClient#deductStock扣减库存失败,参数:{}",items,cause);
            }
        };
    }
}