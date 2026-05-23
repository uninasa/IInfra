package cn.uninasa.annotation;

import java.lang.annotation.*;

/**
 * 转译字典 注解
 * 用于标注实体字段，指定该属性使用 转义字典（从数据库字典表/数据表获取数据）进行翻译
 * 
 * @author Sayil
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransDict {
    
    /**
     * 字典编码
     */
    String dictCode() default "";
    
    /**
     * 表字典-表名
     */
    String dictTable() default "";
    
    /**
     * 表字典-文本字段
     */
    String dictText() default "";
    
    /**
     * 表字典-值字段
     */
    String dictValueField() default "";
}
