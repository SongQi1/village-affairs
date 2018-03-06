package com.bocs.special.service;

import org.springframework.transaction.annotation.Transactional;

import com.bocs.special.model.ServiceDetail;

import core.service.Service;
import core.support.PageView;

@Transactional
public interface ServiceDetailService extends Service<ServiceDetail>{

	PageView<ServiceDetail> doPaginationQuery(ServiceDetail serviceDetail, int firstResult, int pageSize);

	String addService(String volunteerId, ServiceDetail serviceDetail);

	void delService(String serviceId);

	void updateService(String volunteerId, ServiceDetail serviceDetail);

}
