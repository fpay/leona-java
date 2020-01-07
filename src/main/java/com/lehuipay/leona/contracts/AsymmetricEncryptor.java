package com.lehuipay.leona.contracts;

/**
 * 非对称加密
 */
public interface AsymmetricEncryptor {

    byte[] pubEncode(String data);

    byte[] priDecode(byte[] data);

    byte[] sign(byte[] data);

    boolean verify(byte[] content, byte[] sign);
}
