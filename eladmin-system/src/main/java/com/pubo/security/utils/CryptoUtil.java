package com.pubo.security.utils;

/**
 * @Description:
 * @Author:pubo
 * @Date:2023/12/318:55
 */


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class CryptoUtil {
    /***
     * key和iv值需要和前端一致
     */
    public static final String KEY = "smLeGV63judEcxKU";

    public static final String IV = "lFbGSVuAmZqtPCLa";

    public CryptoUtil() {
    }

    /**
     * 加密方法
     *
     * @param data 要加密的数据
     * @param key  加密key
     * @param iv   加密iv
     * @return 加密的结果（加密失败返回null）
     */
    public static String encrypt(String data, String key, String iv) {
        try {
            //"算法/模式/补码方式"NoPadding PkcsPadding
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(iv.getBytes()));

            byte[] encrypted = cipher.doFinal(plaintext);
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("加密异常", e);
            return null;
        }
    }

    /**
     * 解密方法
     *
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv   解密iv
     * @return 解密的结果（解密失败返回原始值）
     */
    public static String desEncrypt(String data, String key, String iv) {
        try {
            System.out.println("enPassword:"+ data);
            byte[] encrypted = new Base64().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(iv.getBytes()));
            byte[] original = cipher.doFinal(encrypted);
            System.out.println("enPassword:"+ new String(original).trim());
            return new String(original).trim();
        } catch (Exception e) {
            log.error("解密异常", e);
            return data;
        }
    }

}


