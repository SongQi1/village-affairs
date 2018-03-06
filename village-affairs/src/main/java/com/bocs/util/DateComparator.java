package com.bocs.util;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * 日期比较器。只比较日期，不考虑时间。
 * 按日期排序
 * @author songqi
 *
 */
public class DateComparator implements Comparator<Date>{

	@Override
	public int compare(Date o1, Date o2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(o1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(o2);
		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 < year2){
			return -1;
		}else if(year1 > year2){
			return 1;
		}else {
			return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) ? 0 :
				(cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR) ? -1 : 1);
		}
	}
}
