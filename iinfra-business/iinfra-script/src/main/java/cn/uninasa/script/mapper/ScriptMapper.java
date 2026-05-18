package cn.uninasa.script.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.uninasa.script.entity.Script;

/**
 * 剧本Mapper接口
 */
@Mapper
public interface ScriptMapper extends BaseMapper<Script> {
}
