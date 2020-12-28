package com.ff.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseMapper;
import com.ff.system.model.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface LogMapper  extends BaseMapper<Log>{
   List<Map<String,Object>> selectPageByAccount(Page<Map<String, Object>> page,@Param(value="account") String account);
}