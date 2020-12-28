package com.ff.rechage.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.rechage.model.Rechage;
import com.ff.rechage.model.RechageOrder;
import com.ff.rechage.model.bo.RechageOrderBO;
import com.ff.shop.model.Shop;
import com.ff.shop.model.bo.ShopBO;
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
 * @since 2017-09-26
 */
public interface RechageOrderMapper extends BaseMapper<RechageOrder> {
    @Select(" select a.*,b.username from tb_rechage_order a left join tb_users b on a.user_id=b.user_id order by a.order_id desc")
    List<RechageOrderBO> selectListByPage(Page< RechageOrderBO> page, @Param("rechageOrder")RechageOrder model);
}