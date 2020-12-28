package com.ff.rechage.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.rechage.dao.RechageMapper;
import com.ff.rechage.model.Rechage;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class RechageServiceImpl extends BaseServiceImpl<Rechage> implements RechageService{
    @Autowired
    RechageMapper rechageMapper;


}
