package com.ff.order.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.order.model.OrderShipping;
import com.ff.order.model.bo.OrderShippingBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-13
 */
public interface OrderShippingMapper extends BaseMapper<OrderShipping> {
    @Select("SELECT a.*,b.name,b.code FROM tb_order_shipping a LEFT JOIN tb_shipping_carrier b on a.carrier_id=b.id where a.order_id=#{orderId}")
    List<OrderShippingBO> selectByOrdreId(String orderId);



    List<OrderShippingBO> selectByPage(Page<OrderShippingBO> page, @Param(value="id") String id );


    @Select("SELECT a.*,b.name,b.code FROM tb_order_shipping a LEFT JOIN tb_shipping_carrier b on a.carrier_id=b.id where a.order_id=#{orderId}")
    OrderShippingBO findByOrdreId(String orderId);


}