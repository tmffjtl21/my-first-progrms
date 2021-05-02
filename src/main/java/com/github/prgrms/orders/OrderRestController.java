package com.github.prgrms.orders;

import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.utils.ApiUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.prgrms.utils.ApiUtils.success;

@RestController
@RequestMapping("api/orders")
public class OrderRestController {

    private final OrdersService ordersService;

    public OrderRestController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public ApiUtils.ApiResult<List<OrdersDto>> findAll(@RequestParam(value = "offset", defaultValue = "0", required = false) Long offset,
                                                       @RequestParam(value = "size", defaultValue = "5", required = false) Long size) {
        if(offset < 0) offset = 0L;
        if(size < 1) size = 1L;
        if(size > 5) size = 5L;

        return success(ordersService.findAll(offset, size).stream()
                .map(OrdersDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "{id}")
    public ApiUtils.ApiResult<OrdersDto> findById(@PathVariable Long id) {
        return success(ordersService.findById(id)
                .map(OrdersDto::new)
                .orElseThrow(() -> new NotFoundException("Could not found product for " + id)));
    }

    @PatchMapping(path = "{id}/accept")
    public ApiUtils.ApiResult<Boolean> accept(@PathVariable Long id) {
        return success(ordersService.accept(id));
    }

    @PatchMapping(path = "{id}/reject")
    public ApiUtils.ApiResult<Boolean> reject(@PathVariable Long id, @RequestBody Map<String, String> message) {
        return success(ordersService.reject(id, message.get("message")));
    }

    @PatchMapping(path = "{id}/shipping")
    public ApiUtils.ApiResult<Boolean> shipping(@PathVariable Long id) {
        return success(ordersService.shipping(id));
    }

    @PatchMapping(path = "{id}/complete")
    public ApiUtils.ApiResult<Boolean> complete(@PathVariable Long id) {
        return success(ordersService.complete(id));
    }
}