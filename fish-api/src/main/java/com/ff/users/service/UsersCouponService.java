package com.ff.users.service;


import com.ff.common.base.BaseService;
import com.ff.users.model.UsersCoupon;

public interface UsersCouponService extends BaseService<UsersCoupon> {

    int selectCount(UsersCoupon model);


}
