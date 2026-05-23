package cn.uninasa.annotation;

import java.lang.annotation.*;

/**
 * 字典转译注解
 * 用于标注Controller方法，表示该方法的返回值需要进行字典转译
 * 
 * @author Sayil
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictTranslation {
    
    /**
     * 是否启用字典转译
     */
    boolean value() default true;
}
