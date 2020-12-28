package com.ff.coupon.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.coupon.dao.CouponLogsMapper;
import com.ff.coupon.dao.CouponMapper;
import com.ff.coupon.model.Coupon;
import com.ff.coupon.model.CouponLogs;
import com.ff.coupon.service.CouponLogsService;
import com.ff.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;


@Service
public class CouponLogsServiceImpl extends BaseServiceImpl<CouponLogs> implements CouponLogsService {

    @Autowired
    CouponLogsMapper couponLogsMapper;
    @Override
    public Integer selectCount(Integer couponId) {

        return couponLogsMapper.selectLogsCount(couponId);
    }

    @Override
    public List<CouponLogs> selectEnableCoupon(BigDecimal amount, Integer userId) {
        return couponLogsMapper.selectEnableCoupon(amount,userId);
    }
}
