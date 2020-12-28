package com.ff.shop.dao;


import com.ff.common.base.BaseMapper;
import com.ff.shop.model.GoodsAttr;
import com.ff.shop.model.bo.GoodsAttrBO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-01
 */
public interface GoodsAttrMapper extends BaseMapper<GoodsAttr> {
    @Select("select b.name,a.value from tb_goods_attr a LEFT JOIN tb_goods_cate_attr b on a.attr_id=b.attr_id where goods_id=#{goodsId}")
    List<GoodsAttrBO> selectAttrByGoodsId(Integer goodsId);

}