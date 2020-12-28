package com.ff.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ff.system.dao.UserMapper;
import com.ff.system.dao.UserRoleMapper;
import com.ff.system.model.User;
import com.ff.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public List<User> selectAll() {
        return userMapper.selectList(null);
    }

    @Override
    public User findByPrimaryKey(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public int insert(User t) {
        String id = UUID.randomUUID().toString();
        Field field;
        try {
            field = t.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(t, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return 1;
        return userMapper.insert(t);
    }

    @Override
    public int updateByPrimaryKey(User t) {
        return userMapper.updateById(t);
    }

    @Override
    public int deleteByPrimaryKey(String[] id) {
        List<String> ids =new ArrayList<String>();
        for (String string : id) {
            ids.add(string);
        }
        return userMapper.deleteBatchIds(ids);
    }

    @Override
    public com.baomidou.mybatisplus.plugins.Page<User> selectPage(com.baomidou.mybatisplus.plugins.Page<User> page, EntityWrapper<User> ew) {
        // TODO Auto-generated method stub
        if (null != ew) {
            ew.orderBy(page.getOrderByField(), page.isAsc());
        }
        page.setRecords(userMapper.selectPage(page, ew));
        return page;
    }

    @Override
    public List<User> selectByT(User user) {
        EntityWrapper< User> ew = new EntityWrapper<User>();
        return userMapper.selectList(ew);
    }

    @Override
    public User findOne(User t) {
        return userMapper.selectOne(t);
    }
}
