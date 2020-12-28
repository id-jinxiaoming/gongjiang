package com.ff.shop.model.bo;

import com.ff.common.base.BaseModel;
import com.ff.shop.model.GoodsReview;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-08-02 11:47
 **/
public class GoodsAttrBO extends BaseModel {
    private String name;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
