package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.ShopCouponLogsMapper;
import com.ff.shop.dao.ShopCouponMapper;
import com.ff.shop.model.ShopCoupon;
import com.ff.shop.model.ShopCouponLogs;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ShopCouponLogsServiceImpl extends BaseServiceImpl<ShopCouponLogs> implements ShopCouponLogsService{

    @Autowired
    ShopCouponLogsMapper shopCouponLogsMapper;
    @Override
    public Integer selectCount(Integer couponId) {

        return   shopCouponLogsMapper.selectCouponCount(couponId);
    }
}
