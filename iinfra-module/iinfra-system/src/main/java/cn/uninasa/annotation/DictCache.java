package cn.uninasa.annotation;

import java.lang.annotation.*;

/**
 * 字典缓存注解
 * 用于指定某个字典方法的缓存时间与缓存前缀
 * 
 * @author Sayil
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictCache {
    
    /**
     * 缓存key前缀
     * 默认使用方法签名
     */
    String keyPrefix() default "";
    
    /**
     * 缓存过期时间（天）
     * 默认3天
     */
    int expireDays() default 3;
}
