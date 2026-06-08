package com.car.rental.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.car.rental.common.Result;
import com.car.rental.entity.Store;
import com.car.rental.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * C端 - 门店控制器
 */
@RestController
@RequestMapping("/client/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 获取门店列表（分页）
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Store> page = new Page<>(pageNum, pageSize);
        IPage<Store> result = storeService.page(page);

        Map<String, Object> data = new HashMap<>();
        data.put("records", result.getRecords());
        data.put("total", result.getTotal());
        data.put("current", result.getCurrent());
        data.put("size", result.getSize());

        return Result.success(data);
    }

    /**
     * 根据城市编码查询门店列表
     */
    @GetMapping("/list/{cityCode}")
    public Result<List<Store>> listByCityCode(@PathVariable String cityCode) {
        List<Store> list = storeService.listByCityCode(cityCode);
        return Result.success(list);
    }

    /**
     * 获取门店详情
     */
    @GetMapping("/{id}")
    public Result<Store> detail(@PathVariable Long id) {
        Store store = storeService.getDetail(id);
        return Result.success(store);
    }
}
