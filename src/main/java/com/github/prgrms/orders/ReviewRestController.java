package com.github.prgrms.orders;

import com.github.prgrms.utils.ApiUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.github.prgrms.utils.ApiUtils.success;

@RestController
@RequestMapping("api/orders")
public class ReviewRestController {

    private final OrdersService ordersService;

    public ReviewRestController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping(path = "{id}/review")
    public ApiUtils.ApiResult<ReviewDto> review(@PathVariable Long id, @RequestBody Map<String, String> content) {
        return success(ordersService.review(id, content.get("content")));
    }

}