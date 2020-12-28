package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.Shop;
import com.ff.shop.model.ShopCate;
import com.ff.shop.model.ShopMoney;

public interface ShopMoneyService extends BaseService<ShopMoney> {

    boolean addLog(ShopMoney shopMoney);


}
