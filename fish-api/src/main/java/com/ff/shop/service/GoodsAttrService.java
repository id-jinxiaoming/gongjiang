package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.GoodsAttr;
import com.ff.shop.model.bo.GoodsAttrBO;

import java.util.List;

public interface GoodsAttrService extends BaseService<GoodsAttr> {

    Integer deleteByGoodsId(Integer goodsId);

    List<GoodsAttrBO> selectAttr(Integer goodsId);

}
