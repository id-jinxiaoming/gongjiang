package com.ff.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.user.dao.UseredRanksInformationMapper;
import com.ff.user.model.UseredRanksInformation;
import com.ff.user.service.UseredRanksInformationService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UseredRanksInformationServiceImpl extends BaseServiceImpl<UseredRanksInformation> implements UseredRanksInformationService {

    @Autowired
    private UseredRanksInformationMapper mapper;
}
