package com.lehuipay.leona.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class CommonUtil {

    /**
     * trim string, include convert null to ""; if str1 is null, will return "";
     *
     * @param str1 入参
     * @return return str1 == null ? "" : str1
     */
    public static String NVLL(String str1) {
        return str1 == null ? "" : str1;
    }

    public static Boolean isEmpty(String str1) {
        return NVLL(str1).isEmpty();
    }

    /**
     * "" 等同于null处理
     *
     * @param str1 参数1
     * @param str2 参数2
     * @return equals(null, "") == true
     */
    public static Boolean equals(String str1, String str2) {
        return NVLL(str1).equals(NVLL(str2));
    }

    private static Random random = new Random();

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    public static String readPemFile2String(String fileName) throws IOException {
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String(filecontent, "UTF-8").
                replace("-----BEGIN PRIVATE KEY-----", "").
                replace("-----END PRIVATE KEY-----", "").
                replace("-----BEGIN PUBLIC KEY-----", "").
                replace("-----END PUBLIC KEY-----", "").trim();
    }

    /**
     * 生成随即串
     *
     * @param min 最小程度
     * @param max 最大长度
     * @return 随机串
     */
    public static String randomStr(int min, int max) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        final int length = random.nextInt(max - min + 1) + min;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成固定长度随机串
     *
     * @param length
     */
    public static String randomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    private static Base64 base64 = new Base64();

    public static byte[] base64Decode(String encrypt) {
        return base64.decode(encrypt);
    }

    public static String base64Encode(byte[] data) {
        return base64.encodeToString(data);
    }
}
