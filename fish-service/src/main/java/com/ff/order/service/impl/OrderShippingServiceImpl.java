package com.ff.order.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.order.dao.OrderShippingMapper;
import com.ff.order.model.OrderShipping;
import com.ff.order.model.bo.OrderShippingBO;
import com.ff.order.service.OrderShippingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 *
 */
@Service
public class OrderShippingServiceImpl extends BaseServiceImpl<OrderShipping> implements OrderShippingService {

	@Autowired
    protected OrderShippingMapper mapper;


    @Override
    public List<OrderShippingBO> selectByOrdreId(String orderId) {
        return mapper.selectByOrdreId(orderId);
    }

    @Override
    public List<OrderShippingBO> selectByPage(Page<OrderShippingBO> page, String id) {
        return mapper.selectByPage(page,id);
    }

    @Override
    public OrderShippingBO findByOrderId(String orderId) {

        return  mapper.findByOrdreId(orderId);
    }
}
