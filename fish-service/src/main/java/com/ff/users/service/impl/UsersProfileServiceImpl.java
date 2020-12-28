package com.ff.users.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersProfileMapper;
import com.ff.users.model.UsersProfile;
import com.ff.users.service.UsersProfileService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UsersProfileServiceImpl extends BaseServiceImpl<UsersProfile> implements UsersProfileService {

	@Autowired
    protected UsersProfileMapper mapper;
	

}
