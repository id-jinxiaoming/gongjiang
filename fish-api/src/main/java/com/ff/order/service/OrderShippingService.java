package com.ff.order.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.order.model.OrderShipping;
import com.ff.order.model.bo.OrderShippingBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderShippingService extends BaseService<OrderShipping> {
        List<OrderShippingBO> selectByOrdreId(String orderId);

        List<OrderShippingBO> selectByPage(Page<OrderShippingBO> page,  String id );

        OrderShippingBO findByOrderId(String orderId);
}
