package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.ShopCouponMapper;
import com.ff.shop.model.ShopCate;
import com.ff.shop.model.ShopCoupon;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ShopCouponServiceImpl extends BaseServiceImpl<ShopCoupon> implements ShopCouponService{

    @Autowired
    ShopCouponMapper shopCouponMapper;
    @Override
    public Boolean updateCount(Integer couponId) {

        return shopCouponMapper.updateCount(couponId)>0?true:false;
    }
}
