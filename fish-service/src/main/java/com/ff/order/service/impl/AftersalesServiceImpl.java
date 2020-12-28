package com.ff.order.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.order.dao.AftersalesMapper;
import com.ff.order.model.Aftersales;
import com.ff.order.model.bo.AftersalesBO;
import com.ff.order.service.AftersalesService;
import com.ff.shop.dao.GoodsFavoritesMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 *
 */
@Service
public class AftersalesServiceImpl extends BaseServiceImpl<Aftersales> implements AftersalesService {

    @Autowired
    AftersalesMapper aftersalesMapper;
    @Override
    public Page<AftersalesBO> selectByPage(Page<AftersalesBO> page) {
        List<AftersalesBO> list= aftersalesMapper.selectByPage(page);
        page.setRecords(list);
        return page;
    }
}
