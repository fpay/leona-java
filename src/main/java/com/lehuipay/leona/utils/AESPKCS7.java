package com.lehuipay.leona.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class AESPKCS7 {
    // 算法名称
    final String KEY_ALGORITHM = "AES";
    // 加解密算法/模式/填充方式
    final String algorithmStr = "AES/CBC/PKCS5Padding";

    /**
     * 加密方法, 不对结果进行base64二次加密
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @param iv       向量
     * @return AES加密后的数据
     * @throws InvalidKeyException
     */
    public byte[] encrypt(byte[] content, byte[] keyBytes, byte[] iv) throws InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance(algorithmStr);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
            return cipher.doFinal(content);
        } catch (InvalidAlgorithmParameterException | BadPaddingException | NoSuchPaddingException |
                IllegalBlockSizeException | NoSuchAlgorithmException e) {
            // 该异常理应永远不会抛出
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密方法
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @param iv       向量
     * @return base64Encode(iv + encryptedBody)
     * @throws InvalidKeyException
     */
    public String encryptWithIVBase64(byte[] content, byte[] keyBytes, byte[] iv) throws InvalidKeyException {
        final byte[] encrypted = encrypt(content, keyBytes, iv);
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
     * @return AES解密后的数据
     * @throws InvalidKeyException
     */
    public byte[] decrypt(byte[] encryptedData, byte[] keyBytes, byte[] iv) throws InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance(algorithmStr);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
            return cipher.doFinal(encryptedData);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | BadPaddingException |
                NoSuchPaddingException | IllegalBlockSizeException e) {
            // 该异常理应永远不会抛出
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密方法
     *
     * @param encryptedDataBase64 要解密的字符串, 格式为base64Encode(iv + encryptedBody)
     * @param keyBytes            解密密钥
     * @param ivLength            附加到密文最前方的iv长度
     * @return AES机密后的数据
     * @throws InvalidKeyException
     */
    public byte[] decryptWithIVBase64(String encryptedDataBase64, byte[] keyBytes, int ivLength) throws InvalidKeyException {
        byte[] encrypted = new Base64().decode(encryptedDataBase64); //先用base64解密

        final byte[] iv = CommonUtil.subBytes(encrypted, 0, ivLength);
        final byte[] encryptedData = CommonUtil.subBytes(encrypted, ivLength, encrypted.length - ivLength);
        return decrypt(encryptedData, keyBytes, iv);
    }
}