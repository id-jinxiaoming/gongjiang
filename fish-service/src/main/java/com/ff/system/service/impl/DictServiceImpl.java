package com.ff.system.service.impl;


import java.lang.reflect.Field;
import java.util.*;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.system.dao.DictMapper;
import com.ff.system.model.Dict;
import com.ff.system.service.DictService;


@Service
public class DictServiceImpl implements DictService{

	@Autowired
    protected DictMapper mapper;
	
	@Override
	public List<Dict> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectList(null);
	}

	@Override
	public Dict findByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return mapper.selectById(id);
	}

	@Override
	public int insert(Dict t) {
		String id = UUID.randomUUID().toString();
		Field field;
		try {
			field = t.getClass().getDeclaredField("id");
			field.setAccessible(true);
			field.set(t, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return 1;
    	return mapper.insert(t);
	}

	@Override
	public int updateByPrimaryKey(Dict t) {
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
	public List<Dict> selectByT(Dict t) {
		// TODO Auto-generated method stub
		EntityWrapper< Dict> ew = new EntityWrapper<Dict>();
		return mapper.selectList(ew);
	}

	@Override
	public Dict findOne(Dict t) {
		return mapper.selectOne(t);
	}

	@Override
	public Page<Dict> selectPage(Page<Dict> page, EntityWrapper<Dict> ew) {
		// TODO Auto-generated method stub
	  if (null != ew) {
	      ew.orderBy(page.getOrderByField(), page.isAsc());
	  }
	  page.setRecords(mapper.selectPage(page, ew));
	  return page;
	}


	@Override
	public List<Dict> selectByCode(String code) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code",code);
		return mapper.selectByMap(map);
	}
}
