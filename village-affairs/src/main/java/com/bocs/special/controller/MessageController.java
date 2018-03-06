package com.bocs.special.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bocs.special.core.Constant;
import com.bocs.special.core.JavaEEFrameworkBaseController;
import com.bocs.special.model.Message;
import com.bocs.special.service.MessageService;

import core.support.JsonData;
import core.support.PageView;
import core.support.SystemContext;

@Controller
@RequestMapping("/message")
public class MessageController  extends JavaEEFrameworkBaseController<Message> implements Constant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2324068820539577247L;
	
	@Resource
	private MessageService messageService;
	
	
	@RequestMapping("/getUnreadCount")
	@ResponseBody
	public Integer getUnread(){
		List<Message> unReadMessages = messageService.queryByProerties(new String[]{"read","recevicer.id"}, new Object[]{false, getCurrentSysUser().getId()});
		return unReadMessages.size();
	}
	
	@RequestMapping("/inbox")
	public ModelAndView getInbox(){
		Map<String,String> sortedCondition = new HashMap<String,String>();
		sortedCondition.put("sendTime", "desc");
		
		int firstResult = SystemContext.getOffset();
		int pageSize = SystemContext.getPageSize();
		
		Message message = new Message();
		message.setRecevicer(getCurrentSysUser());
		PageView<Message> pageView  = messageService.doPaginationQuery(message,firstResult, pageSize);
		
		return new ModelAndView("messagebox/inbox", "pageView", pageView);
	}
	
	@ResponseBody
	@RequestMapping("/getMessgeContent")
	public JsonData getMessgeContent(String id){
		JsonData jsonData = new JsonData();
		Message message = messageService.get(id);
		Message data = new Message();
		data.setTitle(message.getTitle());
		data.setContent(message.getContent());
		data.setId(message.getId());
		jsonData.setData(data);
		jsonData.setSuccess(true);
		messageService.updateByProperties("id", id, "read", true);
		return jsonData;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JsonData delete(String id){
		JsonData jsonData = new JsonData();
		 try {
			messageService.deleteByPK(id);
			jsonData.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonData.setMessage("系统错误");
		}
		return jsonData;
	}
	

	
}
