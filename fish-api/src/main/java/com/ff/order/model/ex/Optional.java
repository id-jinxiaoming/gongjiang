package com.ff.order.model.ex;

import java.io.Serializable;

public class Optional implements Serializable, Comparable<Optional> {
    private Integer optTypeId;
    private Integer optId;
    private String optType;
    private String optText;
    private String price;


    public Integer getOptTypeId() {
        return optTypeId;
    }

    public void setOptTypeId(Integer optTypeId) {
        this.optTypeId = optTypeId;
    }

    public Integer getOptId() {
        return optId;
    }

    public void setOptId(Integer optId) {
        this.optId = optId;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getOptText() {
        return optText;
    }

    public void setOptText(String optText) {
        this.optText = optText;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int compareTo(Optional o) {
        int i = this.getOptId() - o.getOptId();
        return i;
    }
}
