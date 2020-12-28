package com.ff.order.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.order.model.Order;
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
public interface OrderMapper extends BaseMapper<Order> {
    List<Map<String,Object>> selectPageByOrderId(Page<Map<String, Object>> page, @Param(value="orderId") String orderId );

    Map<String,Object> findOrderByID(@Param(value="orderId") String orderId );
    @Select("select count(*) from tb_order where order_status=#{status}")
    int selectOrderCount(@Param(value="status") Integer status );


}