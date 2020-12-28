package com.ff.shop.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.shop.model.Shop;
import com.ff.shop.model.ShopCate;
import com.ff.shop.model.ShopMoney;
import com.ff.shop.model.bo.ShopBO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-09-26
 */
public interface ShopMoneyMapper extends BaseMapper<ShopMoney> {

    @Insert("insert into tb_shop_money(shop_id,money,create_time,create_ip,intro,order_id) values(#{shopMoney.shopId},#{shopMoney.money},#{shopMoney.createTime},#{shopMoney.createIp},#{shopMoney.intro},#{shopMoney.orderId})")
    boolean addLog(@Param("shopMoney")ShopMoney shopMoney);

}