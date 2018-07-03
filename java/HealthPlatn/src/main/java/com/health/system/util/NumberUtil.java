package com.health.system.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberUtil {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/**
	 * 生成会员卡号：10位
	 * 生成规则：（时间戳+随机数+会员名或编号） 取MD5前10位
	 */
	public static String getMemberCarNo(String user) {
		String time = format.format(new Date());
		String random = getRandom(5);
		String carNo = getMD5(time+random+user, 10);
		return carNo;
	}
	
	/**
	 * 生成订单编号(16位)
	 * 使用用户编号解决并发问题，同一用户同一时间只会下单一次
	 * 
	 * 生成规则：时间戳（分秒毫秒）+ 用户Id +随机数5位 
	 */
	public static String getOrderNumber(String userId){
		String time = getMD5(format.format(new Date()).substring(10,17),7);
		String userStr = getMD5(userId,4);
		String random = getRandom(5);
		return time+userStr+random;
	}
	
	/**
	 * 生成交易支付编号：28位
	 * 
	 * 生成规则：时间（年月日秒毫秒：yyyyMMddHHmmssSSS）+商品Id + 用户Id +随机数3位
	 */
	public static String getDealNumber(String productId,String userId){
		String time = format.format(new Date());
		String productStr  = getMD5(productId,4);
		String userStr = getMD5(userId,4);
		String random = getRandom(3);
		return time+productStr+userStr+random;
	}
	
	public static String getRandom(int n){
		String numStr=String.valueOf(Math.random());
        return numStr.substring(2,n+2);
	}
	
	/**
	 * 将输入的字符串MD5后取前n位
	 */
	public static String getMD5(String input,int n) {
		byte[] source = input.getBytes();
        String s = null;  
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符,去掉字母
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '1', '2', '3', '5', '7', '9'};  
        try {  
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");  
            md.update(source);  
            byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，  
            // 用字节表示就是 16 个字节  
            char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，  
            // 所以表示成 16 进制需要 32 个字符  
            int k = 0;                                // 表示转换结果中对应的字符位置  
            for (int i = 0; i < 16; i++) {    // 从第一个字节开始，对 MD5 的每一个字节  
                // 转换成 16 进制字符的转换  
                byte byte0 = tmp[i];  // 取第 i 个字节  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,  
                // >>> 为逻辑右移，将符号位一起右移  
                str[k++] = hexDigits[byte0 & 0xf];   // 取字节中低 4 位的数字转换  
            }  
            s = new String(str);  // 换后的结果转换为字符串  
   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return s.substring(0,n);  
    }  
	
	public static void main(String[] args) {
		System.out.println(getOrderNumber("111"));
		System.out.println(getOrderNumber("11"));
		System.out.println(getOrderNumber("1"));
		
		System.out.println(getDealNumber("11","1"));
		System.out.println(getDealNumber("1","1"));
		System.out.println(getDealNumber("111","11"));
		
		System.out.println(getMemberCarNo("莫胜吕"));
		System.out.println(getMemberCarNo("莫胜"));
		System.out.println(getMemberCarNo("moshenglv"));
		System.out.println(getMemberCarNo("11234455"));
	}
}

