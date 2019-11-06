package com.muzi.system.util;
import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
/**
 * 编写MD5加密方式
 * @author Administrator
 *
 */
public class MD5Utils {
	
	/**  
     * 获取十六进制字符串形式的MD5摘要  
     */  
    private static String md5Hex(String src) {  
        try {  
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
            byte[] bs = md5.digest(src.getBytes());  
            return new String(new Hex().encode(bs));  
        } catch (Exception e) {  
            return null;  
        }  
    } 
	
    /**
     * 第一种加密方式，使用userName作盐  的盐值加密方式
     * @param userName
     * @param passWord
     * @return
     */
	public static String generateByuserName(String userName,String passWord){
		passWord = md5Hex(passWord + userName);
		char[] cs = new char[48];  
        for (int i = 0; i < 48; i += 3) {  
            cs[i] = passWord.charAt(i / 3 * 2);  
            char c = userName.charAt(i / 3);  
            cs[i + 1] = c;  
            cs[i + 2] = passWord.charAt(i / 3 * 2 + 1);  
        }  
        return new String(cs);
	}
	
	
	/**
	 * 第二种加密方式  使用随机数作为盐 进行盐值加密
	 * @param password
	 * @return
	 */
	 public static String generateByRandom(String password) {  
         Random r = new Random();  
         StringBuilder sb = new StringBuilder(16);  
         sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));  
         int len = sb.length();  
         if (len < 16) {  
             for (int i = 0; i < 16 - len; i++) {  
                 sb.append("0");  
             }  
         }  
         String salt = sb.toString();  
         password = md5Hex(password + salt);  
         char[] cs = new char[48];  
         for (int i = 0; i < 48; i += 3) {  
             cs[i] = password.charAt(i / 3 * 2);  
             char c = salt.charAt(i / 3);  
             cs[i + 1] = c;  
             cs[i + 2] = password.charAt(i / 3 * 2 + 1);  
         }  
         return new String(cs);  
     }
	 
	 
	 /**
      * 密码比较
      * @param password
      * @param md5
      * @return
      */
     public static boolean verify(String password, String md5) {  
         char[] cs1 = new char[32];  
         char[] cs2 = new char[16];  
         for (int i = 0; i < 48; i += 3) {  
             cs1[i / 3 * 2] = md5.charAt(i);  
             cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);  
             cs2[i / 3] = md5.charAt(i + 1);  
         }  
         String salt = new String(cs2);  
         return md5Hex(password + salt).equals(new String(cs1));  
     }  
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
