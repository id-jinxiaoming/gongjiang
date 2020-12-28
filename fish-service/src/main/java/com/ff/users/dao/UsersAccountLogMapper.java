package com.ff.users.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.ff.common.base.BaseMapper;
import com.ff.users.model.UsersAccountLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-04
 */
public interface UsersAccountLogMapper extends BaseMapper<UsersAccountLog> {

    List<Map<String,Object>> selectPageByUserName(Page<Map<String, Object>> page, @Param(value="username") String username );
}