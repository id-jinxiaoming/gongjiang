package com.ff.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.user.model.Authentication;
import com.ff.user.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl extends BaseServiceImpl<Authentication> implements AuthenticationService {

}
