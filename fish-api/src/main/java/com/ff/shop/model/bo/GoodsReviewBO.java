package com.ff.shop.model.bo;

import com.ff.shop.model.GoodsReview;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-08-02 11:47
 **/
public class GoodsReviewBO extends GoodsReview {
    private String goodsName;


    private String userName;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
