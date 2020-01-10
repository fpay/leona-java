package com.lehuipay.leona.utils;

import com.lehuipay.leona.exception.LeonaRuntimeException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMAC {

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    /**
     * HMAC-SHA256签名算法
     *
     * @param key     签名秘钥
     * @param content 内容
     * @return 签名
     * @throws LeonaRuntimeException 签名错误
     */
    public static byte[] hmacSHA256(byte[] key, byte[] content) throws LeonaRuntimeException {
        try {
            Mac hmacSha256 = Mac.getInstance(HMAC_ALGORITHM);
            hmacSha256.init(new SecretKeySpec(key, 0, key.length, HMAC_ALGORITHM));
            byte[] hmacSha256Bytes = hmacSha256.doFinal(content);
            return hmacSha256Bytes;
        } catch (NoSuchAlgorithmException e) {
            throw new LeonaRuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new LeonaRuntimeException("HMAC秘钥格式非法");
        }
    }

    /**
     * HEX编码数据
     *
     * @param src 待编码的数据
     * @return HEX编码后的数据
     */
    public static String encode(byte[] src) {
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < src.length; n++) {
            strHex = Integer.toHexString(src[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }
}
