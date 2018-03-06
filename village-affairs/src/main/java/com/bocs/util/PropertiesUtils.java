package com.bocs.util;

import java.util.Properties;

public class PropertiesUtils {
	private static Properties cache3 = new Properties();
	private static Properties cache4 = new Properties();
	static {
		try {
			cache3.load(PropertiesUtils.class.getClassLoader()
					.getResourceAsStream("config.properties"));
			cache4.load(PropertiesUtils.class.getClassLoader()
					.getResourceAsStream("mailserver.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static String getBasicValue(String key) {
		return cache3.getProperty(key);
	}
	public static String getMailValue(String key) {
		return cache4.getProperty(key);
	}
	
}
