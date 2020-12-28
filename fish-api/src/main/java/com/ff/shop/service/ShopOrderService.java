package com.ff.shop.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.shop.model.ShopOrder;
import com.ff.shop.model.bo.GoodsReviewUserBO;
import com.ff.shop.model.bo.ShopOrderBO;

import java.math.BigDecimal;
import java.util.List;

public interface ShopOrderService extends BaseService<ShopOrder> {

    BigDecimal selectMoneySum(Integer shopId);

    Integer selectOrderCount(Integer shopId);

    List<ShopOrderBO> selectListByPage(Integer userId,Integer isPay, Page page);

    List<ShopOrderBO> selectListByShopId(Integer shopId, Page page);


    List<ShopOrderBO> selectListByPage( Page page);


}
