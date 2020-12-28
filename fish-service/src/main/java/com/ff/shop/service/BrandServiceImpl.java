package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.shop.dao.BrandMapper;
import com.ff.shop.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
public class BrandServiceImpl implements BrandService{
	@Autowired
	protected BrandMapper mapper;
	@Override
	public List<Brand> selectAll() {
		return mapper.selectList(null);
	}

	@Override
	public Brand findByPrimaryKey(String id) {
		return mapper.selectById(id);
	}

	@Override
	public int insert(Brand brand) {

		return mapper.insert(brand);
	}

	@Override
	public int updateByPrimaryKey(Brand t) {
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
	public Page<Brand> selectPage(Page<Brand> page, EntityWrapper<Brand> ew) {
		// TODO Auto-generated method stub
		if (null != ew) {
			ew.orderBy(page.getOrderByField(), page.isAsc());
		}
		page.setRecords(mapper.selectPage(page, ew));
		return page;
	}

	@Override
	public List<Brand> selectByT(Brand brand) {
		// TODO Auto-generated method stub
		EntityWrapper< Brand> ew = new EntityWrapper<Brand>();
		return mapper.selectList(ew);
	}

	@Override
	public Brand findOne(Brand t) {
		return mapper.selectOne(t);
	}
}
