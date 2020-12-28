package com.ff.user.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.user.dao.UseredLabelMapper;
import com.ff.user.model.UseredLabel;
import com.ff.user.service.UseredLabelService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UseredLabelServiceImpl extends BaseServiceImpl<UseredLabel> implements UseredLabelService {

    @Autowired
    private UseredLabelMapper mapper;
}
