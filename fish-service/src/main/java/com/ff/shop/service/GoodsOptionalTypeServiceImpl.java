package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.shop.dao.GoodsOptionalTypeMapper;
import com.ff.shop.model.GoodsOptionalType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
public class GoodsOptionalTypeServiceImpl implements GoodsOptionalTypeService{
	@Autowired
	protected GoodsOptionalTypeMapper mapper;
	@Override
	public List<GoodsOptionalType> selectAll() {
		return mapper.selectList(null);
	}

	@Override
	public GoodsOptionalType findByPrimaryKey(String id) {
		return mapper.selectById(id);
	}

	@Override
	public int insert(GoodsOptionalType t) {
		return mapper.insert(t);
	}

	@Override
	public int updateByPrimaryKey(GoodsOptionalType t) {
		return mapper.updateById(t);
	}

	@Override
	public int deleteByPrimaryKey(String[] id) {
		List<String> ids =new ArrayList<String>();
		for (String string : id) {
			ids.add(string);
		}
		return mapper.deleteBatchIds(ids);
	}

	@Override
	public Page<GoodsOptionalType> selectPage(Page<GoodsOptionalType> page, EntityWrapper<GoodsOptionalType> ew) {
		// TODO Auto-generated method stub
		if (null != ew) {
			ew.orderBy(page.getOrderByField(), page.isAsc());
		}
		page.setRecords(mapper.selectPage(page, ew));
		return page;
	}

	@Override
	public List<GoodsOptionalType> selectByT(GoodsOptionalType goodsOptionalType) {
		EntityWrapper< GoodsOptionalType> ew = new EntityWrapper<GoodsOptionalType>();
		return mapper.selectList(ew);
	}

	@Override
	public GoodsOptionalType findOne(GoodsOptionalType t) {
		return mapper.selectOne(t);
	}
}
