package com.ff.users.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersFavoriteMapper;
import com.ff.users.model.UsersFavorite;
import com.ff.users.service.UsersFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UsersFavoriteServiceImpl extends BaseServiceImpl<UsersFavorite> implements UsersFavoriteService {

	@Autowired
    protected UsersFavoriteMapper mapper;
	

}
