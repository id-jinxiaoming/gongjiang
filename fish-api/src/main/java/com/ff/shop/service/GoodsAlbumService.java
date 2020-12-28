package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.shop.model.GoodsAlbum;

public interface GoodsAlbumService extends BaseService<GoodsAlbum> {

    Integer deleteByGoodsId(Integer id);

}
