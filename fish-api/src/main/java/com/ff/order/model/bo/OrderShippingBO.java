package com.ff.order.model.bo;

import com.ff.order.model.Order;
import com.ff.order.model.OrderShipping;

import java.util.List;

public class OrderShippingBO extends OrderShipping {
   private String name;

    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
