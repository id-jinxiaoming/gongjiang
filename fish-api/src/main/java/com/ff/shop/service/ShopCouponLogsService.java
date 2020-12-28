package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.ShopCouponLogs;

public interface ShopCouponLogsService extends BaseService<ShopCouponLogs> {

    
    Integer selectCount(Integer couponId);

}
