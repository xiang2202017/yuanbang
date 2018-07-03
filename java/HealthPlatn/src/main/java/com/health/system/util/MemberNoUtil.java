package com.health.system.util;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

public class MemberNoUtil extends AbstractUUIDGenerator {
	public static final MemberNoUtil DEFAULT = new MemberNoUtil();
	private String sep = "";
 
	protected String format(final int intval) {
		final String formatted = Integer.toHexString(intval);
		final StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}
 
	protected String format(final short shortval) {
		final String formatted = Integer.toHexString(shortval);
		final StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}
 
	public Serializable generate(final Object obj) {
		return new StringBuffer(36).append(format(getIP())).append(sep)
				.append(format(getJVM())).append(sep)
				.append(format(getHiTime())).append(sep)
				.append(format(getLoTime())).append(sep)
				.append(format(getCount())).toString();
	}
 
	public void configure(final Properties params) {
		sep = params.getProperty("separator", "");
	}
 
	public static final String generator() {
		return String.valueOf(MemberNoUtil.DEFAULT.generate(null));
	}
 
	public static final String generator(final Object obj) {
		return String.valueOf(MemberNoUtil.DEFAULT.generate(obj));
	}
 
	public static void main(final String[] args) {
		String s= MemberNoUtil.generator();
		System.out.println(s.length());
		System.out.println(MemberNoUtil.generator());
		System.out.println(MemberNoUtil.generator());
		String s2=UUID.randomUUID().toString();
		System.out.println(s2);
		System.out.println(s2.length());
	}
}
