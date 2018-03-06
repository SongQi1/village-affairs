package com.bocs.special.dao;

import com.bocs.special.model.Volunteer;

import core.dao.Dao;
import core.support.PageView;

public interface VolunteerDao extends Dao<Volunteer>{

	PageView<Volunteer> doPaginationQuery(Volunteer volunteer, int firstResult, int pageSize);

}
