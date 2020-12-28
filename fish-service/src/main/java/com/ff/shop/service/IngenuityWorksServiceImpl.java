package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.IngenuityWorksMapper;
import com.ff.shop.model.IngenuityWorks;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class IngenuityWorksServiceImpl extends BaseServiceImpl<IngenuityWorks> implements IngenuityWorksService {

    @Autowired
    private IngenuityWorksMapper mapper;

    @Override
    public int insert(IngenuityWorks t) {
        mapper.insert(t);
        Integer id=t.getId();
        return id;
    }
}
