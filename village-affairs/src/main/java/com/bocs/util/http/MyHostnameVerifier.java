package com.bocs.util.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MyHostnameVerifier implements HostnameVerifier{

	public boolean verify(String arg0, SSLSession arg1) {
		return true;
	}
	
}
