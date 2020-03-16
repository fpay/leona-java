package com.lehuipay.leona.contracts;

import java.security.InvalidKeyException;

/**
 * 对称加密
 */
public interface SymmetricEncryptor {

    String encrypt(byte[] body, String secretKey) throws InvalidKeyException;

    byte[] decrypt(String body, byte[] secretKey) throws InvalidKeyException ;
}
