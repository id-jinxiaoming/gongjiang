package com.ff.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.user.dao.UseredPhotoMapper;
import com.ff.user.model.UseredPhoto;
import com.ff.user.service.UseredPhotoService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UseredPhotoServiceImpl extends BaseServiceImpl<UseredPhoto> implements UseredPhotoService {

    @Autowired
    private UseredPhotoMapper mapper;
}
