package com.ff.common.base;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T>{
	@Autowired
	protected BaseMapper<T> mapper;


	public List<T> selectAll() {
		return mapper.selectList(null);
	}


	public T findByPrimaryKey(String id) {
		return mapper.selectById(id);
	}


	public int insert(T t) {
		return mapper.insert(t);
	}


	public int updateByPrimaryKey(T t) {
		return mapper.updateById(t);
	}


	public int deleteByPrimaryKey(String[] id) {
		List<String> ids =new ArrayList<String>();
		for (String string : id) {
			ids.add(string);
		}
		return mapper.deleteBatchIds(ids);
	}


	public Page<T> selectPage(Page<T> page, EntityWrapper<T> ew) {
		if (null != ew) {
			ew.orderBy(page.getOrderByField(), page.isAsc());
		}
		page.setRecords(mapper.selectPage(page, ew));
		return page;
	}


	public List<T> selectByT(T t) {
		EntityWrapper<T> ew = new EntityWrapper<T>();
		ew.setEntity(t);
		return mapper.selectList(ew);
	}


	public T findOne(T t) {
		return mapper.selectOne(t);
	}
	
	
}
