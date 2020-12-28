package com.ff.rechage.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.rechage.model.RechageOrder;
import com.ff.rechage.model.bo.RechageOrderBO;
import com.ff.shop.model.Shop;
import com.ff.shop.model.bo.ShopBO;
import com.ff.users.model.Users;

import java.util.List;
import java.util.Map;

public interface RechageOrderService extends BaseService<RechageOrder> {

    Boolean payment(RechageOrder model);


    List<RechageOrderBO> selectList(Page<RechageOrderBO> page, RechageOrder model);




}
