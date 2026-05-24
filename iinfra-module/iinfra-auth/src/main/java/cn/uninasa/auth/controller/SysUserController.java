package cn.uninasa.auth.controller;

import cn.hutool.core.util.StrUtil;
import cn.uninasa.annotation.DictTranslation;
import cn.uninasa.auth.service.ISysUserService;
import cn.uninasa.core.result.PageRequest;
import cn.uninasa.core.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import cn.uninasa.auth.entity.SysUser;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 系统用户表(SysUser)表控制层
 *
 * @author Sayil
 */
@Api(tags = "系统用户表")
@RestController
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {
    @Resource
    private ISysUserService sysUserService;
    
    /**
     * 分页列表查询
     */
    @ApiOperation(value = "系统用户表-分页列表查询", notes = "系统用户表-分页列表查询")
    @GetMapping(value = "/list")
    @DictTranslation
    public Result<IPage<SysUser>> queryPageList(SysUser sysUser, PageRequest pageRequest) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        IPage<SysUser> pageList = this.sysUserService.page(pageRequest.toPage(), query);
        return Result.ok(pageList);
    }
    
    /**
     * 通过主键查询
     * @param id
     * @return
     */
    @ApiOperation(value = "系统用户表-通过主键查询", notes = "系统用户表-通过主键查询")
    @GetMapping(value = "/queryById")
    @DictTranslation
    public Result<SysUser> queryById(@RequestParam("id") String id) {
        SysUser sysUser = this.sysUserService.getById(id);
        if(sysUser == null) {
            return Result.error("未找到对应数据！");
        }
        return Result.ok(sysUser);
    }
    
    /**
     * 保存
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "系统用户表-添加", notes = "系统用户表-添加")
    @PostMapping(value = "/add")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> add(@RequestBody SysUser sysUser) {
        this.sysUserService.save(sysUser);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "系统用户表-编辑", notes = "系统用户表-编辑")
    @PostMapping(value = "/edit")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> edit(@RequestBody SysUser sysUser) {
        if (StrUtil.isEmpty(sysUser.getId()) || this.sysUserService.getById(sysUser.getId()) == null) {
            return Result.ok("查询不到当前待修改的数据！");
        } else {
            this.sysUserService.updateById(sysUser);
            return Result.ok("编辑成功！");
        }
    }
    
    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "系统用户表-删除", notes = "系统用户表-删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam("id") String id) {
        this.sysUserService.removeById(id);
        return Result.ok("删除成功！");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "系统用户表-批量删除", notes = "系统用户表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam("ids") String ids) {
        this.sysUserService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }
}

