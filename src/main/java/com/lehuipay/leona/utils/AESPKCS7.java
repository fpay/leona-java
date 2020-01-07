package com.lehuipay.leona.utils;

import com.lehuipay.leona.exception.LeonaErrorCodeEnum;
import com.lehuipay.leona.exception.LeonaRuntimeException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

/**
 * AES128 算法
 * <p>
 * CBC 模式
 * <p>
 * PKCS7Padding 填充模式
 * <p>
 * CBC模式需要添加一个参数iv
 * <p>
 * 介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
 * 要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */
public class AESPKCS7 {
    // 算法名称
    final String KEY_ALGORITHM = "AES";
    // 加解密算法/模式/填充方式
    final String algorithmStr = "AES/CBC/PKCS7Padding";
    //
    private Key key;
    private Cipher cipher;

    public void init(byte[] keyBytes) {

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + 1;
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(algorithmStr, "BC");
        } catch (Exception e) {
            throw new LeonaRuntimeException(e);
        }
    }

    /**
     * 加密方法, 不对结果进行base64二次加密
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @param iv       向量
     * @return
     */
    public byte[] encrypt(byte[] content, byte[] keyBytes, byte[] iv) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(content);
        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new LeonaRuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new LeonaRuntimeException("AES秘钥非法", e);
        }
        return encryptedText;
    }

    /**
     * 加密方法
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @param iv       向量
     * @return base64Encode(iv + encryptedBody)
     */
    public String encryptWithIVBase64(byte[] content, byte[] keyBytes, byte[] iv) {
        final byte[] encrypted;
        try {
            encrypted = encrypt(content, keyBytes, iv);
        } catch (LeonaRuntimeException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.ENCRYPTION_FAIL, e);
        }
        byte[] tmp = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, tmp, 0, iv.length);
        System.arraycopy(encrypted, 0, tmp, iv.length, encrypted.length);
        return new Base64().encodeToString(tmp);
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param iv            向量
     * @return
     */
    public byte[] decrypt(byte[] encryptedData, byte[] keyBytes, byte[] iv) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new LeonaRuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new LeonaRuntimeException("AES秘钥非法", e);
        }
        return encryptedText;
    }

    /**
     * 解密方法
     *
     * @param encryptedDataBase64 要解密的字符串, 格式为base64Encode(iv + encryptedBody)
     * @param keyBytes            解密密钥
     * @param ivLength            附加到密文最前方的iv长度
     * @return
     */
    public byte[] decryptWithIVBase64(String encryptedDataBase64, byte[] keyBytes, int ivLength) {
        byte[] encrypted = new Base64().decode(encryptedDataBase64); //先用base64解密

        final byte[] iv = CommonUtil.subBytes(encrypted, 0, ivLength);
        final byte[] encryptedData = CommonUtil.subBytes(encrypted, ivLength, encrypted.length - ivLength);

        try {
            return decrypt(encryptedData, keyBytes, iv);
        } catch (LeonaRuntimeException e) {
            throw new LeonaRuntimeException(LeonaErrorCodeEnum.DECRYPTION_FAIL, e);
        }
    }
}