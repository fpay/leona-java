package com.lehuipay.leona.contracts;

import java.security.InvalidKeyException;

/**
 * 签名与验签
 */
public interface Signer {

    String sign(String body, String nonce) throws InvalidKeyException;

    boolean verify(String body, String nonce, String signature) throws InvalidKeyException;
}
