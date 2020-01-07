package com.lehuipay.leona.exception;

import com.lehuipay.leona.contracts.ErrorCode;

public class LeonaRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -7864604160297181941L;

    /**
     * 错误码
     */
    protected ErrorCode errorCode;

    /**
     * 无参默认构造UNSPECIFIED
     */
    public LeonaRuntimeException() {
        super(LeonaErrorCodeEnum.UNEXPECTED.getMessage());
        this.errorCode = LeonaErrorCodeEnum.UNEXPECTED;
    }

    /**
     * 指定错误码构造通用异常
     *
     * @param errorCode 错误码
     */
    public LeonaRuntimeException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 指定详细描述构造通用异常
     *
     * @param detailedMessage 详细描述
     */
    public LeonaRuntimeException(final String detailedMessage) {
        super(detailedMessage);
        this.errorCode = LeonaErrorCodeEnum.UNEXPECTED;
    }

    /**
     * 指定导火索构造通用异常
     *
     * @param t 导火索
     */
    public LeonaRuntimeException(final Throwable t) {
        super(t);
        this.errorCode = LeonaErrorCodeEnum.UNEXPECTED;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode       错误码
     * @param detailedMessage 详细描述
     */
    public LeonaRuntimeException(final ErrorCode errorCode, final String detailedMessage) {
        super(detailedMessage);
        this.errorCode = errorCode;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode 错误码
     * @param t         导火索
     */
    public LeonaRuntimeException(final ErrorCode errorCode, final Throwable t) {
        super(errorCode.getMessage(), t);
        this.errorCode = errorCode;
    }

    /**
     * 构造通用异常
     *
     * @param detailedMessage 详细描述
     * @param t               导火索
     */
    public LeonaRuntimeException(final String detailedMessage, final Throwable t) {
        super(detailedMessage, t);
        this.errorCode = LeonaErrorCodeEnum.UNEXPECTED;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode       错误码
     * @param detailedMessage 详细描述
     * @param t               导火索
     */
    public LeonaRuntimeException(final ErrorCode errorCode, final String detailedMessage,
                                 final Throwable t) {
        super(detailedMessage, t);
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
