package com.bocs.special.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.bocs.special.core.Constant;
import com.bocs.special.dao.AttachmentDao;
import com.bocs.special.dao.RoleDao;
import com.bocs.special.dao.SysUserDao;
import com.bocs.special.model.Permission;
import com.bocs.special.model.Role;
import com.bocs.special.model.SysUser;
import com.bocs.special.model.UserStatus;
import com.bocs.special.service.SysUserService;

import core.exception.ServiceException;
import core.service.BaseService;
import core.support.PageView;

/**
 * 用户的业务逻辑层的实现
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUser> implements SysUserService, Constant {

	private SysUserDao sysUserDao;
	

	@Resource
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
		this.dao = sysUserDao;
	}

	@Resource
	private AttachmentDao attachmentDao;
	@Resource
	private RoleDao roleDao;
	
	
	@Override
	public void login(SysUser user)  throws ServiceException{
		Subject subject = SecurityUtils.getSubject();
		SysUser account = sysUserDao.getByProerties("userName", user.getUserName());
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
			subject.login(token);
			
			account.setFailedCnt(0);
			account.setLastLoginTime(new Date());
			
			Session session = subject.getSession();
			session.setAttribute(CURRENT_USER, account);
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			throw new ServiceException("用户名不存在。");
		}catch (LockedAccountException e) {
			e.printStackTrace();
			throw new ServiceException("账号已锁定，请联系系统管理员解锁。");
		}catch (AuthenticationException e) {
			e.printStackTrace();
			String errorMsg = "";
			int failedCnt = account.getFailedCnt()+1;
			if(failedCnt < LOGIN_FAILED_LIMITED){
				int tryCnt = LOGIN_FAILED_LIMITED - failedCnt;
				errorMsg = "密码错误，您还可以尝试"+tryCnt +"次！";
			}else{
				account.setUserStatus(new UserStatus(UserStatus.LOCKED));
				errorMsg = "账号已锁定，请联系系统管理员解锁。";
			}
			account.setFailedCnt(failedCnt);
			throw new ServiceException(errorMsg);
		}finally{
			if(account != null){
				sysUserDao.update(account);
			}
			
		}
	}

	@Override
	public void registerUser(SysUser user) throws ServiceException {
		if(user != null){
			SysUser userDb = sysUserDao.getByProerties("userName", user.getUserName());
			if(userDb != null){
				throw new ServiceException("用户名已注册");
			}else{
				user.setPassword(new Sha256Hash(user.getPassword()).toHex());
				user.setUserStatus(new UserStatus(UserStatus.NORMAL));
				user.setLastLoginTime(new Date());
				user.setCreateTime(new Date());
				user.setDeleted(false);
				user.setFailedCnt(0);
				
				Set<Role> roles = new HashSet<Role>();
				Role role = roleDao.getByProerties("roleKey", "ROLE_USER");
				roles.add(role);
				user.setRoles(roles);
				sysUserDao.persist(user);
			}
		}
	}

	@Override
	public void resetPassword(String userId, String password, String oldPassword) throws ServiceException {
		SysUser user = sysUserDao.get(userId);
		if(new Sha256Hash(oldPassword).toHex().equalsIgnoreCase(user.getPassword())){
			user.setPassword(new Sha256Hash(password).toHex());
			user.setLastChangePwdTime(new Date());
			sysUserDao.update(user);
			
		}else{
			throw new ServiceException("旧密码不正确。");
		}
	}
	@Override
	public List<SysUser> getSysUsersByRoleKey(String roleKey){
		return sysUserDao.getSysUsersByRoleKey(roleKey);
	} 

	@Override
	public PageView<SysUser> doPaginationQuery(SysUser userParam, Integer firstResult, Integer pageSize) {
		return sysUserDao.doPaginationQuery(userParam, firstResult, pageSize);
	}

	@Override
	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		Set<String> roleString = new HashSet<String>();
		SysUser sysUser = sysUserDao.getByProerties("userName", username);
		Set<Role> roles = sysUser.getRoles();
		if(roles != null && roles.size() > 0){
			for(Role role : roles){
				roleString.add(role.getRoleKey());
			}
		}
		return roleString;
	}

	@Override
	public Set<String> findPermissions(String username) {
		Set<String> permissions = new HashSet<String>();
		SysUser sysUser = sysUserDao.getByProerties("userName", username);
		Set<Role> roles = sysUser.getRoles();
		if(roles != null && roles.size() > 0){
			for(Role role : roles){
				Set<Permission> pers = role.getPermissions();
				if(pers != null && pers.size() > 0){
					for(Permission p : pers){
						permissions.add(p.getPermission());
					}
				}
			}
		}
		
		return permissions;
	}


}
