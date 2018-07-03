package com.health.system.test;

import java.math.BigInteger;

import com.health.system.util.RightsHelper;

public class Mytest { 
	
	public static void main(String[] args){
		String[] strs = {"1","2","3","4","5","6"};
		BigInteger bi = RightsHelper.sumRights(strs);
		System.out.println(bi.toString());
	}
}
