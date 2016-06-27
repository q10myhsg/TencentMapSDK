package com.zhengjia.tencent;

import home.common.util.file.MyFileReader;
import home.common.util.file.MyFileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.zhengjia21.util.Common;
import com.zhengjia21.util.string.StringUtil;

public class Search extends BaseGeo {
	String keyword="";
	String oriKeyWord="";
	public Search()
	{
		setUrl(Common.searchUrl);
	}
	public void setKeyword(String key)
	{
		oriKeyWord=key;
		keyword=URLEncoder.encode(key);		
	}
	Boundary bd;
	String name;
	int page_size=Common.pageSize;
	int page_index=1;
	
	JSONObject sourcePOI;
	
	
	
	public JSONObject getSourcePOI() {
		return sourcePOI;
	}
	public void setSourcePOI(JSONObject sourcePOI) {
		this.sourcePOI = sourcePOI;
	}
	public List<String>  excute() throws JSONException, ClientProtocolException, IOException, InterruptedException
	{
		List ret=new LinkedList<String>();
		CloseableHttpClient httpclient = HttpClients.createDefault();		
		JSONArray ja = new JSONArray();
		try {
			while(page_index>0)
			{
				ret.addAll(singleRequest(httpclient));
			}
			page_index=1;
		} finally {		    
		    httpclient.close();
		}		
		return ret;
	}
	
	private List<String> singleRequest(CloseableHttpClient httpclient) throws ClientProtocolException, IOException, JSONException, InterruptedException
	{
		List<String> ret = new LinkedList();
		HttpGet httpget = new HttpGet(construct());		
		CloseableHttpResponse response = httpclient.execute(httpget);		
		HttpEntity e =response.getEntity();
		InputStream is =e.getContent();
		String s =StringUtil.inputStream2String(is,"utf-8");
		JSONObject jo =new JSONObject(s);
		if(isAvailable(jo))
		{
			JSONArray temp=jo.getJSONArray("data");
			if(page_size*page_index<jo.getInt("count"))
			{
				page_index++;
			}
			else
			{
				page_index=0;
			}
			for(int i=0;i<temp.length();i++)
			{
				JSONObject j=temp.getJSONObject(i);
				j.put("sourcePOI", sourcePOI);
				j.put("keyword", this.oriKeyWord);
				ret.add(j.toString());
			}
		}
		response.close();
		httpget.completed();
		Thread.sleep(10);
		return ret;
		
	}
	
	public Boundary getBg() {
		return bd;
	}

	public void setBg(Boundary bg) {
		this.bd = bg;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public int getPage_index() {
		return page_index;
	}

	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	public String getKeyword() {
		return keyword;
	}

	@Override
	public String construct() {	
		String keys =super.construct();
		String temp="&"+bd.toString();
		temp+="&keyword="+keyword;
		temp= url+keys+temp+constructPage();
		System.out.println(temp);
		return temp;
	}
	public String  constructPage()
	{
		String ret="&page_size="+page_size+"&page_index="+page_index;
		return ret;
	}
	
	public static void main(String[] args) 
	{
		MyFileReader mfr = new MyFileReader("resources/mallgeo.txt");
		MyFileReader mfrKeywords = new MyFileReader("resources/allkeywords.txt");
		Iterator<String> it = mfr.getContent().iterator();
		List<String> keywords=  mfrKeywords.getContent();
		while(it.hasNext())
			
		{	
			String line = it.next();
			JSONObject jo;
			try {
				jo = new JSONObject(line);
				Iterator<String> itt= keywords.iterator();
				while(itt.hasNext())
				{					
					String category=itt.next();					
					Nearby nb = new Nearby();
					
					Search s = new Search();
					nb.setLat(jo.getFloat("lat"));
					
					nb.setLng(jo.getFloat("lng"));
					nb.setRadius(5000);
					s.setSourcePOI(jo);
					s.setKeyword(category);
					s.setBg(nb);
					List<String> ja=s.excute();					
					MyFileWriter mfw = new MyFileWriter(jo.getString("name")+"_"+category+".txt");
					for(int i=0;i<ja.size();i++)
					{
						mfw.write(ja.get(i)+"\r\n");
					}
					mfw.close();
				}
				
			} catch (JSONException | IOException |InterruptedException e) {
				e.printStackTrace();
			}
			
		}	
	}
}
