package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.GoodsMapper;
import com.ff.shop.dao.PaymentMethodMapper;
import com.ff.shop.model.Goods;
import com.ff.shop.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PaymentMethodServiceImpl extends BaseServiceImpl<PaymentMethod> implements PaymentMethodService{

	@Autowired
	PaymentMethodMapper mapper;


}
