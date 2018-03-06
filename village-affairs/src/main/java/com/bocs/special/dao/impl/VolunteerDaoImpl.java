package com.bocs.special.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bocs.special.dao.VolunteerDao;
import com.bocs.special.model.Volunteer;

import core.dao.BaseDao;
import core.support.PageView;

@Repository
public class VolunteerDaoImpl extends BaseDao<Volunteer> implements VolunteerDao{

	@Override
	public PageView<Volunteer> doPaginationQuery(Volunteer volunteer, int firstResult, int pageSize) {
		Criteria criteria = getCriteria(volunteer);
		return resultPage(criteria, firstResult, pageSize);
	}

	private Criteria getCriteria(Volunteer volunteer) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Volunteer.class);
		if(volunteer != null){
			if(StringUtils.isNotBlank(volunteer.getId())){
				criteria.add(Restrictions.eq("id", volunteer.getId()));
			}
			if(StringUtils.isNotBlank(volunteer.getName())){
				criteria.add(Restrictions.eq("name", volunteer.getName()));
			}
			if(StringUtils.isNotBlank(volunteer.getIdCard())){
				criteria.add(Restrictions.eq("idCard", volunteer.getIdCard()));
			}
			if(StringUtils.isNotBlank(volunteer.getPhoneNo())){
				criteria.add(Restrictions.eq("phoneNo", volunteer.getPhoneNo()));
			}
			if(StringUtils.isNotBlank(volunteer.getSex())){
				criteria.add(Restrictions.eq("sex", volunteer.getSex()));
			}
			if(StringUtils.isNoneBlank(volunteer.getCommunity())){
				criteria.add(Restrictions.eq("community", volunteer.getCommunity()));
			}
		}
		return criteria;
	}

}
