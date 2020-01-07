package com.lehuipay.leona;

import com.lehuipay.leona.contracts.SymmetricEncryptor;
import com.lehuipay.leona.utils.AESPKCS7;
import com.lehuipay.leona.utils.CommonUtil;

public class AESEncryptor implements SymmetricEncryptor {

    AESPKCS7 aes = new AESPKCS7();

    @Override
    public String encrypt(byte[] body, String secretKey) {
        final String iv = CommonUtil.randomStr(Const.IV_LENGTH);
        return aes.encryptWithIVBase64(body, secretKey.getBytes(), iv.getBytes());
    }

    @Override
    public byte[] decrypt(String body, byte[] secretKey) {
        return aes.decryptWithIVBase64(body, secretKey, Const.IV_LENGTH);
    }

}
