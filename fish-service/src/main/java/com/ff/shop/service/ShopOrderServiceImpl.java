package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.ShopOrderMapper;
import com.ff.shop.model.ShopOrder;
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
public class ShopOrderServiceImpl extends BaseServiceImpl<ShopOrder> implements ShopOrderService{

    @Autowired
    ShopOrderMapper shopOrderMapper;
    @Override
    public BigDecimal selectMoneySum(Integer shopId) {
        Date date=new Date();//取时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);

        return shopOrderMapper.selectMoneySum(shopId,dateString);
    }



    @Override
    public Integer selectOrderCount(Integer shopId) {
        return shopOrderMapper.selectOrderCount(shopId);
    }

    @Override
    public List<ShopOrderBO> selectListByPage(Integer userId,Integer isPay, Page page) {
        return shopOrderMapper.selectListByUserId(userId,isPay,page);
    }

    @Override
    public List<ShopOrderBO> selectListByShopId(Integer shopId,  Page page) {
        return shopOrderMapper.selectListByShopId(shopId,page);
    }

    @Override
    public List<ShopOrderBO> selectListByPage( Page page) {
        return shopOrderMapper.selectListByPage(page);
    }
}
