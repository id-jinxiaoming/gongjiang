package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.ShopFavoritesMapper;
import com.ff.shop.model.ShopFavorites;
import com.ff.shop.model.bo.ShopFavoritesBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Service
public class ShopFavoritesServiceImpl extends BaseServiceImpl<ShopFavorites> implements ShopFavoritesService{
    @Autowired
    ShopFavoritesMapper shopFavoritesMapper;

    @Override
    public List<ShopFavoritesBO> selectByUserId(Page<Map<String, ShopFavoritesBO>> page, Integer userId) {
        return shopFavoritesMapper.selectByUserId(page,userId);
    }
}
