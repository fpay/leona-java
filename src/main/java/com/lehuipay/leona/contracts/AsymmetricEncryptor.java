package com.lehuipay.leona.contracts;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

/**
 * 非对称加密
 */
public interface AsymmetricEncryptor {

    byte[] pubEncode(String data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    byte[] priDecode(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    byte[] sign(byte[] data) throws SignatureException, InvalidKeyException;

    boolean verify(byte[] content, byte[] sign) throws SignatureException, InvalidKeyException;
}
