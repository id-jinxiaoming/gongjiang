package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.GoodsCateAttr;
import com.ff.shop.model.GoodsCateBrand;

public interface GoodsCateBrandService extends BaseService<GoodsCateBrand> {

    int deleteByCateId(Integer cateId);

}
