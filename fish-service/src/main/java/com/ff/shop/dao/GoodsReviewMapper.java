package com.ff.shop.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.shop.model.GoodsReview;
import com.ff.shop.model.bo.GoodsReviewBO;
import com.ff.shop.model.bo.GoodsReviewUserBO;
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
public interface GoodsReviewMapper extends BaseMapper<GoodsReview> {
    List<GoodsReviewBO> selectListByT(GoodsReview goodsReview);
    @Select("select a.*,b.username,b.avatar from tb_goods_review a LEFT JOIN tb_users b on a.user_id = b.user_id where a.goods_id=#{goodsId} and a.status=1 order by a.created_date desc")
    List<GoodsReviewUserBO> selectListByGoodsId(Integer goodsId, Page page);
}