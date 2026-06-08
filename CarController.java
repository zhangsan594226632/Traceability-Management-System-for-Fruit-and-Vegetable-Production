package com.car.rental.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.car.rental.common.Result;
import com.car.rental.dto.CarQueryDTO;
import com.car.rental.entity.Car;
import com.car.rental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * C端 - 车辆控制器
 */
@RestController
@RequestMapping("/client/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    /**
     * 车辆列表（分页）
     */
    @GetMapping("/list")
    public Result<IPage<Car>> list(CarQueryDTO dto) {
        // C端只查询可租车辆
        dto.setStatus(1);
        IPage<Car> page = carService.queryPage(dto);
        return Result.success(page);
    }

    /**
     * 车辆详情
     */
    @GetMapping("/{id}")
    public Result<Car> detail(@PathVariable Long id) {
        Car car = carService.getDetail(id);
        return Result.success(car);
    }

    /**
     * 搜索车辆
     */
    @GetMapping("/search")
    public Result<IPage<Car>> search(CarQueryDTO dto) {
        dto.setStatus(1);
        IPage<Car> page = carService.queryPage(dto);
        return Result.success(page);
    }

    /**
     * 获取可租车辆（指定门店）
     */
    @GetMapping("/available")
    public Result<IPage<Car>> getAvailableCars(@RequestParam(required = false) Long storeId,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        CarQueryDTO dto = new CarQueryDTO();
        dto.setStoreId(storeId);
        dto.setStatus(1);
        dto.setPageNum(pageNum);
        dto.setPageSize(pageSize);
        IPage<Car> page = carService.queryPage(dto);
        return Result.success(page);
    }
}
