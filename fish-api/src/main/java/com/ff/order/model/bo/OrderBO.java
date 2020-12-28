package com.ff.order.model.bo;

import com.ff.order.model.Order;
import com.ff.order.model.OrderGoods;

import java.util.List;

public class OrderBO {
    private Order order;
    private List<OrderGoodsBO> orderGoods;

    private boolean review;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderGoodsBO> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoodsBO> orderGoods) {
        this.orderGoods = orderGoods;
    }

    public boolean isReview() {
        return review;
    }

    public void setReview(boolean review) {
        this.review = review;
    }
}
