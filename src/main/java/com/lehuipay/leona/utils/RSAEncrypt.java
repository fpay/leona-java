package com.lehuipay.leona.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAEncrypt {

    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String RSA_ALGORITHM = "RSA"; // ALGORITHM ['ælgərɪð(ə)m] 算法的意思
    private static final String RSA_HAS256_SIGNATURE = "SHA256WithRSA";

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥字符串
     * @throws IllegalArgumentException 公钥格式非法
     */
    public static RSAPublicKey getPublicKey(String publicKeyStr) {
        try {
            byte[] buffer = new Base64().decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException | NullPointerException e) {
            throw new IllegalArgumentException("公钥非法", e);
        }
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 私钥数据字符串
     * @return
     * @throws IllegalArgumentException 私钥格式非法
     */
    public static RSAPrivateKey getPrivateKey(String privateKeyStr) {
        try {
            byte[] buffer = new Base64().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException | NullPointerException e) {
            throw new IllegalArgumentException("私钥非法", e);
        }
    }

     /**
     * 公钥加密
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] publicEncrypt(RSAPublicKey publicKey, String plainTextData)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (publicKey == null) {
            throw new IllegalArgumentException("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(RSA_ALGORITHM);
            // cipher= Cipher.getInstance(RSA_ALGORITHM, new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(plainTextData.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // 该异常理应永远不会抛出
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥加密过程
     *
     * @param privateKey    私钥
     * @param plainTextData 明文数据
     * @return 私钥加密内容
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] privateEncrypt(RSAPrivateKey privateKey, String plainTextData)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (privateKey == null) {
            throw new IllegalArgumentException("加密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(plainTextData.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] privateDecrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (privateKey == null) {
            throw new IllegalArgumentException("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(RSA_ALGORITHM);
            // cipher= Cipher.getInstance(RSA_ALGORITHM, new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(cipherData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // 该异常理应永远不会抛出
            throw new RuntimeException(e);
        }
    }

    /**
     * 公钥解密过程
     *
     * @param publicKey  公钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] publicDecrypt(RSAPublicKey publicKey, byte[] cipherData)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (publicKey == null) {
            throw new IllegalArgumentException("解密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance(RSA_ALGORITHM);
            // cipher= Cipher.getInstance(RSA_ALGORITHM, new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(cipherData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // 该异常理应永远不会抛出
            throw new RuntimeException(e);
        }
    }

    /**
     * rsa签名
     *
     * @param data       原始数据
     * @param privateKey 私钥
     * @return 数据的签名
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] sign(byte[] data, RSAPrivateKey privateKey) throws InvalidKeyException, SignatureException {
        try {
            Signature signature = Signature.getInstance(RSA_HAS256_SIGNATURE);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            // never
            throw new RuntimeException(e);
        }
    }

    /**
     * rsa验签
     *
     * @param content   数据
     * @param sign      数据签名
     * @param publicKey 公钥
     * @return RSA验签结果
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(byte[] content, byte[] sign, RSAPublicKey publicKey) throws InvalidKeyException, SignatureException {
        //获取Signature实例，指定签名算法(与之前一致)
        Signature signature = null;
        try {
            signature = Signature.getInstance(RSA_HAS256_SIGNATURE);
            //加载公钥
            signature.initVerify(publicKey);
            //更新原数据
            signature.update(content);
            //公钥验签（true-验签通过；false-验签失败）
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException e) {
            // never
            throw new RuntimeException(e);
        }
    }
}
