package com.zhengjia.map;

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

import com.zhengjia21.util.string.StringUtil;

/**
 * 
 *  this is a demo class
 *  
 * */
public class ClassRequest
{
	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://apis.map.qq.com/ws/geocoder/v1/?region=%E5%8C%97%E4%BA%AC&address=%E5%8C%97%E4%BA%AC%E5%B8%82%E6%B5%B7%E6%B7%80%E5%8C%BA%E5%BD%A9%E5%92%8C%E5%9D%8A%E8%B7%AF%E6%B5%B7%E6%B7%80%E8%A5%BF%E5%A4%A7%E8%A1%9774%E5%8F%B7&key=EERBZ-F3GAW-SLVRJ-REIDV-4SYW5-Y2BK6");		
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			HttpEntity e =response.getEntity();
			InputStream is =e.getContent();
			String s =StringUtil.inputStream2String(is);
			JSONObject jo =new JSONObject(s);
			System.out.println(jo);
			is.close();
		} finally {
		    response.close();
		}
	}
}