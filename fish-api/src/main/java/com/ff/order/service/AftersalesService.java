package com.ff.order.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.order.model.Aftersales;
import com.ff.order.model.Order;
import com.ff.order.model.bo.AftersalesBO;
import com.ff.order.model.ex.Cart;
import com.ff.users.model.UsersConsignee;

import java.util.List;
import java.util.Map;

public interface AftersalesService extends BaseService<Aftersales> {

    Page<AftersalesBO> selectByPage(Page<AftersalesBO> page);
}
