package com.bocs.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bocs.special.dao.ServiceDetailDao;
import com.bocs.special.dao.VolunteerDao;
import com.bocs.special.model.ServiceDetail;
import com.bocs.special.model.Volunteer;
import com.bocs.special.service.ServiceDetailService;

import core.service.BaseService;
import core.support.PageView;

@Service
public class ServiceDetailServiceImpl extends BaseService<ServiceDetail> implements ServiceDetailService{

	private ServiceDetailDao serviceDetailDao;
	@Resource
	private VolunteerDao volunteerDao;
	
	@Resource
	public void setServiceDetailDao(ServiceDetailDao serviceDetailDao) {
		this.dao = serviceDetailDao;
		this.serviceDetailDao = serviceDetailDao;
	}
	
	@Override
	public PageView<ServiceDetail> doPaginationQuery(ServiceDetail serviceDetail, int firstResult, int pageSize) {
		return serviceDetailDao.doPaginationQuery(serviceDetail, firstResult, pageSize);
	}

	@Override
	public String addService(String volunteerId, ServiceDetail serviceDetail) {
		Volunteer volunteer = volunteerDao.get(volunteerId);
		volunteer.setTotalPoint(volunteer.getTotalPoint() + serviceDetail.getPoint());
		volunteer.setTotalServiceTime(volunteer.getTotalServiceTime() + serviceDetail.getServiceTime());
		volunteerDao.update(volunteer);
		serviceDetail.setVolunteer(volunteer);
		serviceDetailDao.persist(serviceDetail);
		return serviceDetail.getId();
	}

	@Override
	public void delService(String serviceId) {
		ServiceDetail serviceDetail = serviceDetailDao.get(serviceId);
		Volunteer volunteer = serviceDetail.getVolunteer();
		volunteer.setTotalPoint(volunteer.getTotalPoint() - serviceDetail.getPoint());
		volunteer.setTotalServiceTime(volunteer.getTotalServiceTime() - serviceDetail.getServiceTime());
		volunteerDao.update(volunteer);
		serviceDetailDao.delete(serviceDetail);
	}

	@Override
	public void updateService(String volunteerId, ServiceDetail serviceDetail) {
		Volunteer volunteer = volunteerDao.get(volunteerId);
		ServiceDetail oldServiceDetail = serviceDetailDao.get(serviceDetail.getId());
		double oldPoint = oldServiceDetail.getPoint();
		double oldTime = oldServiceDetail.getServiceTime();
		
		double newPoint = serviceDetail.getPoint();
		double newTime = serviceDetail.getServiceTime();
		
		if((newPoint - oldPoint) > 0){
			volunteer.setTotalPoint(volunteer.getTotalPoint() + (newPoint - oldPoint));
		}else{
			volunteer.setTotalPoint(volunteer.getTotalPoint() - (oldPoint - newPoint));
		}
		if((newTime - oldTime) > 0){
			volunteer.setTotalServiceTime(volunteer.getTotalServiceTime() + (newTime - oldTime));
		}else{
			volunteer.setTotalServiceTime(volunteer.getTotalServiceTime() - (oldTime - newTime));
		}
		volunteerDao.update(volunteer);
		
		oldServiceDetail.setServiceName(serviceDetail.getServiceName());
		oldServiceDetail.setServiceDate(serviceDetail.getServiceDate());
		oldServiceDetail.setServiceTime(serviceDetail.getServiceTime());
		oldServiceDetail.setPoint(serviceDetail.getPoint());
		oldServiceDetail.setRemark(serviceDetail.getRemark());
		oldServiceDetail.setConfirmer(serviceDetail.getConfirmer());
		serviceDetailDao.update(oldServiceDetail);
	}

}
