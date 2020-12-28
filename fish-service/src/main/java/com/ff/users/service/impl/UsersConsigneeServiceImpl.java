package com.ff.users.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersConsigneeMapper;
import com.ff.users.model.UsersConsignee;
import com.ff.users.service.UsersConsigneeService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UsersConsigneeServiceImpl extends BaseServiceImpl<UsersConsignee> implements UsersConsigneeService {

	@Autowired
    protected UsersConsigneeMapper mapper;


    @Override
    public Integer setDefault(Integer id) {
        UsersConsignee map=new UsersConsignee();
        map.setIsDefault(0);
        mapper.update(map,null);
        map.setIsDefault(1);
        map.setId(id);

        return mapper.updateById(map);
    }
}
