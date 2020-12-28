package com.ff.shop.dao;

import com.ff.common.base.BaseMapper;
import com.ff.shop.model.ShopCoupon;
import com.ff.shop.model.ShopCouponLogs;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-09-26
 */
public interface ShopCouponLogsMapper extends BaseMapper<ShopCouponLogs> {

    @Select("select count(*) from tb_shop_coupon_logs where coupon_id=#{couponId} ")
    Integer selectCouponCount(Integer couponId);

}