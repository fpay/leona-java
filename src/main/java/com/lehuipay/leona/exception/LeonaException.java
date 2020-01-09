package com.lehuipay.leona.exception;

import com.lehuipay.leona.contracts.ErrorCode;

public class LeonaException extends Exception {

    private static final long serialVersionUID = -930797565147601915L;

    public LeonaException(ErrorCode errorCode) {
        this.type = errorCode.getType();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public LeonaException(LeonaRuntimeException e) {
        this.type = e.getErrorCode().getType();
        this.code = e.getErrorCode().getCode();
        this.message = e.getErrorCode().getMessage();
    }

    private final String type;
    private final String code;
    private final String message;

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
