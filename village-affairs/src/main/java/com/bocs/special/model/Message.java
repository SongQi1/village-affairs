package com.bocs.special.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.bocs.special.model.param.MessageParameter;


@Entity
@Table(name="tb_message")
public class Message extends MessageParameter{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1719603644823511799L;

	@Id
	@GenericGenerator(name="idGenerater",strategy="uuid")
	@GeneratedValue(generator="idGenerater")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="send_user_id")
	private SysUser sendUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_time")
	private Date sendTime;
	
	@Column(length=100)
	private String title;
	
	@Column(name="content",columnDefinition="longtext")
	private String content;
	
	@Column(name="is_read")
	@Type(type="yes_no")
	private Boolean read;
	
	@ManyToOne
	@JoinColumn(name="receiver_id")
	private SysUser recevicer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysUser getSendUser() {
		return sendUser;
	}

	public void setSendUser(SysUser sendUser) {
		this.sendUser = sendUser;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public SysUser getRecevicer() {
		return recevicer;
	}

	public void setRecevicer(SysUser recevicer) {
		this.recevicer = recevicer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
