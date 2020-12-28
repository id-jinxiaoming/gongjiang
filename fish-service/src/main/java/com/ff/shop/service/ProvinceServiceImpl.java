package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.GoodsMapper;
import com.ff.shop.dao.ProvinceMapper;
import com.ff.shop.model.Goods;
import com.ff.shop.model.Province;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ProvinceServiceImpl extends BaseServiceImpl<Province> implements ProvinceService{

	@Autowired
	ProvinceMapper provinceMapper;
	@Override
	public Province findByProvince(String province) {

		return provinceMapper.selectByProvince(province);
	}
}
