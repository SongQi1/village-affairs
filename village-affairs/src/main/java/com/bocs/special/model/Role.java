package com.bocs.special.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bocs.special.model.param.RoleParameter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;


/**
 * 用户角色
 */
@Entity
@Table(name = "tb_role")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Role extends RoleParameter {
	private static final long serialVersionUID = 6019103858711599150L;
	@Id
	private Long id;
	
	@Column(name = "role_key", length = 40, nullable = false, unique = true)
	private String roleKey;
	
	@Column(name = "role_value", length = 40, nullable = false)
	private String roleValue;
	
	@Column(name = "description", length = 200)
	private String description;
	
	@ManyToMany
	@JoinTable(name = "r_role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	private Set<Permission> permissions = new HashSet<Permission>();

	public Role() {

	}
	
	public Role(long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Role other = (Role) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.roleKey, other.roleKey) && Objects.equal(this.roleValue, other.roleValue) && Objects.equal(this.description, other.description)
				&& Objects.equal(this.permissions, other.permissions);
	}

	public int hashCode() {
		return Objects.hashCode(this.id, this.roleKey, this.roleValue, this.description, this.permissions);
	}

	
}
