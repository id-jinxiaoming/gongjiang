package com.ff.coupon.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.coupon.dao.CouponMapper;
import com.ff.coupon.model.Coupon;
import com.ff.coupon.service.CouponService;
import com.ff.system.dao.DictMapper;
import com.ff.system.model.Dict;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.*;


@Service
public class CouponServiceImpl extends BaseServiceImpl<Coupon> implements CouponService{

	@Autowired
    protected CouponMapper couponMapper;


    @Override
    public List<Coupon> selectCoupon() {
        return couponMapper.selectCoupon();
    }
}
