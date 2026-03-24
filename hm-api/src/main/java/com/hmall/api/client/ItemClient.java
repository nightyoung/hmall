package com.hmall.api.client;



import com.hmall.api.config.DefaultFeignConfig;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import com.hmall.api.fallback.ItemClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient(value = "item-service", path = "/items",
        fallbackFactory = ItemClientFallbackFactory.class
        ,configuration = DefaultFeignConfig.class)

public interface ItemClient {
    @GetMapping()
    List<ItemDTO> queryItemByIds(@RequestParam("ids") Set<Long> itemIds);

    @PutMapping("/stock/deduct")
    public void deductStock(@RequestBody List<OrderDetailDTO> items);

}
