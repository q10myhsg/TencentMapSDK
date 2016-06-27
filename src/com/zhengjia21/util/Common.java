package com.zhengjia21.util;

public class Common {
	
	public static final String[] KEYS={"EERBZ-F3GAW-SLVRJ-REIDV-4SYW5-Y2BK6","NRMBZ-FVHR5-I2ZIT-QGTVI-45RGO-W5BIB","FGBBZ-LXIRQ-EIN5W-G6TB5-SDDI7-U4BQ6"};
	
	//地址解析
	public static final String geocoderUrl="http://apis.map.qq.com/ws/geocoder/v1";
	
	//search
	public static final String searchUrl="http://apis.map.qq.com/ws/place/v1/search";
	
	
	public static final int pageSize=20;
	
	private static int keyIndex=0;
	public static String getKey()
	{
		 keyIndex++;
		 if(keyIndex>=KEYS.length)
		 {
			 keyIndex-=KEYS.length;
		 }
		 return KEYS[keyIndex];
	}
	
	public static double distance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2*R*Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)*Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
	public static void main(String...strings)
	{
		System.out.println(distance(116.0,40.0,  116.01175,40.0));
	}
}
