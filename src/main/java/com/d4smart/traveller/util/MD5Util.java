package com.d4smart.traveller.util;

import java.security.MessageDigest;

/**
 * Created by d4smart on 2018/3/29 15:09
 */
public class MD5Util {

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 返回大写MD5
     * @param origin 要加密的原始字符串
     * @param charsetName 加密的编码
     * @return 大写的MD5字符串
     */
    private static String MD5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception exception) {
        }
        return resultString.toUpperCase();
    }

    /**
     * 以utf-8编码使用MD5加密字符串
     * @param origin 要加密的字符串
     * @return 加密后的MD5字符串
     */
    public static String MD5EncodeUtf8(String origin) {
        origin = PropertiesUtil.getProperty("password.salt.pre", "") + origin + PropertiesUtil.getProperty("password.salt.suf", "");
        return MD5Encode(origin, "utf-8");
    }
}
