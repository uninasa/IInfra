package cn.uninasa.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import cn.uninasa.core.exception.BusinessException;
import cn.uninasa.core.result.ResultCode;
import cn.uninasa.system.entity.SysUser;
import cn.uninasa.system.mapper.SysUserMapper;
import cn.uninasa.system.service.ISysUserService;

/**
 * 用户服务实现
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
    }

    @Override
    public void register(SysUser user) {
        // 检查用户名是否已存在
        SysUser existUser = getByUsername(user.getUsername());
        if (existUser != null) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS);
        }
        // TODO: 密码加密
        user.setStatus(1);
        save(user);
    }
}
