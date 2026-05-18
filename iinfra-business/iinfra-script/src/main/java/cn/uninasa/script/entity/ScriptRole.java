package cn.uninasa.script.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 剧本角色实体
 */
@Data
@TableName("script_role")
public class ScriptRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 所属剧本ID */
    private Long scriptId;

    /** 角色名称 */
    private String roleName;

    /** 角色性别 (0-不限, 1-男, 2-女) */
    private Integer gender;

    /** 角色描述 */
    private String description;

    /** 角色背景故事 */
    private String background;

    /** 角色线索 */
    private String clues;

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
