package com.ff.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.user.dao.UseredWorkTypeMapper;
import com.ff.user.model.UseredWorkType;
import com.ff.user.service.UseredWorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UseredWorkTypeServiceImpl extends BaseServiceImpl<UseredWorkType> implements UseredWorkTypeService {

    @Autowired
    private UseredWorkTypeMapper mapper;
}
