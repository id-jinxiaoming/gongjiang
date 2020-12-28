package com.ff.users.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersCouponMapper;
import com.ff.users.model.UsersConsignee;
import com.ff.users.model.UsersCoupon;
import com.ff.users.service.UsersCouponService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.parser.Entity;


@Service
public class UsersCouponServiceImpl extends BaseServiceImpl<UsersCoupon> implements UsersCouponService {

	@Autowired
    protected UsersCouponMapper usersCouponMapper;


    @Override
    public int selectCount(UsersCoupon model) {
        EntityWrapper<UsersCoupon> w=new EntityWrapper<>();
        w.setEntity(model);;
        return usersCouponMapper.selectCount(w);
    }


}
