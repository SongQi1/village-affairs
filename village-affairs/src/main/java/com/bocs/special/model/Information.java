package com.bocs.special.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.bocs.special.model.param.InformationParameter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.support.DateTimeSerializer;

/**
 * 项目小区管理
 */
@Entity
@Table(name = "tb_information")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Information extends InformationParameter {

	
	private static final long serialVersionUID = 7306241552815502398L;
	@Id
	@GenericGenerator(name="idGenerater",strategy="uuid")
	@GeneratedValue(generator="idGenerater")
	private String id;
	
	/**
	 * 标题
	 */
	@Column(name = "title", length = 100, nullable = false)
	private String title;
	
	/**
	 * 发布人
	 */
	@Column(name = "author", length = 40)
	private String author;
	
	/**
	 * 信息发布时间
	 */
	@Column(name = "refresh_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date refreshTime;
	
	/**
	 * 信息类型
	 */
	private String type;
	
	/**
	 * 社区
	 */
	@Column(length = 100)
	private String community;
	
	/**
	 * 信息内容
	 */
	@Column(name = "content", columnDefinition = "longtext")
	private String content;
	
	/**
	 * 是否是重要事项
	 */
	@Column(name="is_important")
	@Type(type="yes_no")
	private Boolean important;
	
	/**
	 * 完成百分比
	 */
	@Column(name="complete_percent")
	private Double completePercent;
	
	/**
	 * 信息回复列表
	 */
	@OneToMany(mappedBy="information", cascade=CascadeType.REMOVE)
	private List<ReplyInfo> replyInfoList;
	
	public Information() {

	}
	
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}
	
	public List<ReplyInfo> getReplyInfoList() {
		return replyInfoList;
	}

	public void setReplyInfoList(List<ReplyInfo> replyInfoList) {
		this.replyInfoList = replyInfoList;
	}

	public Boolean getImportant() {
		return important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
	}
	
	public Double getCompletePercent() {
		return completePercent;
	}
	
	public void setCompletePercent(Double completePercent) {
		this.completePercent = completePercent;
	}



	/**
	 * 小区管理
	 */
	public final static String TYPE_COMMUNITYAFFAIRS="communityaffairs";
	
	/**
	 * 党建服务
	 */
	public final static String TYPE_PARTY="party";
	
	/**
	 * 民政民生
	 */
	public final static String TYPE_CIVIL_AFFAIRS="civilaffairs";
	
	/**
	 * 综合治理
	 */
	public final static String TYPE_COMPREHENSIVES="comprehensives";
	
	/**
	 * 其他
	 */
	public final static String TYPE_OTHER_AFFAIRS="otheraffairs";
	
	
	/**
	 * 蓬朗社区
	 */
	public final static String COMMUNITY_PENGLANG="penglang";
	
	/**
	 * 蓬曦社区
	 */
	public final static String COMMUNITY_PENGXII="pengxii";
	
	/**
	 * 蓬欣社区
	 */
	public final static String COMMUNITY_PENGXIN="pengxin";
	
	/**
	 * 蓬晨社区
	 */
	public final static String COMMUNITY_PENGCHEN="pengchen";
	
	/**
	 * 蓬苑社区
	 */
	public final static String COMMUNITY_PENGYUAN="pengyuan";
	
	/**
	 * 蓬莱社区
	 */
	public final static String COMMUNITY_PENGLAI="penglai";

}
