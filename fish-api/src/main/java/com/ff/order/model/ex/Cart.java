package com.ff.order.model.ex;

import com.ff.order.model.Order;
import com.ff.order.model.bo.OrderGoodsBO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Cart implements Serializable {
    private Integer goodsId;
    private Integer num;

    private String title;
    private String image;

    private List<Optional> opt;

    private BigDecimal price;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Optional> getOpt() {
        return opt;
    }

    public void setOpt(List<Optional> opt) {
        this.opt = opt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
