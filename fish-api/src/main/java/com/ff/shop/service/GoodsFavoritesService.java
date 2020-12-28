package com.ff.shop.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.common.model.ResponseData;
import com.ff.shop.model.GoodsFavorites;
import com.ff.shop.model.GoodsReview;
import com.ff.shop.model.bo.GoodsFavoritesBO;
import com.ff.shop.model.bo.GoodsReviewBO;
import com.ff.shop.model.bo.GoodsReviewUserBO;
import com.ff.shop.model.bo.ShopFavoritesBO;

import java.util.List;
import java.util.Map;

public interface GoodsFavoritesService extends BaseService<GoodsFavorites> {

    List<GoodsFavoritesBO> selectByUserId(Page<Map<String, GoodsFavoritesBO>> page, Integer userId);

}
