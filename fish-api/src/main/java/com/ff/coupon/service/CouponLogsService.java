package com.ff.coupon.service;


import com.ff.common.base.BaseService;
import com.ff.coupon.model.CouponLogs;

import java.math.BigDecimal;
import java.util.List;

public interface CouponLogsService extends BaseService<CouponLogs> {

    Integer selectCount(Integer couponId);

    List<CouponLogs> selectEnableCoupon(BigDecimal amount,Integer userId);
}
