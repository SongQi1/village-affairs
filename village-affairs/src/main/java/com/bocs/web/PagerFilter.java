package com.bocs.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import core.support.SystemContext;

/**
 * 使用pager-taglib进行分页时，需要对分页参数进行处理
 * @author songqi
 *
 */
public class PagerFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		SystemContext.setOffset(getOffset(request));
		SystemContext.setPageSize(getPagesize(request));

		try {
			chain.doFilter(request, response);
		} finally {
			SystemContext.removeOffset();
			SystemContext.removePagesize();
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	private int getOffset(ServletRequest request) {
		int offset = 0;
		try {
			offset = Integer.parseInt(request.getParameter("pager.offset"));
		} catch (Exception ignore) {
		}
		return offset;
	}

	private int getPagesize(ServletRequest request) {
		int pagesize = 0;
		try {
			pagesize = Integer.parseInt(request.getParameter("pagesize"));
		} catch (Exception ignore) {
		}

		if (pagesize == 0) {
			pagesize = 10;
		}
		// System.out.println(pagesize);
		return pagesize;
	}
}
