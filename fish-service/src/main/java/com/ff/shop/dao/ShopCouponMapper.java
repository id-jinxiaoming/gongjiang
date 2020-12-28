package com.ff.shop.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.shop.model.Shop;
import com.ff.shop.model.ShopCoupon;
import com.ff.shop.model.bo.ShopBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-09-26
 */
public interface ShopCouponMapper extends BaseMapper<ShopCoupon> {

    @Update("update tb_shop_coupon set count=count+1 where coupon_id=#{couponId}")
    Integer updateCount(Integer couponId);


}