package com.ff.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ff.common.base.BaseMapper;
import com.ff.system.model.Resources;
import com.ff.system.model.User;

public interface ResourcesMapper extends BaseMapper<Resources>{

	
	List<Resources> selectListByUserId(@Param("userId")String userId,@Param("type")String type);

	List<Resources> selectListByRoleId(@Param("roleId")String roleId);
}