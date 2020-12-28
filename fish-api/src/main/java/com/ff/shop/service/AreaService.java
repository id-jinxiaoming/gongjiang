package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.Area;
import com.ff.shop.model.City;

public interface AreaService extends BaseService<Area> {

    Area selectByArea(String area);

}
