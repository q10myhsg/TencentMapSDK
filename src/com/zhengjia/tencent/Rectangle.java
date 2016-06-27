package com.zhengjia.tencent;

public class Rectangle extends Boundary {
	String type="rectangle";
	float leftlat=0;
	float leftlng=0;
	float rightlat=0;
	float rightlng=0;
	
	public void setWS(float lat, float lng)
	{
		leftlat=lat;
		leftlng=lng;
	}
	public void setEN(float lat, float lng)
	{
		rightlat=lat;
		rightlng=lng;
	}
	
	@Override
	public String toString() {
		return "boundary="+type+"("+lat+","+ lng+","+radius+")";
	}
}
