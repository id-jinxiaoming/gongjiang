package com.ff.order.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.order.dao.OrderConsigneeMapper;
import com.ff.order.model.OrderConsignee;
import com.ff.order.service.OrderConsigneeService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class OrderConsigneeServiceImpl extends BaseServiceImpl<OrderConsignee> implements OrderConsigneeService {

	@Autowired
    protected OrderConsigneeMapper mapper;
	

}
