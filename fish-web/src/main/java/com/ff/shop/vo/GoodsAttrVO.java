package com.ff.shop.vo;

import com.ff.shop.model.Goods;
import com.ff.shop.model.GoodsAttr;

import java.util.List;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-08-02 10:21
 **/
public class GoodsAttrVO {
    private List<GoodsAttr> goodsAttrs;

    public List<GoodsAttr> getGoodsAttrs() {
        return goodsAttrs;
    }

    public void setGoodsAttrs(List<GoodsAttr> goodsAttrs) {
        this.goodsAttrs = goodsAttrs;
    }
}
