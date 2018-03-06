package com.bocs.special.dao;

import com.bocs.special.model.Message;

import core.dao.Dao;
import core.support.PageView;

public interface MessageDao extends Dao<Message>{

	PageView<Message> doPaginationQuery(Message message, int firstResult,
			int pageSize);

}
