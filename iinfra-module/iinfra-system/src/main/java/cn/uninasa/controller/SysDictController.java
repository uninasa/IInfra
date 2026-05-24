package cn.uninasa.controller;

import cn.hutool.core.util.StrUtil;
import cn.uninasa.entity.SysDictItem;
import cn.uninasa.service.IEnumDictService;
import cn.uninasa.service.ISysDictService;
import cn.uninasa.core.result.PageRequest;
import cn.uninasa.core.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import cn.uninasa.entity.SysDict;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 字典表(SysDict)表控制层
 *
 * @author Sayil
 */
@Api(tags = "字典表")
@RestController
@RequestMapping("sys/dict")
@Slf4j
public class SysDictController {
    @Resource
    private ISysDictService sysDictService;
    
    @Resource
    private IEnumDictService enumDictService;
    
    /**
     * 分页列表查询
     */
    @ApiOperation(value = "字典表-分页列表查询", notes = "字典表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<SysDict>> queryPageList(SysDict sysDict, PageRequest pageRequest) {
        LambdaQueryWrapper<SysDict> query = new LambdaQueryWrapper<>();
        if(StrUtil.isNotEmpty(sysDict.getDictName())){
            query.like(SysDict::getDictName,sysDict.getDictName());
        }
        if(StrUtil.isNotEmpty(sysDict.getDictCode())){
            query.like(SysDict::getDictCode,sysDict.getDictCode());
        }
        if(StrUtil.isNotEmpty(sysDict.getDescription())){
            query.like(SysDict::getDescription,sysDict.getDescription());
        }
        IPage<SysDict> pageList = this.sysDictService.page(pageRequest.toPage(), query);
        return Result.ok(pageList);
    }
    
    /**
     * 通过主键查询
     * @param id
     * @return
     */
    @ApiOperation(value = "字典表-通过主键查询", notes = "字典表-通过主键查询")
    @GetMapping(value = "/queryById")
    public Result<SysDict> queryById(@RequestParam("id") String id) {
        SysDict sysDict = this.sysDictService.getById(id);
        if(sysDict == null) {
            return Result.error("未找到对应数据！");
        }
        return Result.ok(sysDict);
    }
    
    /**
     * 保存
     * @param sysDict
     * @return
     */
    @ApiOperation(value = "字典表-添加", notes = "字典表-添加")
    @PostMapping(value = "/add")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> add(@RequestBody SysDict sysDict) {
        this.sysDictService.save(sysDict);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     * @param sysDict
     * @return
     */
    @ApiOperation(value = "字典表-编辑", notes = "字典表-编辑")
    @PostMapping(value = "/edit")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> edit(@RequestBody SysDict sysDict) {
        if (StrUtil.isEmpty(sysDict.getId()) || this.sysDictService.getById(sysDict.getId()) == null) {
            return Result.ok("查询不到当前待修改的数据！");
        } else {
            this.sysDictService.updateById(sysDict);
            return Result.ok("编辑成功！");
        }
    }
    
    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "字典表-删除", notes = "字典表-删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam("id") String id) {
        this.sysDictService.removeById(id);
        return Result.ok("删除成功！");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "字典表-批量删除", notes = "字典表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam("ids") String ids) {
        this.sysDictService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }
    
    /**
     * 获取转译字典项（用于下拉框）
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @ApiOperation(value = "获取转译字典项", notes = "根据字典编码获取字典项列表，用于下拉框")
    @GetMapping(value = "/items/{dictCode}")
    public Result<List<SysDictItem>> getDictItems(@PathVariable String dictCode) {
        List<SysDictItem> items = sysDictService.getDictItems(dictCode);
        return Result.ok(items);
    }

    
    /**
     * 获取枚举字典项
     * @param enumClassName 枚举类全限定名
     * @return 字典项列表
     */
    @ApiOperation(value = "获取枚举字典项", notes = "根据枚举类全限定名获取字典项列表")
    @GetMapping(value = "/enum/{enumClassName}")
    public Result<List<SysDictItem>> getEnumDictItems(@PathVariable String enumClassName) {
        List<SysDictItem> items = enumDictService.getEnumItemsByClassName(enumClassName);
        return Result.ok(items);
    }
    
    /**
     * 刷新所有字典缓存（包括转译字典和枚举字典）
     * @return 结果
     */
    @ApiOperation(value = "刷新所有字典缓存", notes = "清理所有字典缓存（包括转译字典和枚举字典），下次查询时会重新加载并缓存3天")
    @GetMapping(value = "/refreshCache")
    public Result<String> refreshCache() {
        sysDictService.refreshCache(null);
        return Result.ok("所有字典缓存已清理！");
    }
}


