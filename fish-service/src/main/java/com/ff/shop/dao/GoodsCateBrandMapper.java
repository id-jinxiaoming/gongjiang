package com.ff.shop.dao;

import com.ff.common.base.BaseMapper;
import com.ff.shop.model.GoodsCateBrand;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-07-25
 */
public interface GoodsCateBrandMapper extends BaseMapper<GoodsCateBrand> {

    int deleteByCateId(Integer cateId);


}