package cn.uninasa.service;

import cn.uninasa.entity.SysDictItem;
import cn.uninasa.enums.IDictEnum;

import java.util.List;

/**
 * 枚举字典服务接口
 * 
 * @author Sayil
 */
public interface IEnumDictService {
    
    /**
     * 获取枚举字典项列表
     * @param enumClass 枚举类
     * @return 字典项列表
     */
    List<SysDictItem> getEnumItems(Class<? extends IDictEnum> enumClass);
    
    /**
     * 根据枚举类名获取字典项
     * @param enumClassName 枚举类全限定名
     * @return 字典项列表
     */
    List<SysDictItem> getEnumItemsByClassName(String enumClassName);
    
    /**
     * 根据code获取枚举描述
     * @param enumClass 枚举类
     * @param code 编码
     * @return 描述文本
     */
    String getEnumText(Class<? extends IDictEnum> enumClass, String code);
    
    /**
     * 根据枚举类名和code获取枚举描述
     * @param enumClassName 枚举类全限定名
     * @param code 编码
     * @return 描述文本
     */
    String getEnumTextByClassName(String enumClassName, String code);
}
