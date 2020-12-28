package com.ff.common.base;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;



public interface BaseService<T extends BaseModel> {

	//查询所有数据
	List<T> selectAll();
	
	//通过主键查询单个记录
	T findByPrimaryKey(String id);
	
	//插入记录
    int insert(T t);
    
    //通过主键列更新数据
    int updateByPrimaryKey(T t);
    
    //批量删除数据
    int deleteByPrimaryKey(String[] id);

    //分页查询记录
    Page<T> selectPage(Page<T> page, EntityWrapper<T> ew);
    
	//查询多条记录
    List<T> selectByT(T t);
    
    //查询单条记录
    T findOne(T t);



	
}
