package com.dd1yyg.shop.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * 加密的工具类
 */
public class EncryptUtils {
	
	
	/** md5加密 */
	public static String md5(String content){
		if(content != null){
			return md5(content.getBytes());
		}
		return "null";
	}
	
	/** md5加密 */
	public static String md5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data, 0, data.length);
            byte[] digest = md.digest();
            return convertHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertHashString(byte[] hashBytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : hashBytes) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

}
