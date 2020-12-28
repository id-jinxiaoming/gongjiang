package com.ff.order.model.bo;

import com.ff.order.model.Order;
import com.ff.order.model.OrderGoods;
import com.ff.order.model.OrderLog;

import java.util.List;

public class OrderLogBO extends OrderLog {
   private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
