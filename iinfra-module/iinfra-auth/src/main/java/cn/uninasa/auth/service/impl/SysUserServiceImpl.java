package cn.uninasa.auth.service.impl;

import cn.uninasa.auth.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.uninasa.auth.mapper.SysUserMapper;
import cn.uninasa.auth.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * 系统用户表(SysUser)表服务实现类
 *
 * @author Sayil
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}

