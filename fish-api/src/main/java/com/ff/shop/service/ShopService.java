package com.ff.shop.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.shop.model.Goods;
import com.ff.shop.model.Shop;
import com.ff.shop.model.bo.ShopBO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShopService extends BaseService<Shop> {

    List<ShopBO> selectByLngLat(Page<Map<String, ShopBO>> page, Shop model);


    void addMoney(Integer shopId,BigDecimal money);
}
