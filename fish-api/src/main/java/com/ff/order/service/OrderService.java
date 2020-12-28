package com.ff.order.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.order.model.Order;
import com.ff.order.model.ex.Cart;
import com.ff.users.model.UsersConsignee;

import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService<Order> {
    Page<Map<String, Object>> selectPageByOrderId(Page<Map<String, Object>> page, String orderId);
    String createOrder(Order order, List<Cart> list, UsersConsignee usersConsignee);
    Boolean cancelOrder(String orderId);

    Boolean deliveredOrder(String orderId);

    Map<String, Object> findByOrderId( String orderId);

    Order findOrder( String orderId,Integer userId);

    int selectCount(Integer status,Integer userId);
}
