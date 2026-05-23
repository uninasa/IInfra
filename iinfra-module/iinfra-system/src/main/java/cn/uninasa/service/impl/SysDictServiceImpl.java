package cn.uninasa.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.uninasa.annotation.DictCache;
import cn.uninasa.entity.SysDictItem;
import cn.uninasa.service.ISysDictItemService;
import cn.uninasa.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.uninasa.mapper.SysDictMapper;
import cn.uninasa.entity.SysDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 字典表(SysDict)表服务实现类
 *
 * @author Sayil
 */
@Service
@Slf4j
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Resource
    private ISysDictItemService sysDictItemService;
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    private static final String DICT_CACHE_PREFIX = "dict:cache:*";
    
    @Override
    @DictCache(keyPrefix = "dict:items")
    public List<SysDictItem> getDictItems(String dictCode) {
        if (StrUtil.isEmpty(dictCode)) {
            return new ArrayList<>();
        }
        
        return queryDictItemsFromDb(dictCode);
    }
    
    @Override
    @DictCache(keyPrefix = "dict:text")
    public String getDictText(String dictCode, String value) {
        if (StrUtil.isEmpty(dictCode) || StrUtil.isEmpty(value)) {
            return null;
        }
        
        // 从字典项列表中查找
        List<SysDictItem> items = getDictItems(dictCode);
        return items.stream()
                .filter(item -> value.equals(item.getItemValue()))
                .map(SysDictItem::getItemText)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    @DictCache(keyPrefix = "dict:table")
    public String getTableDictText(String tableName, String textField, String valueField, String value) {
        if (StrUtil.isEmpty(tableName) || StrUtil.isEmpty(textField) 
            || StrUtil.isEmpty(valueField) || StrUtil.isEmpty(value)) {
            return null;
        }
        
        // 使用MyBatis-Plus动态查询
        try {
            // 安全校验：只允许字母、数字、下划线，防止SQL注入
            if (!tableName.matches("^[a-zA-Z0-9_]+$") 
                || !textField.matches("^[a-zA-Z0-9_]+$") 
                || !valueField.matches("^[a-zA-Z0-9_]+$")) {
                log.error("表字典参数包含非法字符: table={}, textField={}, valueField={}", 
                         tableName, textField, valueField);
                return null;
            }
            
            // 使用Mapper的自定义方法查询
            return baseMapper.selectTableDictText(tableName, textField, valueField, value);
        } catch (Exception e) {
            log.error("查询表字典失败: table={}, textField={}, valueField={}, value={}", 
                     tableName, textField, valueField, value, e);
        }
        
        return null;
    }
    
    @Override
    public void refreshCache(String dictCode) {
        // 清理所有字典缓存（包括枚举字典）
        Set<String> keys = redisTemplate.keys(DICT_CACHE_PREFIX);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.info("已清理所有字典缓存，共 {} 个key", keys.size());
        } else {
            log.info("没有需要清理的字典缓存");
        }
    }
    
    /**
     * 从数据库查询字典项
     */
    private List<SysDictItem> queryDictItemsFromDb(String dictCode) {
        // 根据字典编码查询字典
        LambdaQueryWrapper<SysDict> dictQuery = new LambdaQueryWrapper<>();
        dictQuery.eq(SysDict::getDictCode, dictCode);
        SysDict dict = this.getOne(dictQuery);
        
        if (dict == null) {
            log.warn("字典不存在: {}", dictCode);
            return new ArrayList<>();
        }
        
        // 查询字典项
        LambdaQueryWrapper<SysDictItem> itemQuery = new LambdaQueryWrapper<>();
        itemQuery.eq(SysDictItem::getDictId, dict.getId())
                .orderByAsc(SysDictItem::getSortOrder);
        return sysDictItemService.list(itemQuery);
    }
}

