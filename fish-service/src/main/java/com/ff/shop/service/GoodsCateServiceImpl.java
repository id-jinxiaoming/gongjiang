package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.model.Attributes;
import com.ff.common.model.JsonTreeData;
import com.ff.common.util.TreeNodeUtil;
import com.ff.shop.dao.GoodsCateBrandMapper;
import com.ff.shop.dao.GoodsCateMapper;
import com.ff.shop.model.GoodsCate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
public class GoodsCateServiceImpl implements GoodsCateService{
	@Autowired
	GoodsCateMapper mapper;
	@Autowired
	GoodsCateBrandMapper goodsCateBrandMapper;
	@Override
	public List<GoodsCate> selectAll() {

		return mapper.selectList(null);
	}

	@Override
	public GoodsCate findByPrimaryKey(String id) {

		return mapper.selectById(id);
	}

	@Override
	public int insert(GoodsCate goodsCate) {
		int flag=mapper.insert(goodsCate);
		if(flag!=0)
		{
			return goodsCate.getCateId();
		}

		return flag;

	}

	@Override
	public int updateByPrimaryKey(GoodsCate goodsCate) {
		return mapper.updateById(goodsCate);
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
	public Page<GoodsCate> selectPage(Page<GoodsCate> page, EntityWrapper<GoodsCate> ew) {
		if (null != ew) {
			ew.orderBy(page.getOrderByField(), page.isAsc());
		}
		page.setRecords(mapper.selectPage(page, ew));
		return page;
	}

	@Override
	public List<GoodsCate> selectByT(GoodsCate goodsCate) {
		EntityWrapper< GoodsCate> ew = new EntityWrapper<GoodsCate>();
		ew.eq("parent_id",goodsCate.getParentId());
		return mapper.selectList(ew);
	}

	@Override
	public GoodsCate findOne(GoodsCate t) {
		return mapper.selectOne(t);
	}

	@Override
	public List<JsonTreeData> selectTree() {
		List<GoodsCate> resourcesList =mapper.selectList(null);
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();

		for (GoodsCate res : resourcesList) {
			JsonTreeData treeData = new JsonTreeData();
			treeData.setId(res.getCateId().toString());
			treeData.setPid(res.getParentId().toString());
			treeData.setText(res.getCateName());
			treeData.attributes=new Attributes();
			treeData.attributes.setSeq(res.getSeq());
			treeData.attributes.setUrl(res.getImgUrl());
			treeDataList.add(treeData);
		}
		List<JsonTreeData> newTreeDataList = TreeNodeUtil.getfather(treeDataList);
		return newTreeDataList;
	}

	@Override
	public List<JsonTreeData> selectTreeList() {
		List<GoodsCate> resourcesList =mapper.selectList(null);
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();

		for (GoodsCate res : resourcesList) {
			JsonTreeData treeData = new JsonTreeData();
			treeData.setId(res.getCateId().toString());
			treeData.setPid(res.getParentId().toString());
			treeData.setText(res.getCateName());
			treeData.attributes=new Attributes();
			treeData.attributes.setSeq(res.getSeq());
			treeDataList.add(treeData);
		}
		List<JsonTreeData> newTreeDataList = TreeNodeUtil.getfatherNode(treeDataList);
		return newTreeDataList;
	}
}
