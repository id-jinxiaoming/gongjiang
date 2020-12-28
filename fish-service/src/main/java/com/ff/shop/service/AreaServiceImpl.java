package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.AreaMapper;
import com.ff.shop.model.Area;
import com.ff.shop.model.Province;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService{

    @Autowired
    AreaMapper areaMapper;
    @Override
    public Area selectByArea(String area) {
        return areaMapper.selectByArea(area);
    }
}
