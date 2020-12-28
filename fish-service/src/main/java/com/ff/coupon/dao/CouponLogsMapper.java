package com.ff.coupon.dao;

import com.ff.common.base.BaseMapper;
import com.ff.coupon.model.CouponLogs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;


public interface CouponLogsMapper extends BaseMapper<CouponLogs>{

    @Select("select count(*) from tb_coupon_logs where coupon_id=#{couponId}")
    Integer selectLogsCount(Integer couponId);


    @Select("select * from tb_coupon_logs where user_id=#{userId} and min_price<=#{amount} and status=0  and limit_time >= current_date")
    List<CouponLogs> selectEnableCoupon(@Param(value="amount") BigDecimal amount,@Param(value="userId") Integer userId);
}