package com.muzi.test.util;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA 加密解密
 * @author Administrator
 *
 */
public class RSAUtil {
	/**
	 * 编码
	 * @param bytes
	 * @return
	 */
	public static String encode(byte[] bytes){
		return new String(Base64.encodeBase64(bytes));
	}
	
	/**
	 * base64 解码
	 * @param bytes
	 * @return
	 */
    public static String decode(byte[] bytes) {  
        return new String(Base64.decodeBase64(bytes));  
    } 
    
    
	public static void main(String[] args) {
		String string = "123456789";
		String encode = encode(string.getBytes());
		
		String decode = decode(encode.getBytes());
		
		System.out.println(decode);
		System.out.println(encode);
	}
}
