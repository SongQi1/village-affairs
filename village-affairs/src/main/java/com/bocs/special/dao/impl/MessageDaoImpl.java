package com.bocs.special.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bocs.special.dao.MessageDao;
import com.bocs.special.model.Message;

import core.dao.BaseDao;
import core.support.PageView;

@Repository
public class MessageDaoImpl extends BaseDao<Message> implements MessageDao{

	@Override
	public PageView<Message> doPaginationQuery(Message message,
			int firstResult, int pageSize) {
		
		Criteria criteria = getSession().createCriteria(Message.class);
		if(message != null){
			if(message.getRecevicer() != null){
				criteria.add(Restrictions.eq("recevicer.id", message.getRecevicer().getId()));
			}
		}
		criteria.addOrder(Order.desc("sendTime"));
		return resultPage(criteria, firstResult, pageSize);
	}

}
