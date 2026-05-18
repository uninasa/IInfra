package cn.uninasa.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.uninasa.system.entity.SysUser;

/**
 * 用户服务接口
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);

    /**
     * 注册用户
     */
    void register(SysUser user);
}
