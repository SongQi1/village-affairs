package com.bocs.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bocs.special.dao.RoleDao;
import com.bocs.special.model.Role;
import com.bocs.special.service.RoleService;

import core.service.BaseService;

/**
 * 角色的业务逻辑层的实现
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

	private RoleDao roleDao;

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
		this.dao = roleDao;
	}
	@Override
	public void deleteSysUserAndRoleByRoleId(String roleId) {
		roleDao.deleteSysUserAndRoleByRoleId(roleId);
	}


}
