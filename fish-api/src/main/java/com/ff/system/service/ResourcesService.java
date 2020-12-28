package com.ff.system.service;


import com.ff.common.base.BaseService;
import com.ff.common.model.JsonTreeData;
import com.ff.common.model.ResourcesTree;
import com.ff.system.model.Resources;
import com.ff.system.model.User;

import java.util.List;

public interface ResourcesService extends BaseService<Resources> {

	int delete(String[] id);
	
	
	
	List<JsonTreeData> selectResources();

	List<JsonTreeData> selectResourcesByUser(User user);
	

	//获取用户所有权限
	List<Resources> selectListByUserId(String userId,String type);

	//获取角色所有权限
	List<Resources> selectListByRoleId(String roleId);

	List<ResourcesTree> selectResourcesTree();



	 
	
	

	
}
