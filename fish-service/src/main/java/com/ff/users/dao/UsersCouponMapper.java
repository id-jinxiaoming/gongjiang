package com.ff.users.dao;


import com.ff.common.base.BaseMapper;
import com.ff.users.model.UsersCoupon;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-10-07
 */
public interface UsersCouponMapper extends BaseMapper<UsersCoupon> {

    Integer setDefault(Integer id,Integer def);
}