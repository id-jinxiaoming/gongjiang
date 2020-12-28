package com.ff.users.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersGroupMapper;
import com.ff.users.model.UsersGroup;
import com.ff.users.service.UsersGroupService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UsersGroupServiceImpl extends BaseServiceImpl<UsersGroup> implements UsersGroupService {

	@Autowired
    protected UsersGroupMapper mapper;
	

}
