package com.lehuipay.leona.contracts;

public interface ErrorCode {

    /**
     * 错误类型
     *
     * @return
     */
    String getType();

    /**
     * 获取错误码
     *
     * @return
     */
    String getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getMessage();
}
