package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.GoodsOptionalMapper;
import com.ff.shop.model.GoodsOptional;
import org.springframework.beans.factory.annotation.Autowired;



@Service
public class GoodsOptionalServiceImpl extends BaseServiceImpl<GoodsOptional> implements GoodsOptionalService{
	@Autowired
	protected GoodsOptionalMapper mapper;

}
