package com.lehuipay.leona.model;

import com.lehuipay.leona.contracts.ErrorCode;
import com.squareup.moshi.Json;

public class ErrorMessage implements ErrorCode {

    @Json(name = "type")
    private String type;

    @Json(name = "code")
    private String code;

    @Json(name = "message")
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
