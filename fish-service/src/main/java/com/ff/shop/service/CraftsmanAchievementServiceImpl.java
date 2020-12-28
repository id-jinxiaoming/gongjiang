package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.dao.CraftsmanAchievementMapper;
import com.ff.shop.model.CraftsmanAchievement;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CraftsmanAchievementServiceImpl extends BaseServiceImpl<CraftsmanAchievement> implements CraftsmanAchievementService {

    @Autowired
    CraftsmanAchievementMapper mapper;

    @Override
    public int insert(CraftsmanAchievement t) {
        mapper.insert(t);
        Integer id=t.getId();
        return id;
    }
}
