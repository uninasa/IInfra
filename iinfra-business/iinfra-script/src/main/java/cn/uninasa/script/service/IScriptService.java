package cn.uninasa.script.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.uninasa.script.entity.Script;

/**
 * 剧本服务接口
 */
public interface IScriptService extends IService<Script> {

    /**
     * 发布剧本
     */
    void publish(Long scriptId);

    /**
     * 下架剧本
     */
    void offline(Long scriptId);
}
