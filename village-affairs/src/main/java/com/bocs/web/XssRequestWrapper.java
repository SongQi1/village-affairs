package com.bocs.web;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class XssRequestWrapper extends HttpServletRequestWrapper {
	
	public XssRequestWrapper(HttpServletRequest request){
		super(request);
	}


	@Override
	public String getHeader(String name) {
		String str = super.getHeader(name);
		if (str == null)
			return null;
		return XssClean.xssClean(str);
	}

	@Override
	public String getParameter(String name) {
		
		String str = super.getParameter(name);
		if (str == null)
			return null;
		return XssClean.xssClean(str);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getParameterMap() {
		
		
		Map<String, String[]> request_map = super.getParameterMap();
		Iterator iterator = request_map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry me = (Map.Entry) iterator.next();
			String[] values = (String[]) me.getValue();
			for (int i = 0; i < values.length; i++) {
				values[i] = XssClean.xssClean(values[i]);
			}
		}
		return request_map;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] arrayOfString1 = super.getParameterValues(name);
		if (arrayOfString1 == null)
			return null;
		int i = arrayOfString1.length;
		String[] arrayOfString2 = new String[i];
		for (int j = 0; j < i; j++) {
			arrayOfString2[j] = XssClean.xssClean(arrayOfString1[j]);
		}
		return arrayOfString2;
	}
	
	
	@Override
	public String getQueryString() {
		String value = super.getQueryString();
		if (value != null) {
			value = XssClean.xssClean(value);
		}
		return value;
	}
	
}
