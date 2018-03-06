package com.bocs.special.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.bocs.special.model.param.ServiceDetailParameter;

/**
 * 志愿者服务明细
 * @author songqi
 *
 */
@Entity
@Table(name="tb_service_detail")
public class ServiceDetail extends ServiceDetailParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5834958565385208788L;

	@Id
	@GenericGenerator(name="idGenerater",strategy="uuid")
	@GeneratedValue(generator="idGenerater")
	private String id;

	/**
	 * 志愿者
	 */
	@ManyToOne
	@JoinColumn(name="volunteer_id")
	private Volunteer volunteer;
	
	
	/**
	 * 服务日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="service_date")
	private Date serviceDate;
	
	/**
	 * 服务项目
	 */
	@Column(name="service_name", length=100)
	private String serviceName;
	
	/**
	 * 服务时长
	 */
	@Column(name="service_time")
	private double serviceTime;
	
	
	/**
	 * 积分
	 */
	private double point;
	
	/**
	 * 备注
	 */
	@Column(length=200)
	private String remark;
	
	/**
	 * 确认人
	 */
	@Column(length=200)
	private String confirmer;
	
	/**
	 * 服务现场图片
	 */
	@OneToMany(mappedBy="serviceDetail", cascade=CascadeType.REMOVE)
	private List<Picture> pictures;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
}
