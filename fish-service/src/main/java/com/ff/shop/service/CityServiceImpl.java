package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.CityMapper;
import com.ff.shop.model.City;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CityServiceImpl extends BaseServiceImpl<City> implements CityService{

    @Autowired
    CityMapper cityMapper;
    @Override
    public City selectByCity(String city) {
        return cityMapper.selectByCity(city);
    }
}
