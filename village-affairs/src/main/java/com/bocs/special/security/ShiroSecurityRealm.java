package com.bocs.special.security;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.bocs.special.dao.SysUserDao;
import com.bocs.special.model.SysUser;
import com.bocs.special.model.UserStatus;
import com.bocs.special.service.SysUserService;

@Component(value="shiroSecurityRealm")
public class ShiroSecurityRealm extends AuthorizingRealm {

	@Resource
	private SysUserService sysUserService;




    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        
        authorizationInfo.setRoles(sysUserService.findRoles(username));
        authorizationInfo.setStringPermissions(sysUserService.findPermissions(username));

        return authorizationInfo;
    }

    @Override
	protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();

		SysUser user = sysUserService.getByProerties("userName", username);

		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		if (user.getUserStatus().getId() == UserStatus.LOCKED) {
			throw new LockedAccountException(); // 帐号锁定
		}

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user.getUserName(), // 用户名
				user.getPassword(), // 密码
				getName() // realm name
		);
		return authenticationInfo;
	}


}
