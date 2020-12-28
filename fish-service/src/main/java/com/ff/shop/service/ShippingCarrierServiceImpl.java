package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.common.model.ResponseData;
import com.ff.common.util.JsonUtils;
import com.ff.shop.dao.ShippingCarrierMapper;
import com.ff.shop.dao.ShippingMethodMapper;
import com.ff.shop.model.ShippingCarrier;
import com.ff.shop.model.ShippingMethod;
import com.ff.shop.model.ex.ShipingParams;
import com.ff.users.dao.UsersConsigneeMapper;
import com.ff.users.model.UsersConsignee;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ShippingCarrierServiceImpl extends BaseServiceImpl<ShippingCarrier> implements ShippinpCarrierService{

    @Autowired
    ShippingCarrierMapper shippingCarrierMapper;


}
