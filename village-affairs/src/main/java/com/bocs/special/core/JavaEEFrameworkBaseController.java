package com.bocs.special.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bocs.special.model.SysUser;

import core.support.ExtJSBaseParameter;

/**
 */
public abstract class JavaEEFrameworkBaseController<E extends ExtJSBaseParameter> extends ExtJSBaseParameter implements Constant {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3900786620598486594L;

	public SysUser getCurrentSysUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return (SysUser) request.getSession().getAttribute(CURRENT_USER);
	}

	
}
