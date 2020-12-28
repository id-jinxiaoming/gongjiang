package com.ff.order.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.order.model.Aftersales;
import com.ff.order.model.Order;
import com.ff.order.model.bo.AftersalesBO;
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
public interface AftersalesMapper extends BaseMapper<Aftersales> {

    @Select("SELECT a.*,b.username,b.avatar FROM tb_aftersales a LEFT JOIN tb_users b on a.user_id=b.user_id")
    List<AftersalesBO> selectByPage(Page<AftersalesBO> page);


}