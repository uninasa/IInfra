package cn.uninasa.script.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 剧本实体
 */
@Data
@TableName("script_info")
public class Script implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 剧本名称 */
    private String name;

    /** 剧本简介 */
    private String description;

    /** 剧本类型 (推理/恐怖/情感/欢乐等) */
    private String type;

    /** 难度等级 (1-5) */
    private Integer difficulty;

    /** 适合人数(最少) */
    private Integer minPlayers;

    /** 适合人数(最多) */
    private Integer maxPlayers;

    /** 预计时长(分钟) */
    private Integer duration;

    /** 封面图片 */
    private String coverImage;

    /** 状态 (0-草稿, 1-已发布, 2-已下架) */
    private Integer status;

    /** 作者ID */
    private Long authorId;

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
