package com.bocs.special.dao;

import com.bocs.special.model.ServiceDetail;

import core.dao.Dao;
import core.support.PageView;

public interface ServiceDetailDao extends Dao<ServiceDetail>{

	PageView<ServiceDetail> doPaginationQuery(ServiceDetail serviceDetail, int firstResult, int pageSize);

}
