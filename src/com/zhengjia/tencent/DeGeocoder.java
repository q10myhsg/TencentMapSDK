package com.zhengjia.tencent;

import java.io.IOException;
import java.io.InputStream;

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

public class DeGeocoder  extends BaseGeo{

	String location="";
	public DeGeocoder()
	{
		url=Common.geocoderUrl;
	}

	public void setLocaiton(String text) {
		location =text;
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
		if(location.length()>0)
		{
			temp+="&location="+location;
		}
		return url+keys+temp;	
	}
	
	
	
	
	public static void main(String[] args) throws ClientProtocolException, JSONException, IOException {
		// TODO Auto-generated method stub%3Fkey%3DEERBZ
		
			DeGeocoder g=  new DeGeocoder();
			g.setLocaiton("39.97129,116.436642");
			System.out.println(g.excute());
		}	
}

