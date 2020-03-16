package com.lehuipay.leona;

import com.lehuipay.leona.contracts.AsymmetricEncryptor;
import com.lehuipay.leona.utils.RSAEncrypt;
import com.lehuipay.leona.utils.CommonUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA加密器
 */
public class RSAEnctryptor implements AsymmetricEncryptor {

    public RSAEnctryptor(String partnerPriKey, String lhPubKey) {
        if (CommonUtil.isEmpty(partnerPriKey) || CommonUtil.isEmpty(lhPubKey)) {
            throw new IllegalArgumentException("init com.lehuipay.leona.RSAEnctryptor, partnerPriKey and lhPubKey should not be empty");
        }
        this.privateKey = RSAEncrypt.getPrivateKey(partnerPriKey);
        this.publicKey = RSAEncrypt.getPublicKey(lhPubKey);
    }

    private RSAPrivateKey privateKey;

    private RSAPublicKey publicKey;

    /**
     * RSA公钥加密
     *
     * @param data 待加密内容
     * @return 加密后的数据
     * @throws com.lehuipay.leona.exception.LeonaRuntimeException RSA公钥加密异常
     */
    @Override
    public byte[] pubEncode(String data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return RSAEncrypt.publicEncrypt(publicKey, data);
    }

    /**
     * RSA私钥解密
     *
     * @param data 待解密内容
     * @return 解密后的数据
     * @throws com.lehuipay.leona.exception.LeonaRuntimeException RSA私钥解密异常
     */
    @Override
    public byte[] priDecode(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return RSAEncrypt.privateDecrypt(privateKey, data);
    }

    /**
     * RSA签名
     *
     * @param data 待签名内容
     * @return 签名
     * @throws com.lehuipay.leona.exception.LeonaRuntimeException RSA加签异常
     */
    @Override
    public byte[] sign(byte[] data) throws SignatureException, InvalidKeyException {
        return RSAEncrypt.sign(data, privateKey);
    }

    /**
     * RSA验签
     *
     * @param content 待验签内容
     * @param sign 签名
     * @return 验签结果
     * @throws com.lehuipay.leona.exception.LeonaRuntimeException RSA验签异常
     */
    @Override
    public boolean verify(byte[] content, byte[] sign) throws SignatureException, InvalidKeyException {
        return RSAEncrypt.verify(content, sign, publicKey);
    }

}
