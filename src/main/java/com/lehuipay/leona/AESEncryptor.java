package com.lehuipay.leona;

import com.lehuipay.leona.contracts.SymmetricEncryptor;
import com.lehuipay.leona.utils.AESPKCS7;
import com.lehuipay.leona.utils.CommonUtil;

/**
 * AES加密器
 */
public class AESEncryptor implements SymmetricEncryptor {

    AESPKCS7 aes = new AESPKCS7();

    /**
     * AES加密
     *
     * @param body 待加密数据
     * @param secretKey AES秘钥
     * @return 加密后数据
     * @throws com.lehuipay.leona.exception.LeonaRuntimeException AES加密异常
     */
    @Override
    public String encrypt(byte[] body, String secretKey) {
        final String iv = CommonUtil.randomStr(Const.IV_LENGTH);
        return aes.encryptWithIVBase64(body, secretKey.getBytes(), iv.getBytes());
    }

    /**
     * AES解密
     *
     * @param body 待解密数据
     * @param secretKey AES秘钥
     * @return 解密后的数据
     * @throws com.lehuipay.leona.exception.LeonaRuntimeException AES解密异常
     */
    @Override
    public byte[] decrypt(String body, byte[] secretKey) {
        return aes.decryptWithIVBase64(body, secretKey, Const.IV_LENGTH);
    }

}
