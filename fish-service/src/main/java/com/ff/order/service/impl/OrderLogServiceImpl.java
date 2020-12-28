package com.ff.order.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.order.dao.OrderLogMapper;
import com.ff.order.model.OrderLog;
import com.ff.order.model.bo.OrderLogBO;
import com.ff.order.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog> implements OrderLogService {

	@Autowired
    protected OrderLogMapper mapper;



}
