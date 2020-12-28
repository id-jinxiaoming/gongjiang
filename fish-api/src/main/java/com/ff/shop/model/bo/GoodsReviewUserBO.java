package com.ff.shop.model.bo;

import com.ff.shop.model.GoodsReview;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-08-02 11:47
 **/
public class GoodsReviewUserBO extends GoodsReview {



    private String userName;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
