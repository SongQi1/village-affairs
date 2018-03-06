package com.bocs.special.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bocs.special.dao.RoleDao;
import com.bocs.special.model.Role;

import core.dao.BaseDao;

/**
 * 角色的数据持久层的实现类
 */
@Repository
public class RoleDaoImpl extends BaseDao<Role> implements RoleDao {


	// 删除角色
	public void deleteSysUserAndRoleByRoleId(String roleId) {
		Query query = this.getSession().createSQLQuery("delete from r_sysuser_role where role_id = :roleId");
		query.setParameter("roleId", roleId);
		query.executeUpdate();
	}

	// 根据菜单编码删除按钮权限
	public void deleteRolePermissionByMenuCode(String menuCode) {
		Query query = this.getSession().createSQLQuery("delete from r_role_permission where permissions like '%" + menuCode + "%'");
		query.executeUpdate();
	}

	// 保存按钮权限
	public void saveRolePermission(Long roleId, String permissions) {
		Query query = this.getSession().createSQLQuery("insert into r_role_permission values (:roleId,:permissions)");
		query.setParameter("roleId", roleId);
		query.setParameter("permissions", permissions);
		query.executeUpdate();
	}

}
