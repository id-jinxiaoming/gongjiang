package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.GoodsMapper;
import com.ff.shop.dao.ShopImageMapper;
import com.ff.shop.model.ShopImage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


@Service
public class ShopImageServiceImpl extends BaseServiceImpl<ShopImage> implements ShopImageService{
    @Autowired
    ShopImageMapper shopImageMapper;
    @Override
    public boolean deleteByShopId(Integer shopId) {
        Map<String, Object> map=new HashMap<>();
        map.put("shop_id",shopId);
        return shopImageMapper.deleteByMap(map)>0?true:false;
    }
}
