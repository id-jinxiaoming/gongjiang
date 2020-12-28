package com.ff.shop.dao;

import com.ff.common.base.BaseMapper;
import com.ff.shop.model.Area;
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
public interface AreaMapper extends BaseMapper<Area> {
    @Select("select * from tb_area where area_id=#{area}")
    Area selectByArea(@Param("area")String area);



}