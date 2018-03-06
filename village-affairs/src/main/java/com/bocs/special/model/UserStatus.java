package com.bocs.special.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 账号状态
 */
@Entity
@Table(name = "tb_user_status")
public class UserStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "STATUS",length=20)
	private String status;
	
	
	@Column(name="status_desc",length=20)
	private String statusDesc;

	/**
	 * 正常
	 */
	public static int NORMAL = 1;

	/**
	 * 锁定
	 */
	public static int LOCKED = 2;

	public UserStatus() {
		super();
	}

	public UserStatus(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

}