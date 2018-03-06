package com.bocs.special.dao;

import java.util.List;

import com.bocs.special.model.SysUser;

import core.dao.Dao;
import core.support.PageView;

/**
 * 用户的数据持久层的接口
 */
public interface SysUserDao extends Dao<SysUser> {

	String getRoleValueBySysUserId(Long sysUserId);
	
	
	List<SysUser> getSysUsersByRoleKey(String roleKey);

	/**
	 * 分页查询所有用户
	 * @param userParam
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	PageView<SysUser> doPaginationQuery(SysUser userParam,
			Integer firstResult, Integer pageSize);

}
