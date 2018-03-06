package com.bocs.special.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.bocs.special.model.param.ReplyInfoParameter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.support.DateTimeSerializer;

/**
 * 回复信息
 */
@Entity
@Table(name = "tb_reply_info")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ReplyInfo extends ReplyInfoParameter {

	
	private static final long serialVersionUID = 7306241552815502398L;
	@Id
	@GenericGenerator(name="idGenerater", strategy="uuid")
	@GeneratedValue(generator="idGenerater")
	private String id;
	
	/**
	 * 回复人
	 */
	@Column(name = "author", length = 40)
	private String author;
	
	/**
	 * 回复时间
	 */
	@Column(name = "reply_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date replyTime;
	
	/**
	 * 回复内容
	 */
	@Column(name = "content", columnDefinition = "longtext")
	private String content;
	
	/**
	 * 信息
	 */
	@ManyToOne
	@JoinColumn(name="information_id")
	private Information information;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getReplyTime() {
		return replyTime;
	}


	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Information getInformation() {
		return information;
	}


	public void setInformation(Information information) {
		this.information = information;
	}
	
	

}
