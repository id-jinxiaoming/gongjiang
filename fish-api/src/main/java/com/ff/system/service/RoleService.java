package com.ff.system.service;


import com.ff.common.base.BaseService;
import com.ff.system.model.Role;
import com.ff.system.model.RoleResources;

import java.util.List;

public interface RoleService extends BaseService<Role> {

    int insert(Role role, String[] resourcesId);
    
    int update(Role role, String[] resourcesId);
    
    int delete(String[] id);

	List<RoleResources> findRoleResourcesByRole(Role role);
	
	List<Role> findListByUserId(String userId);

    boolean updateByUserId(String userId,String[] roleId);

    boolean addResourcesByRoleId(String roleId,String[] resourcesId);

	

}
