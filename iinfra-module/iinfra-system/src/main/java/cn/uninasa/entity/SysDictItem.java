package cn.uninasa.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 字典项表(SysDictItem)表实体类
 *
 * @author Sayil
 */
@Data
@TableName("sys_dict_item")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sys_dict_item对象", description="字典项表")
public class SysDictItem implements Serializable {
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
     * 字典id
     */
    @ApiModelProperty(value = "字典id")
    private String dictId;
    /**
     * 字典项文本
     */
    @ApiModelProperty(value = "字典项文本")
    private String itemText;
    /**
     * 字典项值
     */
    @ApiModelProperty(value = "字典项值")
    private String itemValue;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sortOrder;
}

