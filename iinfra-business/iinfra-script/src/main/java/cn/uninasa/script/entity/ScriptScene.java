package cn.uninasa.script.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 剧本场景实体
 */
@Data
@TableName("script_scene")
public class ScriptScene implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 所属剧本ID */
    private Long scriptId;

    /** 场景名称 */
    private String sceneName;

    /** 场景描述 */
    private String description;

    /** 场景内容 */
    private String content;

    /** 场景类型 */
    private String sceneType;

    /** 排序 */
    private Integer sortOrder;

    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标记 (0-正常, 1-已删除) */
    private Integer delFlag;
}
