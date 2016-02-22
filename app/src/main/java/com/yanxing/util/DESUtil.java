package com.yanxing.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES加密
 * Created by lishuangxiang on 2016/2/2.
 */
public class DESUtil {

    public static final String KEY = "yan_xing";

    /**
     * 解密
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String message){
        return decrypt(message, KEY);
    }

    /**
     * 解密
     * @param message
     * @param key 8字节长度
     * @return
     * @throws Exception
     */
    public static String decrypt(String message, String key){
        byte[] byteSrc = Base64.decode(message.getBytes(), Base64.DEFAULT);
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(byteSrc);
            return new String(retByte);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 加密
     * @param message
     * @return
     * @throws Exception
     */
    public static String encrypt(String message){
        try {
            return encrypt(message,KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 加密
     * @param message
     * @param key 8字节长度
     * @return
     * @throws Exception
     */
    public static String encrypt(String message, String key){
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptByte = cipher.doFinal(message.getBytes());
            return new String(Base64.encode(encryptByte, Base64.DEFAULT));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
