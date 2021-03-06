package com.bocs.util;

public class CommonUtil {

	
	/**
	 * 根据两点的坐标，计算两点的距离
	 * @param longt1
	 * @param lat1
	 * @param longt2
	 * @param lat2
	 * @return 两点间的距离（单位米）
	*/
	public static  double getDistance(double longt1, double lat1, double longt2, double lat2){
		double PI = 3.14159265358979323; //圆周率
	    double R = 6371229;              //地球的半径
		double x,y,distance;
        x=(longt2-longt1)*PI*R*Math.cos( ((lat1+lat2)/2) *PI/180)/180;
        y=(lat2-lat1)*PI*R/180;
        distance=Math.hypot(x,y);
        return distance;
    }
}
