package com.bocs.web;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class XssClean {
	private static Policy policy = null;
	
	static {
		if (policy == null) {
			InputStream policyFile = XssClean.class.getClassLoader().getResourceAsStream("antisamy-anythinggoes.xml");
			try {
				policy = Policy.getInstance(policyFile);
			} catch (PolicyException e) {
				System.out.println("读取策略文件异常: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// 将client端用户输入的request，在server端进行了拦截，并且进行了过滤
	public static String xssClean(String value) {
		if (StringUtils.isNotEmpty(value)) {
			AntiSamy antiSamy = new AntiSamy();
			try {
				final CleanResults cr = antiSamy.scan(value, policy);
				String safeStr = cr.getCleanHTML();
				//safeStr = safeStr.replaceAll(antiSamy.scan("&nbsp;", policy).getCleanHTML(), "");
				return safeStr;
			} catch (ScanException e) {
				e.printStackTrace();
			} catch (PolicyException e) {
				System.out.println("加载XSS规则文件异常: " + e.getMessage());
			}
		}
		return value;
	}
}
