package com.bocs.special.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bocs.special.dao.AttachmentDao;
import com.bocs.special.model.Attachment;
import com.bocs.special.service.AttachmentService;

import core.service.BaseService;

/**
 * 附件的业务逻辑层的实现
 */
@Service
public class AttachmentServiceImpl extends BaseService<Attachment> implements AttachmentService {

	private AttachmentDao attachmentDao;

	@Resource
	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
		this.dao = attachmentDao;
	}

	

}
