package com.ff.payment.service;


import com.ff.common.base.BaseService;
import com.ff.users.model.UsersAccountLog;


public interface PaymentService {

    //商品订单支付确认
    boolean goodsPayNotify(String orderId,String thirdpartyTradeId);

    //充值订单支付确认
    boolean rechargePayNotify(String orderId, String thirdpartyTradeId,String ip);

    //商家快捷支付订单确认
    boolean shopPayNotify(String orderId, String thirdpartyTradeId,String ip);

    //代金券支付订单确认
    boolean shopCouponPayNotify(String orderId, String thirdpartyTradeId,String ip);

}
