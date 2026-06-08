package com.car.rental.controller.client;

import cn.dev33.satoken.stp.StpUtil;
import com.car.rental.common.Result;
import com.car.rental.dto.LoginDTO;
import com.car.rental.dto.RegisterDTO;
import com.car.rental.entity.User;
import com.car.rental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * C端 - 用户认证控制器
 */
@RestController
@RequestMapping("/client/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 发送验证码
     */
    @PostMapping("/sms")
    public Result<String> sendSms(@RequestParam String phone) {
        // 模拟发送验证码
        // 实际项目需要调用短信服务发送验证码
        return Result.success();
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody @Validated RegisterDTO dto) {
        userService.register(dto);
        return Result.success("注册成功");
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated LoginDTO dto) {
        String token = userService.login(dto);
        return Result.success("登录成功", token);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        StpUtil.logout();
        return Result.success("退出成功");
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/user/info")
    public Result<User> getUserInfo() {
        User user = userService.getCurrentUser();
        return Result.success(user);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/user/info")
    public Result<String> updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return Result.success("更新成功");
    }
}
