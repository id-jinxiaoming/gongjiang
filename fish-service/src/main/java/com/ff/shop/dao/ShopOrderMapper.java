package com.ff.shop.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.shop.model.ShopOrder;
import com.ff.shop.model.bo.GoodsReviewUserBO;
import com.ff.shop.model.bo.ShopOrderBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-09-26
 */
public interface ShopOrderMapper extends BaseMapper<ShopOrder> {
    @Select("SELECT sum(money)  FROM tb_shop_order  where shop_id= #{shopId} and is_pay=1 and pay_time>#{date}")
    BigDecimal selectMoneySum( @Param("shopId")Integer shopId,@Param("date") String date);

    @Select("SELECT count(*)  FROM tb_shop_order  where shop_id= #{shopId} and is_pay=1")
    Integer selectOrderCount(Integer shopId);


    @Select("select a.*,b.title as shopName,b.img_url from tb_shop_order a LEFT JOIN tb_shop b on a.shop_id = b.shop_id where a.is_pay=#{isPay} and a.user_id=#{userId} order by a.create_time desc")
    List<ShopOrderBO> selectListByUserId(@Param("userId")Integer userId,@Param("isPay")Integer isPay, Page page);


    @Select("select a.*,b.title as shopName,b.img_url from tb_shop_order a LEFT JOIN tb_shop b on  a.shop_id =b.shop_id   where a.shop_id=#{shopId} and a.is_pay=1  order by a.create_time desc")
    List<ShopOrderBO> selectListByShopId(@Param("shopId")Integer shopId, Page page);



    @Select("select a.*,b.title as shopName,b.img_url,c.username,d.name as paymentName from ((tb_shop_order a LEFT JOIN tb_shop b on a.shop_id = b.shop_id) left JOIN tb_users as c on a.user_id =c.user_id) LEFT JOIN tb_payment_method as d on a.payment_method=d.id  order by a.create_time desc")
    List<ShopOrderBO> selectListByPage( Page page);
}