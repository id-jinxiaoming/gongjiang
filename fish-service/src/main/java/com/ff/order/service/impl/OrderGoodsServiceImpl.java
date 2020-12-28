package com.ff.order.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.order.dao.OrderGoodsMapper;
import com.ff.order.model.OrderGoods;
import com.ff.order.service.OrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class OrderGoodsServiceImpl extends BaseServiceImpl<OrderGoods> implements OrderGoodsService {

	@Autowired
    protected OrderGoodsMapper mapper;

    @Override
    public int insert(OrderGoods orderGoods) {
        mapper.insert(orderGoods);
        return orderGoods.getId();
    }
}
