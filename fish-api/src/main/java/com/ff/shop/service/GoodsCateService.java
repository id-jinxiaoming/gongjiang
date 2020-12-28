package com.ff.shop.service;


import com.ff.common.base.BaseService;
import com.ff.common.model.JsonTreeData;
import com.ff.shop.model.GoodsCate;

import java.util.List;

public interface GoodsCateService extends BaseService<GoodsCate> {

    List<JsonTreeData> selectTree();
    //获取TREEList数据
    List<JsonTreeData> selectTreeList();

}
