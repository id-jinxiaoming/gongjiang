package com.ff.shop.dao;

import com.ff.common.base.BaseMapper;
import com.ff.shop.model.Province;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-07-25
 */
public interface ProvinceMapper extends BaseMapper<Province> {

    Province selectByProvince(String province);

}