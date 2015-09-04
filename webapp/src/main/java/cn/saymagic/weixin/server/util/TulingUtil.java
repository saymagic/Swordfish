package cn.saymagic.weixin.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import cn.saymagic.weixin.server.Config;
import cn.saymagic.weixin.server.bean.TulingResult;

import com.google.gson.Gson;

public class TulingUtil {

	public static String getResponse(String request,String userid) throws IOException{
		String INFO = URLEncoder.encode(request, "utf-8"); 
		String USER_ID = URLEncoder.encode(userid, "utf-8"); 
		String getURL = "http://www.tuling123.com/openapi/api?key=" + Config.APIKEY + "&info=" + INFO + "&userid=" + USER_ID; 
		URL getUrl = new URL(getURL); 
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); 
		connection.connect(); 
		// 取得输入流，并使用Reader读取 
		BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream(), "utf-8")); 
		StringBuffer sb = new StringBuffer(); 
		String line = ""; 
		while ((line = reader.readLine()) != null) { 
			sb.append(line); 
		} 
		reader.close(); 
		// 断开连接 
		connection.disconnect(); 
		return sb.toString();
	}

	public static String getContentStr(String request,String userid) throws IOException{
		Gson gson = new Gson();
		TulingResult result = gson.fromJson(getResponse(request,userid), TulingResult.class);  
		return result.getText();
	}

}
