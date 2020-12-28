package com.ff.users.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersAccountLogMapper;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.service.UsersAccountLogService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UsersAccountLogServiceImpl extends BaseServiceImpl<UsersAccountLog> implements UsersAccountLogService {

	@Autowired
    protected UsersAccountLogMapper mapper;



}
