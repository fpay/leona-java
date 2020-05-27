package com.lehuipay.leona.contracts;

public interface ErrorCode {

    /**
     * 错误类型
     *
     */
    String getType();

    /**
     * 获取错误码
     *
     */
    String getCode();

    /**
     * 获取错误信息
     *
     */
    String getMessage();
}
