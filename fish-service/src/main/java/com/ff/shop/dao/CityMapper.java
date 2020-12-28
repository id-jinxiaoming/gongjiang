package com.ff.shop.dao;

import com.ff.common.base.BaseMapper;
import com.ff.shop.model.City;
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
public interface CityMapper extends BaseMapper<City> {
    @Select("select * from tb_city where city_id=#{city}")
    City selectByCity(@Param("city")String city);
}