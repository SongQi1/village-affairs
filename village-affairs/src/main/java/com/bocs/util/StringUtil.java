package com.bocs.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class StringUtil {

	/**
	 * 产生长度为20位的唯一字符串。
	 * 规则是：yyMMddHHmmssSSS+5位随机数
	 * @return
	 */
	public static String getUniqueStr() {
		String trans_date = new SimpleDateFormat("yyMMddHHmmssSSS")
				.format(new Date());
		String random_int = getRandom(99999, 5);
		return trans_date + random_int;
	}

	public static String getRandom(int i, int len) {
		return (addZeroOnTheLeft((int) (Math.random() * i), len));
	}
	
	/**
	 * 在数字的左边加0，使之达到len长度
	 * @param i 数字
	 * @param len 长度
	 * @return
	 */
	public static String addZeroOnTheLeft(int i, int len) {
		String str = String.valueOf(i);
		String s = str;
		for (; len > str.length(); len--) {
			s = "0" + s;
		}
		return s;
	}
	
	
}
