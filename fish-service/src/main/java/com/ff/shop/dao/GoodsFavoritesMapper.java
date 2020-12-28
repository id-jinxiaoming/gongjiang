package com.ff.shop.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.shop.model.GoodsFavorites;
import com.ff.shop.model.bo.GoodsFavoritesBO;
import com.ff.shop.model.bo.ShopFavoritesBO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-07-24
 */
public interface GoodsFavoritesMapper extends BaseMapper<GoodsFavorites> {
    @Select("SELECT a.*,b.goods_name,b.now_price,b.goods_image  FROM tb_goods_favorites a ,tb_goods b where a.goods_id=b.goods_id and a.user_id= #{userId}")
    List<GoodsFavoritesBO> selectByUserId(Page<Map<String, GoodsFavoritesBO>> page, Integer userId);

}