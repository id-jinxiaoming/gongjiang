package com.ff.shop.service;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.common.model.ResponseData;
import com.ff.shop.model.ShippingMethod;
import com.ff.shop.model.ShopFavorites;
import com.ff.shop.model.bo.ShopFavoritesBO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShippingMethodService extends BaseService<ShippingMethod> {


    ResponseData getFreight(Integer cid, Integer shippingId,BigDecimal cartWeight,Integer cartNum);

}
