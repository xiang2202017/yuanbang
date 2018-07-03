package com.health.system.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CopyOfMemberNoUtil {
	private static int i = 0;
	
	private static long tmpID = 0;
	 
	 private static boolean tmpIDlocked = false;
	 
	 private static long getUniqueId() {
	  long ltime = 0;
	  while (true) {
	   if (tmpIDlocked == false) {
	    tmpIDlocked = true;
	    ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS")
	      .format(new Date()).toString()) * 10000;
	    if (tmpID < ltime) {
	     tmpID = ltime;
	    } else {
	     tmpID = tmpID + 1;
	     ltime = tmpID;
	    }
	    tmpIDlocked = false;
	    return ltime;
	   }
	  }
	 }
	
	/***
	 * 获取当前系统时间戳 并截取 
	 * @return
	 */
	private synchronized static String getUnixTime(){
		try {
			Thread.sleep(10);//线程同步执行，休眠10毫秒 防止卡号重复
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		i++;i=i>100?i%10:i;
		return ((System.currentTimeMillis()/100)+"").substring(1)+(i%10);
	}
}
