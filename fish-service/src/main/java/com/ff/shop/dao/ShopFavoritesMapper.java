package com.ff.shop.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.shop.model.ShopFavorites;
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
 * @since 2017-10-06
 */
public interface ShopFavoritesMapper extends BaseMapper<ShopFavorites> {

    @Select("SELECT a.*,b.title,b.sub_title subTitle,b.img_url imgUrl  FROM tb_shop_favorites a ,tb_shop b where a.shop_id=b.shop_id and a.user_id= #{userId}")
    List<ShopFavoritesBO> selectByUserId(Page<Map<String, ShopFavoritesBO>> page, Integer userId);


}