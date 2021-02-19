package com.xx.cortp.utils;

public class BitUtil {

	public static final int H5=1; // 0001 
	public static final int MINI=2; // 0010 
	public static final int APP=4; // 0100 
	public static final int OTHER=8; //1000
	
	public static int add(int a,int b){
		return a | b;
	}
	
	public static int del(int ab,int b){
		return ab & (~b);
	}
	
	public static boolean has(int ab,int b){
		return (ab & b) == b;
	}
	
	public static void main(String[] args) {
		
		
		String[] s ={"0001","0011","0101","0111","1001","1011","1101","1111"};
		String[] s1 ={"0010","0011","0110","1010","1011","1110","1111"};
		for (String string : s1) {
			
			System.out.println(Integer.parseInt(string, 2));
		}
	}
	
}
