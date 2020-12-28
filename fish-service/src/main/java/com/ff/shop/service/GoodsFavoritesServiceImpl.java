package com.ff.shop.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.common.model.ResponseData;
import com.ff.order.dao.OrderGoodsMapper;
import com.ff.order.model.OrderGoods;
import com.ff.shop.dao.GoodsFavoritesMapper;
import com.ff.shop.dao.GoodsReviewMapper;
import com.ff.shop.dao.ShopFavoritesMapper;
import com.ff.shop.model.GoodsFavorites;
import com.ff.shop.model.GoodsReview;
import com.ff.shop.model.bo.GoodsFavoritesBO;
import com.ff.shop.model.bo.GoodsReviewBO;
import com.ff.shop.model.bo.GoodsReviewUserBO;
import com.ff.shop.model.bo.ShopFavoritesBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GoodsFavoritesServiceImpl extends BaseServiceImpl<GoodsFavorites> implements GoodsFavoritesService{
    @Autowired
    GoodsFavoritesMapper goodsFavoritesMapper;


    @Override
    public List<GoodsFavoritesBO> selectByUserId(Page<Map<String, GoodsFavoritesBO>> page, Integer userId) {
        return goodsFavoritesMapper.selectByUserId(page,userId);
    }




}
