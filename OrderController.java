package com.car.rental.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.car.rental.common.Result;
import com.car.rental.dto.CreateOrderDTO;
import com.car.rental.entity.Order;
import com.car.rental.entity.Payment;
import com.car.rental.service.OrderService;
import com.car.rental.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * C端 - 订单控制器
 */
@RestController
@RequestMapping("/client/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<Order> createOrder(@RequestBody CreateOrderDTO dto) {
        Order order = orderService.createOrder(dto);
        return Result.success("订单创建成功", order);
    }

    /**
     * 计算价格
     */
    @GetMapping("/calculate")
    public Result<BigDecimal> calculatePrice(
            @RequestParam Long carId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        BigDecimal price = orderService.calculatePrice(carId,
                startTime.toString(), endTime.toString());
        return Result.success(price);
    }

    /**
     * 订单详情
     */
    @GetMapping("/{orderNo}")
    public Result<Order> detail(@PathVariable String orderNo) {
        Order order = orderService.getDetail(orderNo);
        return Result.success(order);
    }

    /**
     * 我的订单列表
     */
    @GetMapping("/list")
    public Result<IPage<Order>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        IPage<Order> page = orderService.getUserOrders(pageNum, pageSize, status);
        return Result.success(page);
    }

    /**
     * 取消订单
     */
    @PostMapping("/{orderNo}/cancel")
    public Result<String> cancel(@PathVariable String orderNo,
                                @RequestParam(required = false) String reason) {
        orderService.cancelOrder(orderNo, reason);
        return Result.success("订单已取消");
    }

    /**
     * 创建支付
     */
    @PostMapping("/payment/create")
    public Result<String> createPayment(@RequestParam String orderNo) {
        String paymentNo = paymentService.createPayment(orderNo);
        return Result.success("支付创建成功", paymentNo);
    }

    /**
     * 模拟支付成功（回调接口）
     */
    @PostMapping("/payment/mock-callback")
    public Result<String> mockPaymentCallback(@RequestParam String paymentNo) {
        paymentService.handlePaymentCallback(paymentNo);
        return Result.success("支付成功");
    }

    /**
     * 查询支付状态
     */
    @GetMapping("/payment/status/{paymentNo}")
    public Result<Payment> paymentStatus(@PathVariable String paymentNo) {
        Payment payment = paymentService.getPaymentStatus(paymentNo);
        return Result.success(payment);
    }
}
