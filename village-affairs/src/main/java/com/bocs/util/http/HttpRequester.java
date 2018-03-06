package com.bocs.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class HttpRequester {
	public static InputStream send(String url) throws IOException {
		URL httpUrl = new URL(url);
		if (url.startsWith("https")) {
			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("TLS");
				MyX509TrustManager[] xtmArray = { new MyX509TrustManager() };
				sslContext.init(null, xtmArray, new SecureRandom());
			} catch (GeneralSecurityException localGeneralSecurityException) {
			}
			if (sslContext != null) {
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			}
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
		}
		HttpURLConnection httpConn = (HttpURLConnection) httpUrl.openConnection();
		
		httpConn.setRequestProperty("Content-type", "text/html");  
		httpConn.setRequestProperty("Accept-Charset", "utf-8");  
		httpConn.setRequestMethod("GET");
		httpConn.setConnectTimeout(5 * 1000);
		httpConn.setUseCaches(false);
		InputStream inStream = httpConn.getInputStream();
		return inStream;
	}
	public static String sendGet(String url)  throws IOException{
		StringBuffer stringBuff  = new StringBuffer();
		String content = "";
		InputStream inStream = send(url);
		 BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream,"GBK"));
		 while((content = buffReader.readLine()) != null) {
			 stringBuff.append(content);
         }
		 buffReader.close();
		 inStream.close();
		 return stringBuff.toString();
	}
	public static String sendGet(String url, Map<String,String> params) throws IOException{
		String content = "";
		int i = 0;
		StringBuffer param = new StringBuffer();     
		for(String key : params.keySet()){
			if(i == 0)
				param.append("?");
			else
				param.append("&");
			i = 1;
			param.append(key).append("=").append(params.get(key));
		}
		url += param.toString();
		content = sendGet(url);
		 return content;
	}
	
	public static InputStream sendPost(String url, byte[] data) throws IOException{
		if (url.startsWith("https")) {
			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("TLS");
				MyX509TrustManager[] xtmArray = { new MyX509TrustManager() };
				sslContext.init(null, xtmArray, new SecureRandom());
			} catch (GeneralSecurityException localGeneralSecurityException) {
			}
			if (sslContext != null) {
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			}
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
		}
		HttpURLConnection urlCon = (HttpURLConnection)(new URL(url)).openConnection();
        urlCon.setRequestMethod("POST");     
        urlCon.setDoOutput(true);     
        urlCon.setDoInput(true);     
        urlCon.setUseCaches(false);
        urlCon.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        urlCon.setRequestProperty("Accept-Charset", "utf-8");  
        urlCon.setRequestProperty("Cache-Control","no-cache");  
        urlCon.setConnectTimeout(5*1000);
        urlCon.getOutputStream().write(data);     
        urlCon.getOutputStream().flush();     
        urlCon.getOutputStream().close();    
        return urlCon.getInputStream();
	}
	
	public static InputStream sendPost(String url, Map<String,String>params) throws UnsupportedEncodingException, IOException{
		StringBuffer param = new StringBuffer();     
		for(String key : params.keySet()){
			if(!"".equals(param.toString()))
        		param.append("&");  
			param.append(key).append("=").append(params.get(key));
		}
		return sendPost(url, param.toString().getBytes("UTF-8"));
	}
	public static String sendPostString(String url, Map<String,String>params) throws IOException{
		StringBuffer stringBuff  = new StringBuffer();
		String content = "";
		InputStream inStream = sendPost(url, params);
		 BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
		 while((content = buffReader.readLine()) != null) {
			 stringBuff.append(content);
         }
		 buffReader.close();
		 inStream.close();
		return stringBuff.toString();
	}
}
