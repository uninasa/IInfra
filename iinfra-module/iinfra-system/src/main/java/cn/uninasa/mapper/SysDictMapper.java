package cn.uninasa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.uninasa.entity.SysDict;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 字典表(SysDict)表数据库访问层
 *
 * @author Sayil
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 动态查询表字典文本
     * 注意：此方法使用动态SQL，调用前必须对参数进行严格校验，防止SQL注入
     * 
     * @param tableName 表名（已校验）
     * @param textField 文本字段（已校验）
     * @param valueField 值字段（已校验）
     * @param value 查询值
     * @return 文本值
     */
    @Select("SELECT ${textField} FROM ${tableName} WHERE ${valueField} = #{value} LIMIT 1")
    String selectTableDictText(@Param("tableName") String tableName,
                               @Param("textField") String textField,
                               @Param("valueField") String valueField,
                               @Param("value") String value);
}

