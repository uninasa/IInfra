package cn.uninasa.script.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import cn.uninasa.core.exception.BusinessException;
import cn.uninasa.core.result.ResultCode;
import cn.uninasa.script.entity.Script;
import cn.uninasa.script.mapper.ScriptMapper;
import cn.uninasa.script.service.IScriptService;

/**
 * 剧本服务实现
 */
@Service
public class ScriptServiceImpl extends ServiceImpl<ScriptMapper, Script> implements IScriptService {

    @Override
    public void publish(Long scriptId) {
        Script script = getById(scriptId);
        if (script == null) {
            throw new BusinessException(ResultCode.SCRIPT_NOT_FOUND);
        }
        if (script.getStatus() == 1) {
            throw new BusinessException(ResultCode.SCRIPT_ALREADY_PUBLISHED);
        }
        script.setStatus(1);
        updateById(script);
    }

    @Override
    public void offline(Long scriptId) {
        Script script = getById(scriptId);
        if (script == null) {
            throw new BusinessException(ResultCode.SCRIPT_NOT_FOUND);
        }
        script.setStatus(2);
        updateById(script);
    }
}
