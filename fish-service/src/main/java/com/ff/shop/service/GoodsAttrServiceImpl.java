package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.GoodsAttrMapper;
import com.ff.shop.model.GoodsAttr;
import com.ff.shop.model.bo.GoodsAttrBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GoodsAttrServiceImpl extends BaseServiceImpl<GoodsAttr> implements GoodsAttrService{

	@Autowired
	GoodsAttrMapper mapper;


	@Override
	public Integer deleteByGoodsId(Integer goodsId) {
		Map<String,Object> map=new HashMap<>();
		map.put("goods_id",goodsId.toString());
		return mapper.deleteByMap(map);
	}

	@Override
	public List<GoodsAttrBO> selectAttr(Integer goodsId) {
		return mapper.selectAttrByGoodsId(goodsId);

	}
}
