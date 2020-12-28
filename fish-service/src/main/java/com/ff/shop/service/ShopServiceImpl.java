package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.common.util.MapDistance;
import com.ff.shop.dao.ShopMapper;
import com.ff.shop.model.Goods;
import com.ff.shop.model.Shop;
import com.ff.shop.model.bo.ShopBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService{
    @Autowired
    ShopMapper shopMapper;

    /**
     * 根据坐标获取附近商家
     * @param page
     * @param model
     * @return
     */
    @Override
    public List<ShopBO> selectByLngLat(Page<Map<String, ShopBO>> page, Shop model) {
        List<ShopBO> list=shopMapper.selectByLngLat(page, model);
        if(list!=null&&list.size()>0)
        {
            for (ShopBO item:list) {
                String juli = MapDistance.getDistance(model.getLat(),model.getLng(),item.getLat(),item.getLng());
                Double cny = Double.parseDouble(juli);//转换成Double
                DecimalFormat df = new DecimalFormat("0.0");//格式化  保留 "."号后面一位
                float distance = 0;
                distance = Float.valueOf(df.format(cny));
                item.setDistance(String.valueOf(distance));
            }
        }

        return list;

    }

    @Override
    public void addMoney(Integer shopId,BigDecimal money) {
        shopMapper.addMoney(shopId,money);
    }

    @Override
    public int insert(Shop model) {
        mapper.insert(model);
        return model.getShopId();
    }
}
