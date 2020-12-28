package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.shop.dao.GoodsCateAttrMapper;
import com.ff.shop.model.GoodsCateAttr;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
public class GoodsCateAttrServiceImpl implements GoodsCateAttrService{
	@Autowired
	GoodsCateAttrMapper mapper;

	@Override
	public List<GoodsCateAttr> selectAll() {
		return mapper.selectList(null);
	}

	@Override
	public GoodsCateAttr findByPrimaryKey(String id) {
			return mapper.selectById(id);
	}

	@Override
	public int insert(GoodsCateAttr t) {
		return mapper.insert(t);
	}

	@Override
	public int updateByPrimaryKey(GoodsCateAttr t) {
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
	public Page<GoodsCateAttr> selectPage(Page<GoodsCateAttr> page, EntityWrapper<GoodsCateAttr> ew) {
		// TODO Auto-generated method stub
		if (null != ew) {
			ew.orderBy(page.getOrderByField(), page.isAsc());
		}
		page.setRecords(mapper.selectPage(page, ew));
		return page;
	}

	@Override
	public List<GoodsCateAttr> selectByT(GoodsCateAttr goodsCateAttr) {
		EntityWrapper< GoodsCateAttr> ew = new EntityWrapper<GoodsCateAttr>();
		return mapper.selectList(ew);
	}

	@Override
	public GoodsCateAttr findOne(GoodsCateAttr t) {
		return mapper.selectOne(t);
	}
}
