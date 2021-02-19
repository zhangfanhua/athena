package com.xx.cortp.utils;

import java.security.MessageDigest;

public class Md5Util {
	
	public static String encode(String str) {
		try {

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update((str).getBytes("UTF-8"));
		byte b[] = md5.digest();
		 
		int i;
		StringBuffer buf = new StringBuffer("");
		 
		for(int offset=0; offset<b.length; offset++){
			i = b[offset];
			if(i<0){
				i+=256;
			}
			if(i<16){
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		 
		return buf.toString().toUpperCase();
		} catch (Exception e) {
			return "";
		}
	}
	
}
