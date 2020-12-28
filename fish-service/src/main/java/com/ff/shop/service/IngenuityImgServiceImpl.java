package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.IngenuityImgMapper;
import com.ff.shop.model.IngenuityImg;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class IngenuityImgServiceImpl extends BaseServiceImpl<IngenuityImg> implements IngenuityImgService {


    @Autowired
    IngenuityImgMapper mapper;

    @Override
    public Integer deleteById(Integer id) {
        EntityWrapper<IngenuityImg> ew=new EntityWrapper();
        ew.eq("reservation_id",id);
        return mapper.delete(ew);
    }
}
