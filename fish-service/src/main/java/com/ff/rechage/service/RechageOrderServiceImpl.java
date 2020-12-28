package com.ff.rechage.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.rechage.dao.RechageOrderMapper;
import com.ff.rechage.model.RechageOrder;
import com.ff.rechage.model.bo.RechageOrderBO;
import com.ff.users.dao.UsersAccountLogMapper;
import com.ff.users.dao.UsersAccountMapper;
import com.ff.users.dao.UsersCouponMapper;
import com.ff.users.model.UsersAccount;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.model.UsersCoupon;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


@Service
public class RechageOrderServiceImpl extends BaseServiceImpl<RechageOrder> implements RechageOrderService{
    @Autowired
    RechageOrderMapper rechageOrderMapper;

    @Autowired
    protected UsersAccountMapper usersAccountMapper;

    @Autowired
    protected UsersAccountLogMapper usersAccountLogMapper;
    @Autowired
    protected UsersCouponMapper usersCouponMapper;
    @Override
    public int insert(RechageOrder model) {
        mapper.insert(model);
        return model.getOrderId();
    }

    @Override
    public Boolean payment(RechageOrder model) {
        //TUDO事务处理
        //更新用户资金
        UsersAccount accountMap=new UsersAccount();
        accountMap.setUserId(model.getUserId());
        UsersAccount account=usersAccountMapper.selectOne(accountMap);
        account.setBalance(account.getBalance().add(model.getMoney()));
       usersAccountMapper.updateById(account);
        //写入用户资金日志
        UsersAccountLog log=new UsersAccountLog();
        log.setUserId(model.getUserId());
        log.setBalance(model.getMoney());
        log.setCause("充值套餐");
        log.setDateline(Integer.parseInt((String.valueOf(new Date().getTime()/1000))));
        //更新订单状态
        usersAccountLogMapper.insert(log);
        rechageOrderMapper.updateById(model);
        //增加优惠券
        UsersCoupon coupon=new UsersCoupon();
        coupon.setCreateTime(new Date());
        coupon.setIsUsed(0);
        coupon.setMoney(model.getCoupon());
        coupon.setOrderId(model.getOrderId());
        coupon.setStatus(1);
        coupon.setUserId(model.getUserId());
        usersCouponMapper.insert(coupon);
        return null;
    }

    @Override
    public List<RechageOrderBO> selectList(Page<RechageOrderBO> page, RechageOrder model) {
        return rechageOrderMapper.selectListByPage(page,model);
    }
}
