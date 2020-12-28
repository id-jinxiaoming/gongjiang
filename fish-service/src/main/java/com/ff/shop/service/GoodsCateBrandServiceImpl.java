package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.GoodsCateBrandMapper;
import com.ff.shop.model.GoodsCateBrand;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class GoodsCateBrandServiceImpl extends BaseServiceImpl<GoodsCateBrand> implements GoodsCateBrandService{
	@Autowired
	GoodsCateBrandMapper mapper;

	@Override
	public int deleteByCateId(Integer cateId) {
		return mapper.deleteByCateId(cateId);
	}
}
