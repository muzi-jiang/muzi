package com.muzi.app.login.util;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;

public class MD5APPUtil {
	
//  
//	  public static void main(String[] args) {
//		    String ss = MD5APPUtil.generate("admin");
//		    System.out.println(ss);
//		    System.out.println("是否是同一字符串："+MD5APPUtil.verify("admin",ss));
//		  
////		    247d32d9940da0c92873dc61e6e72e35490b61b34bc2bd46
////		    e6a718d0ac6b396870a8df8b22d765c3fc7b89a33c09f904
////		    28fe4b24d52328d869f2fb0788f16ac3fd1f58eb7801fc0a  
//	  }
//
//	
	/**
	 * MD5加密方式
	 * @param password
	 * @return
	 */
	public static String generate(String password) {  
        Random r = new Random();  
        StringBuilder sb = new StringBuilder(16);  
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));  
        int len = sb.length();  
        if (len < 16) {  
            for (int i = 0; i < 16 - len; i++) {  
                sb.append("0");  
            }  
        }  
        /**
         * 生成加密的盐
         */
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
