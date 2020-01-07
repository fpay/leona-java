package com.lehuipay.leona.exception;

import com.lehuipay.leona.model.ErrorMessage;

public class LeonaException extends Exception {

    public LeonaException(String type, String code, String message) {
        this.type = type;
        this.code = code;
        this.message = message;
    }

    public LeonaException(ErrorMessage errorMessage) {
        this.type = errorMessage.getType();
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage();
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
