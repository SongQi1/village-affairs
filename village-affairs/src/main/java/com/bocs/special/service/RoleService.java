package com.bocs.special.service;

import com.bocs.special.model.Role;

import core.service.Service;

/**
 * 角色的业务逻辑层的接口
 */
public interface RoleService extends Service<Role> {

	// 删除角色
	void deleteSysUserAndRoleByRoleId(String roleId);

}
