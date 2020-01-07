package com.lehuipay.leona.contracts;

/**
 * 签名与验签
 */
public interface Signer {

    String sign(String body, String nonce);

    boolean verify(String body, String nonce, String signature);
}
