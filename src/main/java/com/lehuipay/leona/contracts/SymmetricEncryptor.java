package com.lehuipay.leona.contracts;

/**
 * 对称加密
 */
public interface SymmetricEncryptor {

    String encrypt(byte[] body, String secretKey);

    byte[] decrypt(String body, byte[] secretKey);
}
