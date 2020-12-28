package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.ShopImage;

public interface ShopImageService extends BaseService<ShopImage> {

    boolean deleteByShopId(Integer shopId);

}
