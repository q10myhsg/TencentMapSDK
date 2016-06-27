package cn.fangcheng.helper;

import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.zhengjia.tencent.CityService;

import home.common.util.file.MyFileWriter;
import weibo4j.org.json.JSONException;

public class CityServiceHelper {

	public static final float latStep = 0.00899f;
	public static final float lngStep = 0.01175f;

	private String cityName;
	private MyFileWriter mfw;

	public CityServiceHelper() {
		mfw = new MyFileWriter("beijing-lat-lng.txt");
	}

	public void close() {
		mfw.close();
	}

	public void setCityName(String _cityName) {
		cityName = _cityName;
	}

	public boolean isInCity(String city, float lat, float lng) {
		boolean ret = false;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CityService cs = new CityService();
		cs.setLat(lat);
		cs.setLng(lng);
		try {
			String temp = cs.singleRequest(httpclient);
			if (temp.contains(city)) {
				return true;
			}
		} catch (IOException | JSONException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public void splitLatLng(float latStart, float latEnd, float lngStart, float lngEnd) {

		float tempLat = latStart;
		float tempLng = lngStart;
		
		float f = ((lngEnd-lngStart)/lngStep)*((latEnd-latStart)/latStep);
		
		int count=0;
		while (tempLat <= latEnd) {
			
			
			while (tempLng <= lngEnd) {
				if (isInCity(cityName, tempLat, tempLng)) {
					mfw.write(tempLat + "," + tempLng + "\r\n");
					count++;
					if(count%1000==0){
						System.out.println("=================  count:"+ count+ "=======has done :" +  (float)count*100/f+"%==================================================");
					}
				}
				tempLng += lngStep;
			}
			tempLat += latStep;
			tempLng= lngStart;
		}
		mfw.close();
	}

	// latitude 维度 39”26’
	// longitude 精度 115”25’

	public static void main(String[] args) {
		CityServiceHelper csh = new CityServiceHelper();
		csh.setCityName("北京");
		 csh.splitLatLng(39, 41.5f, 115, 117.5f);
//		csh.splitLatLng(39, 39.1f, 115, 115.1f);
		csh.close();
	}

}
