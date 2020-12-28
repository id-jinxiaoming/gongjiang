package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.City;

public interface CityService extends BaseService<City> {

        City selectByCity(String city);

}
