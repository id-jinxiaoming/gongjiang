package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.ShopMoneyMapper;
import com.ff.shop.model.ShopCate;
import com.ff.shop.model.ShopMoney;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ShopMoneyServiceImpl extends BaseServiceImpl<ShopMoney> implements ShopMoneyService{

    @Autowired
    ShopMoneyMapper shopMoneyMapper;
    @Override
    public boolean addLog(ShopMoney shopMoney) {
        shopMoneyMapper.addLog(shopMoney);
        return false;
    }
}
