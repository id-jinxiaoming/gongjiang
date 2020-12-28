package com.ff.users.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersRecordMapper;
import com.ff.users.model.UsersRecord;
import com.ff.users.service.UsersRecordService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UsersRecordServiceImpl extends BaseServiceImpl<UsersRecord> implements UsersRecordService {

	@Autowired
    protected UsersRecordMapper mapper;
	

}
