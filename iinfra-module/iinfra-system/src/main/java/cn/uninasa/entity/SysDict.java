package cn.uninasa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典表(SysDict)表实体类
 *
 * @author Sayil
 */
@Data
@TableName("sys_dict")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sys_dict对象", description="字典表")
public class SysDict implements Serializable {
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;
    /**
     * 删除标记 (0-正常, 1-已删除)
     */
    @ApiModelProperty(value = "删除标记 (0-正常, 1-已删除)")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;
    /**
     * 字典名词
     */
    @ApiModelProperty(value = "字典名词")
    private String dictName;
    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码")
    private String dictCode;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
}

