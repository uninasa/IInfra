package cn.uninasa.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.uninasa.auth.util.JwtUtil;
import cn.uninasa.core.exception.BusinessException;
import cn.uninasa.core.result.ResultCode;
import cn.uninasa.system.entity.SysUser;
import cn.uninasa.system.service.ISysUserService;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务
 */
@Service
public class AuthService {

    @Autowired
    private ISysUserService userService;

    /**
     * 登录
     */
    public String login(String username, String password) {
        SysUser user = userService.getByUsername(username);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        // TODO: 密码加密比对
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }

        // 生成Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        return JwtUtil.generateToken(username, claims);
    }
}
