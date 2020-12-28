package com.ff.shop.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.shop.model.Shop;
import com.ff.shop.model.bo.ShopBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
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
public interface ShopMapper extends BaseMapper<Shop> {

    /**
     * 获取附近商家
     * @param page
     * @param model
     * @return
     */
    List<ShopBO> selectByLngLat(Page<Map<String, ShopBO>> page,@Param("shop")Shop model);

    @Update("update tb_shop set money=money+#{money} where shop_id=#{shopId}")
    int addMoney(@Param("shopId")Integer shopId, @Param("money")BigDecimal money);

}