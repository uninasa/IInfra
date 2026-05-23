package cn.uninasa.aspect;

import cn.uninasa.annotation.DictTranslation;
import cn.uninasa.annotation.EnumDict;
import cn.uninasa.annotation.TransDict;
import cn.uninasa.core.result.Result;
import cn.uninasa.service.IEnumDictService;
import cn.uninasa.service.ISysDictService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 字典转译切面
 * 自动为带有@EnumDict和@TransDict注解的字段添加文本字段
 * 
 * @author Sayil
 */
@Aspect
@Component
@Slf4j
public class DictTranslationAspect {
    
    @Resource
    private ISysDictService sysDictService;
    
    @Resource
    private IEnumDictService enumDictService;
    
    @Around("@annotation(dictTranslation)")
    public Object around(ProceedingJoinPoint point, DictTranslation dictTranslation) throws Throwable {
        Object result = point.proceed();
        
        if (!dictTranslation.value()) {
            return result;
        }
        
        if (result instanceof Result) {
            Result<?> res = (Result<?>) result;
            Object data = res.getData();
            translateDict(data);
        }
        
        return result;
    }
    
    /**
     * 递归处理字典转译
     */
    private void translateDict(Object obj) {
        if (obj == null) {
            return;
        }
        
        // 处理集合
        if (obj instanceof Collection) {
            ((Collection<?>) obj).forEach(this::translateDict);
            return;
        }
        
        // 处理分页对象
        if (obj instanceof IPage) {
            IPage<?> page = (IPage<?>) obj;
            translateDict(page.getRecords());
            return;
        }
        
        // 处理普通对象
        Class<?> clazz = obj.getClass();
        
        // 跳过基本类型和包装类
        if (clazz.isPrimitive() || clazz.getName().startsWith("java.")) {
            return;
        }
        
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                
                if (value == null) {
                    continue;
                }
                
                // 处理枚举字典
                if (field.isAnnotationPresent(EnumDict.class)) {
                    EnumDict enumDict = field.getAnnotation(EnumDict.class);
                    String text = enumDictService.getEnumText(
                        enumDict.enumClass(), 
                        value.toString()
                    );
                    setTextField(obj, field.getName(), text);
                }
                
                // 处理转译字典
                if (field.isAnnotationPresent(TransDict.class)) {
                    TransDict transDict = field.getAnnotation(TransDict.class);
                    String text = getTransDictText(transDict, value.toString());
                    setTextField(obj, field.getName(), text);
                }
                
            } catch (Exception e) {
                log.error("字典转译失败: field={}, error={}", field.getName(), e.getMessage());
            }
        }
    }
    
    /**
     * 获取转译字典文本
     */
    private String getTransDictText(TransDict transDict, String value) {
        if (transDict.dictCode() != null && !transDict.dictCode().isEmpty()) {
            return sysDictService.getDictText(transDict.dictCode(), value);
        } else if (transDict.dictTable() != null && !transDict.dictTable().isEmpty()) {
            return sysDictService.getTableDictText(
                transDict.dictTable(),
                transDict.dictText(),
                transDict.dictValueField(),
                value
            );
        }
        return null;
    }
    
    /**
     * 设置文本字段
     */
    private void setTextField(Object obj, String fieldName, String text) {
        try {
            String textFieldName = fieldName + "Text";
            Field textField = obj.getClass().getDeclaredField(textFieldName);
            textField.setAccessible(true);
            textField.set(obj, text);
        } catch (NoSuchFieldException e) {
            // 如果没有对应的Text字段，忽略
        } catch (Exception e) {
            log.error("设置文本字段失败: fieldName={}, error={}", fieldName, e.getMessage());
        }
    }
}
