package com.ff.system.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.util.StringUtils;
import com.ff.system.model.Resources;
import com.ff.system.model.Role;
import com.ff.system.model.User;
import com.ff.system.service.ResourcesService;
import com.ff.system.service.RoleService;
import com.ff.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
public class ShiroDbRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private RoleService roleService;
	/**
	 *  Shiro登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		User user = new User();
		user.setAccount(token.getUsername());
		user = userService.findOne(user);
		
		
		if(null == user){
			throw new AccountException("帐号不正确！");

		}else if(user.getStatus()==0){
			throw new DisabledAccountException("帐号已经禁止登录！");
		}else{

			//更新登录时间 last login time
			//user.setLast_login_ip(IpAddress.getIp(request));
			user.setLastLoginTime(new Date());

			userService.updateByPrimaryKey(user);
		}
		
		// 认证缓存信息
		return new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), getName());
	}

	/**
	 * 
	 * Shiro权限认证
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User shiroUser = (User) principals.getPrimaryPrincipal();

		//角色
		List<Role> roleList= roleService.findListByUserId(shiroUser.getId());


		Set<String> roleSet = new HashSet<String>();
		for (Role roleId : roleList) {
			roleSet.add(roleId.getType());
		}
		
		//资源
		
		List<Resources> resourcesList = new ArrayList<Resources>();
		if(roleSet.contains("888888"))
		{
			//获取所有权限
			resourcesList= resourcesService.selectAll();
			
		}else
		{
			//获取用户所有权限
			resourcesList= resourcesService.selectListByUserId(shiroUser.getId(), "0");
			
		}
		
		
		Set<String> resourcesSet = new HashSet<String>();
		for (Resources res : resourcesList) {
			
			if(StringUtils.isNotEmpty(res.getPermission()))
			{
				resourcesSet.add(res.getPermission());
			}
			
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roleSet);
		info.addStringPermissions(resourcesSet);
		return info;
	}

}
