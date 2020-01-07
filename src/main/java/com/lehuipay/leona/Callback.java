package com.lehuipay.leona;

import com.lehuipay.leona.exception.LeonaException;

/**
 * 异步http请求callback函数
 */
public interface Callback<T> {
    void callback(LeonaException e, T data);
}
