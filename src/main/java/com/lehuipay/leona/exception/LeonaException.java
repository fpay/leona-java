package com.lehuipay.leona.exception;

import com.lehuipay.leona.contracts.ErrorCode;

public class LeonaException extends Exception {

    private static final long serialVersionUID = -930797565147601915L;

    public LeonaException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public LeonaException(final ErrorCode errorCode, final Throwable t) {
        super(errorCode.getMessage(), t);
        this.errorCode = errorCode;
    }

    public LeonaException(LeonaRuntimeException e) {
        super(e.getCause());
        this.errorCode = e.getErrorCode();
    }

    private final ErrorCode errorCode;

    public String getType() {
        return this.errorCode.getType();
    }

    public String getCode() {
        return this.errorCode.getCode();
    }

    public String getMessage() {
        return this.errorCode.getMessage();
    }

    @Override
    public String toString() {
        return "LeonaException{" +
                "type=" + getType() +
                ", code=" +getCode() +
                ", message=" + getMessage() +
                '}';
    }
}
