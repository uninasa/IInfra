package cn.uninasa.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.uninasa.auth.service.AuthService;
import cn.uninasa.core.result.Result;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestParam String username,
                                @RequestParam String password) {
        String token = authService.login(username, password);
        return Result.ok("登录成功", token);
    }
}
