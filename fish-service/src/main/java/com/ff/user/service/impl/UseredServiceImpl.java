package com.ff.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.user.dao.UseredExperienceMapper;
import com.ff.user.dao.UseredLabelMapper;
import com.ff.user.dao.UseredMapper;
import com.ff.user.dao.UseredWorkTypeMapper;
import com.ff.user.model.Usered;
import com.ff.user.service.UseredService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


@Service
public class UseredServiceImpl extends BaseServiceImpl<Usered> implements UseredService {

    @Autowired
    protected UseredMapper mapper;

    @Autowired
    protected UseredLabelMapper useredLabelMapper;

    @Autowired
    protected UseredExperienceMapper useredExperienceMapper;

    @Autowired
    protected UseredWorkTypeMapper useredWorkTypeMapper;

    @Override
    public int insert(Usered t) {
        mapper.insert(t);
        Integer id=t.getId();
        return id;
    }


    @Override
    public Usered findUserByToken(String token) {
        if(token==null||token.equals("")){
            return  null;
        }
        Usered map=new Usered();
        map.setToken(token);
        return  mapper.selectOne(map);
    }

    @Override
    public boolean updateByUserIds(Integer userId){

        Map<String,Object> map=new HashMap<>();
        map.put("user_id",userId);
        useredLabelMapper.deleteByMap(map);

        Map<String,Object> map1=new HashMap<>();
        map1.put("user_id",userId);
        useredExperienceMapper.deleteByMap(map1);

        Map<String,Object> map2=new HashMap<>();
        map2.put("user_id",userId);
        useredWorkTypeMapper.deleteByMap(map2);

        return true;

    }
}
