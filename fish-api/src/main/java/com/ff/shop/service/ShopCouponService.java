package com.ff.shop.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.shop.model.ShopCoupon;
import com.ff.shop.model.bo.ShopOrderBO;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.List;

public interface ShopCouponService extends BaseService<ShopCoupon> {


        Boolean updateCount(Integer couponId);

}
