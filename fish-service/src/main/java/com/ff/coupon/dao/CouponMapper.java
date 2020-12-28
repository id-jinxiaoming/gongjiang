package com.ff.coupon.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.coupon.model.Coupon;
import com.ff.system.model.Log;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface CouponMapper extends BaseMapper<Coupon>{
    @Select("select * from tb_coupon a where a.status=1 and a.end_time >= current_date and a.start_time <=current_date and a.count >(select count(*) from tb_coupon_logs where coupon_id=a.coupon_id)")
    List<Coupon> selectCoupon();
}