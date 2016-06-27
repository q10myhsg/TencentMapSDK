package com.zhengjia.tencent;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.zhengjia21.util.string.StringUtil;

import weibo4j.org.json.JSONException;

public class CityService extends  BaseGeo{
	public static final String URL = "http://apic.map.qq.com/";
	
	private float lat;
	private float lng;
	
	@Override
	public String construct() {	
		
//      http://apic.map.qq.com/?qt=pos&tp=lonlat&wd=116.397128%2C41.916527&key=FGBBZ-LXIRQ-EIN5W-G6TB5-SDDI7-U4BQ6&output=jsonp&pf=jsapi&ref=jsapi&cb=qq.maps._svcb3.city_service_0
		
		String keys =URL+super.construct();
		String temp=keys+"&"+"qt=pos&tp=lonlat&pf=jsapi&ref=jsapi&cb=qq.maps._svcb3.city_service_0&"+getLatLng();		
		System.out.println(temp);
		return temp;
	}
	
	private String getLatLng(){
		StringBuffer sb = new StringBuffer("&wd=");
		sb.append(lng);
		sb.append("%2C");
		sb.append(lat);
		return sb.toString();
	}
	
	
	public String singleRequest(CloseableHttpClient httpclient) throws ClientProtocolException, IOException, JSONException, InterruptedException
	{
		
		HttpGet httpget = new HttpGet(construct());		
		CloseableHttpResponse response = httpclient.execute(httpget);		
		HttpEntity e =response.getEntity();
		InputStream is =e.getContent();
		String s =StringUtil.inputStream2String(is,"gb18030");
//		String s =StringUtil.inputStream2String(is);	
		response.close();
		httpget.completed();
		Thread.sleep(10);
		return s.toString();	
	}


	public float getLat() {
		return lat;
	}


	public void setLat(float lat) {
		this.lat = lat;
	}


	public float getLng() {
		return lng;
	}


	public void setLng(float lng) {
		this.lng = lng;
	}
}
