package com.bocs.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bocs.special.dao.VolunteerDao;
import com.bocs.special.model.Information;
import com.bocs.special.model.Volunteer;
import com.bocs.special.service.VolunteerService;

import core.service.BaseService;
import core.support.PageView;

@Service
public class VolunteerServiceImpl extends BaseService<Volunteer> implements VolunteerService{
	
	
	private VolunteerDao volunteerDao;
	
	
	@Resource
	public void setVolunteerDao(VolunteerDao volunteerDao) {
		this.dao = volunteerDao;
		this.volunteerDao = volunteerDao;
	}



	@Override
	public PageView<Volunteer> doPaginationQuery(Volunteer volunteer, int firstResult, int pageSize) {
		return volunteerDao.doPaginationQuery(volunteer, firstResult, pageSize);
	}

}
