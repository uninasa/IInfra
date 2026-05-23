package cn.uninasa.aspect;

import cn.uninasa.annotation.DictCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 字典缓存切面
 * 对字典查询的结果缓存，提高转译速度
 * 
 * @author Sayil
 */
@Aspect
@Component
@Slf4j
public class DictCacheAspect {
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //所有字典前缀的声明都为dict:cache:
    private static final String CACHE_PREFIX = "dict:cache:";
    
    @Around("@annotation(dictCache)")
    public Object around(ProceedingJoinPoint point, DictCache dictCache) throws Throwable {
        // 生成缓存的key
        String cacheKey = generateCacheKey(point, dictCache);
        
        // 从缓存读取字典数据
        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return cached;
        }
        
        // 缓存未命中，查数据库
        Object result = point.proceed();
        
        // 写入缓存（只缓存非空结果）
        if (result != null) {
            int expireDays = dictCache.expireDays();
            redisTemplate.opsForValue().set(cacheKey, result, expireDays, TimeUnit.DAYS);
            log.debug("字典数据写入缓存: key={}, expireDays={}", cacheKey, expireDays);
        }
        
        return result;
    }
    
    /**
     * 生成缓存key
     * 格式：dict:cache:{keyPrefix}:{参数1}:{参数2}...
     * 例如：dict:cache:dict:items:user_status [转译字典-用户状态]
     */
    private String generateCacheKey(ProceedingJoinPoint point, DictCache dictCache) {
        StringBuilder keyBuilder = new StringBuilder(CACHE_PREFIX);
        
        // 拼接注解指定的前缀
        keyBuilder.append(dictCache.keyPrefix());
        
        // 拼接方法参数（区分不同的查询）
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg != null) {
                    keyBuilder.append(":").append(arg.toString());
                }
            }
        }
        
        return keyBuilder.toString();
    }
}
