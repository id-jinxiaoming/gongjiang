package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.CraftsmanAchievementImgMapper;
import com.ff.shop.model.CraftsmanAchievementImg;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CraftsmanAchievementImgServiceImpl extends BaseServiceImpl<CraftsmanAchievementImg> implements CraftsmanAchievementImgService {


    @Autowired
    CraftsmanAchievementImgMapper mapper;

    @Override
    public Integer deleteById(Integer id) {
        EntityWrapper<CraftsmanAchievementImg> ew=new EntityWrapper<>();
        ew.eq("achievement_id",id);
        return mapper.delete(ew);
    }
}
