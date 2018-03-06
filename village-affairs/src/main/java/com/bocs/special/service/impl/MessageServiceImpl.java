package com.bocs.special.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bocs.special.dao.MessageDao;
import com.bocs.special.dao.SysUserDao;
import com.bocs.special.model.Message;
import com.bocs.special.model.SysUser;
import com.bocs.special.service.MessageService;
import com.bocs.util.MailService;

import core.service.BaseService;
import core.support.PageView;

@Service
public class MessageServiceImpl extends BaseService<Message> implements MessageService{

	private MessageDao messageDao;

	public MessageDao getMessageDao() {
		return messageDao;
	}

	@Resource
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
		this.dao = messageDao;
	}
	
	
	@Resource
	private MailService mailService;
	@Resource
	private SysUserDao sysUserDao;

	@Override
	public void sentAlert(String title, String content, List<SysUser> receivers, SysUser sender) {
		//发送邮件提醒
		List<String> mailAdds = new ArrayList<String>();
		if(receivers != null && receivers.size() > 0){
			for(SysUser receiver : receivers){
				if(StringUtils.isNotBlank(receiver.getEmail())){
					mailAdds.add(receiver.getEmail());
				}
			}
		}
		if(mailAdds != null && mailAdds.size() > 0){
			final List<String> receiveAdds = mailAdds;
			final String mailContent = content;
			final String mailSubject = title;
			new Thread(new Runnable() {
				public void run() {
					try {
						mailService.sendMail(receiveAdds, mailSubject, mailContent);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		//发送站内信提醒
		if(receivers != null && receivers.size() > 0){
			for(SysUser receiver : receivers){
				Message message = new Message();
				message.setSendUser(sender);
				message.setSendTime(new Date());
				message.setTitle(title);
				message.setContent(content);
				message.setRead(false);
				message.setRecevicer(receiver);
				messageDao.persist(message);
			}
		}
	}


	@Override
	public PageView<Message> doPaginationQuery(Message message,
			int firstResult, int pageSize) {
		// TODO Auto-generated method stub
		return messageDao.doPaginationQuery(message, firstResult, pageSize);
	}
	
	
}
