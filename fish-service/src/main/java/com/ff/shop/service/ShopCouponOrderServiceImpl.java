package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.ShopOrderMapper;
import com.ff.shop.model.ShopCouponOrder;
import com.ff.shop.model.bo.ShopOrderBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 *
 */
@Service
public class ShopCouponOrderServiceImpl extends BaseServiceImpl<ShopCouponOrder> implements ShopCouponOrderService{


}
