package cn.uninasa.script.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.uninasa.core.result.Result;
import cn.uninasa.script.entity.Script;
import cn.uninasa.script.service.IScriptService;

/**
 * 剧本控制器
 */
@RestController
@RequestMapping("/script")
public class ScriptController {

    @Autowired
    private IScriptService scriptService;

    /**
     * 分页查询剧本列表
     */
    @GetMapping("/list")
    public Result<IPage<Script>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Script> page = new Page<>(pageNum, pageSize);
        IPage<Script> result = scriptService.page(page);
        return Result.ok(result);
    }

    /**
     * 获取剧本详情
     */
    @GetMapping("/{id}")
    public Result<Script> detail(@PathVariable Long id) {
        Script script = scriptService.getById(id);
        return Result.ok(script);
    }

    /**
     * 创建剧本
     */
    @PostMapping
    public Result<Void> create(@RequestBody Script script) {
        script.setStatus(0); // 默认草稿状态
        scriptService.save(script);
        return Result.ok();
    }

    /**
     * 更新剧本
     */
    @PutMapping
    public Result<Void> update(@RequestBody Script script) {
        scriptService.updateById(script);
        return Result.ok();
    }

    /**
     * 删除剧本
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        scriptService.removeById(id);
        return Result.ok();
    }

    /**
     * 发布剧本
     */
    @PostMapping("/{id}/publish")
    public Result<Void> publish(@PathVariable Long id) {
        scriptService.publish(id);
        return Result.ok();
    }

    /**
     * 下架剧本
     */
    @PostMapping("/{id}/offline")
    public Result<Void> offline(@PathVariable Long id) {
        scriptService.offline(id);
        return Result.ok();
    }
}
