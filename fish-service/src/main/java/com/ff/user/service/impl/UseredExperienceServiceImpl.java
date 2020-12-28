package com.ff.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.user.dao.UseredExperienceMapper;
import com.ff.user.model.UseredExperience;
import com.ff.user.service.UseredExperienceService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UseredExperienceServiceImpl extends BaseServiceImpl<UseredExperience> implements UseredExperienceService {

    @Autowired
    private UseredExperienceMapper mapper;
}
