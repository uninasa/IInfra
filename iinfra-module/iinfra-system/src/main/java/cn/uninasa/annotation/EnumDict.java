package cn.uninasa.annotation;

import cn.uninasa.enums.IDictEnum;

import java.lang.annotation.*;

/**
 * 枚举字典 注解
 * 用于标注实体字段，指定该属性使用 枚举字典 进行翻译
 * 
 * @author Sayil
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumDict {
    
    /**
     * 枚举类
     */
    Class<? extends IDictEnum> enumClass();
}
