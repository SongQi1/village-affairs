package com.bocs.special.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tb_volunteer")
public class Volunteer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4291124069353662622L;

	@Id
	@GenericGenerator(name="idGenerater",strategy="uuid")
	@GeneratedValue(generator="idGenerater")
	private String id;

	/**
	 * 志愿者编号
	 */
	@Column(length=100)
	private String no;
	
	/**
	 * 志愿者姓名
	 */
	@Column(length=20)
	private String name;
	
	/**
	 * 性别
	 */
	@Column(length=20)
	private String sex;
	
	/**
	 * 身份证号
	 */
	@Column(name="id_card", length=20)
	private String idCard;
	
	
	/**
	 * 手机号码
	 */
	@Column(name="phone_no", length=20)
	private String phoneNo;
	
	/**
	 * 所在社区
	 */
	@Column(length=100)
	private String community;
	
	/**
	 * 总积分
	 */
	@Column(name="total_point")
	private double totalPoint;
	
	/**
	 * 总服务时长
	 */
	@Column(name="total_service_time")
	private double totalServiceTime;
	
	@OneToMany(mappedBy="volunteer", cascade=CascadeType.REMOVE)
	@OrderBy("serviceDate desc")
	private List<ServiceDetail> serviceDetailList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	
	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public List<ServiceDetail> getServiceDetailList() {
		return serviceDetailList;
	}

	public void setServiceDetailList(List<ServiceDetail> serviceDetailList) {
		this.serviceDetailList = serviceDetailList;
	}

	public double getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(double totalPoint) {
		this.totalPoint = totalPoint;
	}

	public double getTotalServiceTime() {
		return totalServiceTime;
	}

	public void setTotalServiceTime(double totalServiceTime) {
		this.totalServiceTime = totalServiceTime;
	}
	
	
	
}
