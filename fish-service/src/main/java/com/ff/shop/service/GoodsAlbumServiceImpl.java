package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.GoodsAlbumMapper;
import com.ff.shop.model.GoodsAlbum;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class GoodsAlbumServiceImpl extends BaseServiceImpl<GoodsAlbum> implements GoodsAlbumService{

	@Autowired
	GoodsAlbumMapper mapper;

	@Override
	public Integer deleteByGoodsId(Integer id) {
		EntityWrapper<GoodsAlbum> ew=new EntityWrapper();
		ew.eq("goods_id",id);
		return mapper.delete(ew);
	}
}
