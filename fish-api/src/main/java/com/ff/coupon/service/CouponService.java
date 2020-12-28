package com.ff.coupon.service;


import com.ff.common.base.BaseService;
import com.ff.coupon.model.Coupon;

import java.util.List;

public interface CouponService extends BaseService<Coupon> {

    List<Coupon> selectCoupon();

}
