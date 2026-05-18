package cn.uninasa.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一结果状态码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /* 通用 */
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),

    /* 用户相关 */
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户已存在"),
    PASSWORD_ERROR(1003, "密码错误"),

    /* 剧本相关 */
    SCRIPT_NOT_FOUND(2001, "剧本不存在"),
    SCRIPT_ALREADY_PUBLISHED(2002, "剧本已发布，无法重复操作");

    /** 状态码 */
    private final Integer code;

    /** 提示信息 */
    private final String message;
}
