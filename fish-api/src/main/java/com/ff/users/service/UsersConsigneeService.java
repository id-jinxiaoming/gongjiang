package com.ff.users.service;


import com.ff.common.base.BaseService;
import com.ff.users.model.UsersConsignee;

public interface UsersConsigneeService extends BaseService<UsersConsignee> {

    Integer setDefault(Integer id);

}
