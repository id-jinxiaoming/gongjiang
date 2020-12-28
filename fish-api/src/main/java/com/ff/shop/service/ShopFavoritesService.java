package com.ff.shop.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.shop.model.ShopFavorites;
import com.ff.shop.model.bo.ShopFavoritesBO;

import java.util.List;
import java.util.Map;

public interface ShopFavoritesService extends BaseService<ShopFavorites> {

    List<ShopFavoritesBO> selectByUserId(Page<Map<String, ShopFavoritesBO>> page, Integer userId);

}
