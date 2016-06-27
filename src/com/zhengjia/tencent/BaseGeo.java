package com.zhengjia.tencent;

import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.zhengjia21.util.Common;

public class BaseGeo {
	
	String url="";
	String key=Common.getKey();
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}	
	public String construct()
	{
		return "?key="+key;
	}
	public boolean isAvailable(JSONObject jo)
	{
		boolean ret = false;
		String status="status";
		try {
			if(jo.getInt(status)!=0)
			{
				return ret;
			}
			else
			{
				ret = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}
