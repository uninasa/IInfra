package cn.uninasa.service;

import cn.uninasa.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.uninasa.entity.SysDict;

import java.util.List;

/**
 * 字典表(SysDict)表服务接口
 *
 * @author Sayil
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 根据字典编码获取字典项列表（用于下拉框、转译）
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    List<SysDictItem> getDictItems(String dictCode);

    /**
     * 根据字典编码和值获取文本
     * @param dictCode 字典编码
     * @param value 字典值
     * @return 字典文本
     */
    String getDictText(String dictCode, String value);
    
    /**
     * 根据表字典获取文本
     * @param tableName 表名
     * @param textField 文本字段
     * @param valueField 值字段
     * @param value 值
     * @return 文本
     */
    String getTableDictText(String tableName, String textField, String valueField, String value);
    
    /**
     * 刷新字典缓存
     * @param dictCode 字典编码，为空则刷新全部
     */
    void refreshCache(String dictCode);
}

