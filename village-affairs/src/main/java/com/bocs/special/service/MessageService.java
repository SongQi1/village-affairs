package com.bocs.special.service;

import java.util.List;

import com.bocs.special.model.Message;
import com.bocs.special.model.SysUser;

import core.exception.ServiceException;
import core.service.Service;
import core.support.PageView;

public interface MessageService extends Service<Message>{

	/**
	 * 发送提醒
	 * @param title
	 * @param content
	 * @param receiver
	 * @param sender
	 */
	void sentAlert(String title, String content, List<SysUser> receiver, SysUser sender);


	PageView<Message> doPaginationQuery(Message message, int firstResult, int pageSize);
}
