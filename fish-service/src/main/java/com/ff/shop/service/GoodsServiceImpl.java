package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.GoodsMapper;
import com.ff.shop.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods> implements GoodsService{

	@Autowired
	GoodsMapper mapper;

	@Override
	public int insert(Goods goods) {
		mapper.insert(goods);
		return goods.getGoodsId();
	}
}
