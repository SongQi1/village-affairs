package com.bocs.special.dao.impl;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bocs.special.dao.ServiceDetailDao;
import com.bocs.special.model.ServiceDetail;

import core.dao.BaseDao;
import core.support.PageView;

@Repository
public class ServiceDetailDaoImpl extends BaseDao<ServiceDetail> implements ServiceDetailDao{

	@Override
	public PageView<ServiceDetail> doPaginationQuery(ServiceDetail serviceDetail, int firstResult, int pageSize) {
		Criteria criteria = getCriteria(serviceDetail);
		return resultPage(criteria, firstResult, pageSize);
	}

	private Criteria getCriteria(ServiceDetail serviceDetail) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ServiceDetail.class);
		if(serviceDetail != null){
			if(serviceDetail.getVolunteer() != null){
				Criteria volunteerCriteria = criteria.createCriteria("volunteer");
				if(StringUtils.isNotBlank(serviceDetail.getVolunteer().getId())){
					volunteerCriteria.add(Restrictions.eq("id", serviceDetail.getVolunteer().getId()));
				}
				if(StringUtils.isNotBlank(serviceDetail.getVolunteer().getName())){
					volunteerCriteria.add(Restrictions.eq("name", serviceDetail.getVolunteer().getName()));
				}
				if(StringUtils.isNotBlank(serviceDetail.getVolunteer().getIdCard())){
					volunteerCriteria.add(Restrictions.eq("idCard", serviceDetail.getVolunteer().getIdCard()));
				}
				if(StringUtils.isNotBlank(serviceDetail.getVolunteer().getPhoneNo())){
					volunteerCriteria.add(Restrictions.eq("phoneNo", serviceDetail.getVolunteer().getPhoneNo()));
				}
			}
			if(StringUtils.isNotBlank(serviceDetail.getServiceName())){
				criteria.add(Restrictions.like("serviceName", serviceDetail.getServiceName(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(serviceDetail.getRemark())){
				criteria.add(Restrictions.like("remark", serviceDetail.getRemark(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(serviceDetail.getStartDate()) && StringUtils.isNotBlank(serviceDetail.getEndDate())){
				try {
					Date startDate = DateUtils.parseDate(serviceDetail.getStartDate() + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"});
					Date endDate =  DateUtils.parseDate(serviceDetail.getEndDate() + " 23:59:59", new String[]{"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"});
					criteria.add(Restrictions.between("serviceDate", startDate, endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return criteria;
	}

}
