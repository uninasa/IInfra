package cn.uninasa.service.impl;

import cn.uninasa.annotation.DictCache;
import cn.uninasa.entity.SysDictItem;
import cn.uninasa.enums.IDictEnum;
import cn.uninasa.service.IEnumDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举字典服务实现
 * 
 * @author Sayil
 */
@Service
@Slf4j
public class EnumDictServiceImpl implements IEnumDictService {

    @Override
    public List<SysDictItem> getEnumItemsByClassName(String enumClassName) {
        try {
            Class<?> clazz = Class.forName(enumClassName);
            if (IDictEnum.class.isAssignableFrom(clazz)) {
                @SuppressWarnings("unchecked")
                Class<? extends IDictEnum> enumClass = (Class<? extends IDictEnum>) clazz;
                return getEnumItems(enumClass);
            } else {
                log.warn("类不是IBaseEnum的实现: {}", enumClassName);
            }
        } catch (ClassNotFoundException e) {
            log.error("枚举类不存在: {}", enumClassName, e);
        }
        return new ArrayList<>();
    }

    @Override
    @DictCache(keyPrefix = "enum:items")
    public List<SysDictItem> getEnumItems(Class<? extends IDictEnum> enumClass) {
        if (enumClass == null || !enumClass.isEnum()) {
            return new ArrayList<>();
        }

        return loadEnumItems(enumClass);
    }

    @Override
    @DictCache(keyPrefix = "enum:text")
    public String getEnumText(Class<? extends IDictEnum> enumClass, String code) {
        if (enumClass == null || code == null) {
            return null;
        }
        
        // getByCode 返回的是 Enum 类型，需要转换为 IDictEnum
        @SuppressWarnings("unchecked")
        Enum<?> enumInstance = IDictEnum.getByCode((Class) enumClass, code);
        
        if (enumInstance instanceof IDictEnum) {
            return ((IDictEnum) enumInstance).getName();
        }
        
        return null;
    }
    
    @Override
    public String getEnumTextByClassName(String enumClassName, String code) {
        try {
            Class<?> clazz = Class.forName(enumClassName);
            if (IDictEnum.class.isAssignableFrom(clazz)) {
                @SuppressWarnings("unchecked")
                Class<? extends IDictEnum> enumClass = (Class<? extends IDictEnum>) clazz;
                return getEnumText(enumClass, code);
            }
        } catch (ClassNotFoundException e) {
            log.error("枚举类不存在: {}", enumClassName, e);
        }
        return null;
    }
    
    /**
     * 从枚举类加载字典项
     */
    private List<SysDictItem> loadEnumItems(Class<? extends IDictEnum> enumClass) {
        List<SysDictItem> items = new ArrayList<>();
        
        // 获取枚举常量，先转为 Object[] 避免类型冲突
        Object[] constants = enumClass.getEnumConstants();
        if (constants == null) {
            return items;
        }
        
        // 逐个转换并处理
        int order = 0;
        for (Object constant : constants) {
            IDictEnum enumConstant = (IDictEnum) constant;
            SysDictItem item = new SysDictItem();
            item.setItemValue(enumConstant.getCode());
            item.setItemText(enumConstant.getName());
            item.setDescription(enumConstant.getDesc());
            item.setSortOrder(order++);
            items.add(item);
        }
        
        return items;
    }
}
