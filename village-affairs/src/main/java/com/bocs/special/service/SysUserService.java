package com.bocs.special.service;

import java.util.List;
import java.util.Set;

import com.bocs.special.model.SysUser;

import core.exception.ServiceException;
import core.service.Service;
import core.support.PageView;

/**
 * 用户的业务逻辑层的接口
 */
public interface SysUserService extends Service<SysUser> {

	
	/**
	 * 登录
	 * @param user
	 */
	void login(SysUser user) throws ServiceException;

	/**
	 * 创建用户
	 * @param user
	 */
	void registerUser(SysUser user) throws ServiceException;

	
	/**
	 * 重置密码
	 * @param userId
	 * @param password
	 * @param oldPassword
	 * @throws ServiceException 
	 */
	void resetPassword(String userId, String password, String oldPassword) throws ServiceException;
	
	/**
	 * 根据角色查询用户
	 * @param roleKey
	 * @return
	 */
	List<SysUser> getSysUsersByRoleKey(String roleKey);

	/**
	 * 分页查询所有用户
	 * @param userParam
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	PageView<SysUser> doPaginationQuery(SysUser userParam, Integer firstResult, Integer pageSize);

	Set<String> findRoles(String username);

	Set<String> findPermissions(String username);


}
