package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.Province;

public interface ProvinceService extends BaseService<Province> {

        Province findByProvince(String province);

}
