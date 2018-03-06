package com.bocs.special.service;

import com.bocs.special.model.Volunteer;

import core.service.Service;
import core.support.PageView;

public interface VolunteerService extends Service<Volunteer>{

	PageView<Volunteer> doPaginationQuery(Volunteer volunteer, int firstResult, int pageSize);

}
