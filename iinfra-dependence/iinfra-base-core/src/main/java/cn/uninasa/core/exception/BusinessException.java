package cn.uninasa.core.exception;

import cn.uninasa.core.result.ResultCode;
import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 错误码 */
    private final Integer code;

    /**
     * 通过消息构造（默认错误码 500）
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    /**
     * 通过错误码和消息构造
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 通过 ResultCode 枚举构造
     */
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    /**
     * 通过 ResultCode 枚举 + 原始异常构造
     */
    public BusinessException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.code = resultCode.getCode();
    }

    /**
     * 通过消息 + 原始异常构造
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }
}
