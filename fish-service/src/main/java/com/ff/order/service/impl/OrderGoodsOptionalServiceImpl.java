package com.ff.order.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.order.dao.OrderGoodsOptionalMapper;
import com.ff.order.model.OrderGoodsOptional;
import com.ff.order.service.OrderGoodsOptionalService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class OrderGoodsOptionalServiceImpl extends BaseServiceImpl<OrderGoodsOptional> implements OrderGoodsOptionalService {

	@Autowired
    protected OrderGoodsOptionalMapper mapper;


}
