package com.lehuipay.leona;

import com.lehuipay.leona.exception.LeonaException;

/**
 * 异步http请求callback函数
 */
public interface Callback<T> {
    /**
     * 异步http请求callback方法
     * http status code > 300时, httpClient将responseBody封装为LeonaException
     * 使用LeonaException中方法获取http请求失败的原因, 例如
     * leonaClient.qrCodePay(req, (e, data) -> {
     *     if (e != null) {
     *        String type = e.getType();
     *        String code = e.getCode();
     *        String message = e.getMessage();
     *        System.err.printf("type: %s, code: %s, message: %s\n", type, code, message);
     *        return;
     *     }
     *     System.out.println(data);
     * });
     *
     * @param e
     * @param data
     */
    void callback(LeonaException e, T data);
}
