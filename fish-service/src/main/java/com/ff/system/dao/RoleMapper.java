package com.ff.system.dao;

import java.util.List;

import com.ff.common.base.BaseMapper;
import com.ff.system.model.Role;

public interface RoleMapper extends BaseMapper<Role>{

	List<Role> findTByUserId(String userId);
	

}