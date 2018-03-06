package com.bocs.special.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.bocs.special.model.param.SysUserParameter;

@Entity
@Table(name = "tb_sys_user")
@Cacheable
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
//@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class SysUser extends SysUserParameter{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="idGenerater",strategy="uuid")
	@GeneratedValue(generator="idGenerater")
	private String id;
	
	
	
	/**
	 * 登录用户名
	 */
	@Column(name = "USER_NAME",length=20, unique=true)
	private String userName;
	
	/**
	 * 中文名
	 */
	@Column(name = "chinese_Name",length=20)
	private String chineseName;
	
	/**
	 * 联系电话
	 */
	@Column(name="mobile_no",length=15)
	private String mobileNo;
	
	/**
	 * 邮件
	 */
	@Column(name="email", length=100, unique=true)
	private String email;
	
	
	@Column(length=20)
	private String qq;
	
	@Column(length=20)
	private String weixin;
	
	@Column(name = "department_key", length = 20)
	private String departmentKey;
	
	/**
	 * 学历
	 */
	@Column(length=20)
	private String degree;
	
	/**
	 * 职称
	 */
	@Column(length=20, name="job_title")
	private String jobTitle;
	
	/**
	 * 职务
	 */
	@Column(name="job_position", length=20)
	private String jobPosition;
	
	/**
	 * 登录密码
	 */
	@Column(name = "PASSWORD",length=64)
	private String password;


	/**
	 * 账号状态
	 */
	@ManyToOne
	@JoinColumn(name = "user_status_id")
	private UserStatus userStatus;

	/**
	 * 是否已删除（只是作一个删除标记，不会从数据库中移除）
	 */
	@Column(name="is_deleted")
	@Type(type="yes_no")
	private Boolean deleted;
	
	/**
	 * 上次修改密码的时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_change_pwd_time")
	private Date lastChangePwdTime;
	
	
	/**
	 * 登录失败次数
	 */
	@Column(name="FAILED_CNT")
	private int failedCnt;
	
	/**
	 * 最近一次登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_LOGIN_TIME")
	private Date lastLoginTime;
	



	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;


	/**
	 * 对应的角色
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "r_sysuser_role", joinColumns = { @JoinColumn(name = "sysuser_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>();
	

	/**
	 * 负责的小区
	 */
	private String community;
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysUser other = (SysUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getLastChangePwdTime() {
		return lastChangePwdTime;
	}

	public void setLastChangePwdTime(Date lastChangePwdTime) {
		this.lastChangePwdTime = lastChangePwdTime;
	}

	public int getFailedCnt() {
		return failedCnt;
	}

	public void setFailedCnt(int failedCnt) {
		this.failedCnt = failedCnt;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

	public String getDepartmentKey() {
		return departmentKey;
	}

	public void setDepartmentKey(String departmentKey) {
		this.departmentKey = departmentKey;
	}

	public Boolean getDeleted() {
		return deleted;
	}
	
	
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	
}