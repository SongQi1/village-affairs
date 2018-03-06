package com.bocs.special.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bocs.special.dao.SysUserDao;
import com.bocs.special.model.SysUser;

import core.dao.BaseDao;
import core.support.PageView;

/**
 * 用户的数据持久层的实现类
 */
@Repository
public class SysUserDaoImpl extends BaseDao<SysUser> implements SysUserDao {

	@Override
	public String getRoleValueBySysUserId(Long sysUserId) {
		Query query = this.getSession().createSQLQuery("select role_value from r_sysuser_role,role where sysuser_role.role_id = role.id and sysuser_id = :sysUserId");
		query.setParameter("sysUserId", sysUserId);
		String roleValue = (String) query.uniqueResult() == null ? "" : (String) query.uniqueResult();
		return roleValue;
	}

	@Override
	public PageView<SysUser> doPaginationQuery(SysUser userParam,
			Integer firstResult, Integer pageSize) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(SysUser.class);
		if(userParam != null){
			if(StringUtils.isNotBlank(userParam.getUserName())){
				criteria.add(Restrictions.eq("userName", userParam.getUserName().trim()));
			}
			if(StringUtils.isNoneBlank(userParam.getChineseName())){
				criteria.add(Restrictions.eq("chineseName", userParam.getChineseName().trim()));
			}
			if(StringUtils.isNoneBlank(userParam.getMobileNo())){
				criteria.add(Restrictions.eq("mobileNo", userParam.getMobileNo().trim()));
			}
			if(StringUtils.isNoneBlank(userParam.getEmail())){
				criteria.add(Restrictions.eq("email", userParam.getEmail().trim()));
			}
			if(StringUtils.isNoneBlank(userParam.getQq())){
				criteria.add(Restrictions.eq("qq", userParam.getQq().trim()));
			}
			if(StringUtils.isNoneBlank(userParam.getWeixin())){
				criteria.add(Restrictions.eq("weixin", userParam.getWeixin().trim()));
			}
			if(userParam.getUserStatus() != null){
				criteria.add(Restrictions.eq("userStatus.id", userParam.getUserStatus().getId()));
			}
		}
		return resultPage(criteria, firstResult, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> getSysUsersByRoleKey(String roleKey) {
		String hql ="select u from SysUser u join u.roles r where r.roleKey=:roleKey";
		return getSessionFactory().getCurrentSession().createQuery(hql).setParameter("roleKey", roleKey).list();
	}

}
