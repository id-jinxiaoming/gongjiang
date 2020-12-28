package com.ff.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.system.dao.LogMapper;
import com.ff.system.model.Log;
import com.ff.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@Service
public class LogServiceImpl  implements LogService{
	@Autowired
	LogMapper mapper;

	@Override
	public List<Log> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectList(null);
	}

	@Override
	public Log findByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return mapper.selectById(id);
	}

	@Override
	public int insert(Log t) {
		String id = UUID.randomUUID().toString();
		Field field;
		try {
			field = t.getClass().getDeclaredField("id");
			field.setAccessible(true);
			field.set(t, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return mapper.insert(t);
	}

	@Override
	public int updateByPrimaryKey(Log t) {
		// TODO Auto-generated method stub
		return mapper.updateById(t);
	}

	@Override
	public int deleteByPrimaryKey(String[] id) {
		// TODO Auto-generated method stub
		List<String> ids =new ArrayList<String>();
		for (String string : id) {
			ids.add(string);
		}
		return mapper.deleteBatchIds(ids);
	}
	
	

	@Override
	public List<Log> selectByT(Log t) {
		// TODO Auto-generated method stub
		EntityWrapper< Log> ew = new EntityWrapper<Log>();
		return mapper.selectList(ew);
	}

	@Override
	public Log findOne(Log t) {
		return mapper.selectOne(t);
	}

	@Override
	public Page<Log> selectPage(Page<Log> page, EntityWrapper<Log> ew) {
		// TODO Auto-generated method stub
	  if (null != ew) {
	      ew.orderBy(page.getOrderByField(), page.isAsc());
	  }
	  page.setRecords(mapper.selectPage(page, ew));
	  return page;
	}




	@Override
	public Page<Map<String, Object>> selectPageByAccount(Page<Map<String, Object>> page, String account) {
		page.setRecords(mapper.selectPageByAccount(page, account));
		return page;
	}
}
