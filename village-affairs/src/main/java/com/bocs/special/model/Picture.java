package com.bocs.special.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tb_picture")
public class Picture  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3946931581850902406L;
	
	@Id
	@GenericGenerator(name="idGenerater",strategy="uuid")
	@GeneratedValue(generator="idGenerater")
	private String id;
	
	/**
	 * 对应的服务明细
	 */
	@ManyToOne
	@JoinColumn(name="service_detail_id")
	private ServiceDetail serviceDetail;
	
	
	/**
	 * 图片保存的路径
	 */
	@Column(name="picture_url")
	private String pictureUrl;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public ServiceDetail getServiceDetail() {
		return serviceDetail;
	}


	public void setServiceDetail(ServiceDetail serviceDetail) {
		this.serviceDetail = serviceDetail;
	}


	public String getPictureUrl() {
		return pictureUrl;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	
	

}
