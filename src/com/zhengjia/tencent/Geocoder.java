package com.zhengjia.tencent;

import home.common.util.file.MyFileReader;
import home.common.util.file.MyFileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.zhengjia21.util.Common;
import com.zhengjia21.util.string.StringUtil;

public class Geocoder  extends BaseGeo{

	String text="";
	String region="";
	public Geocoder()
	{
		url=Common.geocoderUrl;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = URLEncoder.encode(text);
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = URLEncoder.encode(region);
	}

	public JSONObject  excute() throws JSONException, ClientProtocolException, IOException
	{
		
		String s="";
		JSONObject ret=null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(construct());		
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			HttpEntity e =response.getEntity();
			InputStream is =e.getContent();
			s =StringUtil.inputStream2String(is);
						
			JSONObject jo =new JSONObject(s);
			if(isAvailable(jo))
			{
				jo=jo.getJSONObject("result").getJSONObject("location");
			}
			jo.put("name", URLDecoder.decode(text));
			
			ret =jo;
			
		} finally {
		    response.close();
		}		
		return ret;
	}
	
	@Override
	public String construct() {	
		String keys =super.construct();
		String temp="";
		if(text.length()>0)
		{
			temp+="&address="+text;
		}
		if(region.length()>0)
		{
			temp+="&region="+region;
		}		
		return url+keys+temp;	
	}
	
	
	/*
	 * 
	 * 	如果poi不存在腾讯会返回  {"name":"xxxx","lng":116.405285,"lat":39.904989}
	 *  
	 * */
	
	public static void main(String[] args) throws ClientProtocolException, JSONException, IOException {
		// TODO Auto-generated method stub%3Fkey%3DEERBZ
		
		MyFileReader mfr = new MyFileReader("resources/fang.txt");
		MyFileWriter mfw = new MyFileWriter("resources/fang-result-address.txt");
		Iterator<String> it = mfr.getContent().iterator();		
		int count=0;
		while(it.hasNext())
		{
			String line = it.next();
			String[] arr = line.split("\t");
			String name = arr[0].trim();
			String address = arr[1].trim();
			//			String text =line.trim();
			Geocoder g=  new Geocoder();
			g.setRegion("北京");
			g.setText(address);
			//System.out.println(g.excute());
			
			JSONObject jo=  g.excute();
			if(jo.getDouble("lng")==116.405285&&jo.getDouble("lat")==39.904989)
			{
				count++;
				System.out.println(name+"<br/>");
				mfw.write(name+"<br/>"+"\r\n");
			}
			else
			{
				mfw.write("<a href=\"http://apis.map.qq.com/ws/geocoder/v1/?location="+jo.getDouble("lat")+","+jo.getDouble("lng")+"&key=EERBZ-F3GAW-SLVRJ-REIDV-4SYW5-Y2BK6&get_poi=1\" target=_blank >"+name+"</a>  <br/>"+"\r\n");
				System.out.println("<a href=\"http://apis.map.qq.com/ws/geocoder/v1/?location="+jo.getDouble("lat")+","+jo.getDouble("lng")+"&key=EERBZ-F3GAW-SLVRJ-REIDV-4SYW5-Y2BK6&get_poi=1\" target=_blank >"+name+"</a>  <br/>");
			}
		}	
		System.out.println("error count: "+count);
		mfw.write("error count: "+count+"\r\n");
		mfw.close();
       // "lng": 116.436642,
       // "lat": 39.97129
	}

}
