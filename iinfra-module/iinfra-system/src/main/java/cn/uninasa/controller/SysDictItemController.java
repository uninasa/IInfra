package cn.uninasa.controller;

import cn.hutool.core.util.StrUtil;
import cn.uninasa.service.ISysDictItemService;
import cn.uninasa.core.result.PageRequest;
import cn.uninasa.core.result.Result;
import cn.uninasa.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import cn.uninasa.entity.SysDictItem;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 字典项表(SysDictItem)表控制层
 *
 * @author Sayil
 */
@Api(tags = "字典项表")
@RestController
@RequestMapping("sys/dict/item")
@Slf4j
public class SysDictItemController {
    @Resource
    private ISysDictItemService sysDictItemService;
    
    @Resource
    private ISysDictService sysDictService;
    
    /**
     * 分页列表查询
     */
    @ApiOperation(value = "字典项表-分页列表查询", notes = "字典项表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<SysDictItem>> queryPageList(SysDictItem sysDictItem, PageRequest pageRequest) {
        LambdaQueryWrapper<SysDictItem> query = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(sysDictItem.getDictId())) {
            query.eq(SysDictItem::getDictId, sysDictItem.getDictId());
        }
        query.orderByAsc(SysDictItem::getSortOrder);
        IPage<SysDictItem> pageList = this.sysDictItemService.page(pageRequest.toPage(), query);
        return Result.ok(pageList);
    }
    
    /**
     * 通过主键查询
     * @param id
     * @return
     */
    @ApiOperation(value = "字典项表-通过主键查询", notes = "字典项表-通过主键查询")
    @GetMapping(value = "/queryById")
    public Result<SysDictItem> queryById(@RequestParam("id") String id) {
        SysDictItem sysDictItem = this.sysDictItemService.getById(id);
        if(sysDictItem == null) {
            return Result.error("未找到对应数据！");
        }
        return Result.ok(sysDictItem);
    }
    
    /**
     * 保存
     * @param sysDictItem
     * @return
     */
    @ApiOperation(value = "字典项表-添加", notes = "字典项表-添加")
    @PostMapping(value = "/add")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> add(@RequestBody SysDictItem sysDictItem) {
        this.sysDictItemService.save(sysDictItem);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     * @param sysDictItem
     * @return
     */
    @ApiOperation(value = "字典项表-编辑", notes = "字典项表-编辑")
    @PostMapping(value = "/edit")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> edit(@RequestBody SysDictItem sysDictItem) {
        if (StrUtil.isEmpty(sysDictItem.getId()) || this.sysDictItemService.getById(sysDictItem.getId()) == null) {
            return Result.ok("查询不到当前待修改的数据！");
        } else {
            this.sysDictItemService.updateById(sysDictItem);
            return Result.ok("编辑成功！");
        }
    }
    
    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "字典项表-删除", notes = "字典项表-删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam("id") String id) {
        this.sysDictItemService.removeById(id);
        return Result.ok("删除成功！");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "字典项表-批量删除", notes = "字典项表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam("ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        this.sysDictItemService.removeByIds(idList);
        return Result.ok("批量删除成功！");
    }
}


