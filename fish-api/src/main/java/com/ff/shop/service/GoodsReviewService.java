package com.ff.shop.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.common.model.ResponseData;
import com.ff.shop.model.GoodsReview;
import com.ff.shop.model.bo.GoodsReviewBO;
import com.ff.shop.model.bo.GoodsReviewUserBO;
import com.ff.shop.model.bo.ShopFavoritesBO;

import java.util.List;
import java.util.Map;

public interface GoodsReviewService extends BaseService<GoodsReview> {

    List<GoodsReviewBO> selectListByT(GoodsReview t);

    List<GoodsReviewUserBO> selectListByGoodsId(Integer id,Page page);
    //商品评价
    ResponseData GoodsReview(List<GoodsReview> goodsReviews, String orderId, Integer userId);
}
