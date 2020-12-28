package com.ff.users.service;


import com.ff.common.base.BaseService;
import com.ff.users.model.Users;

public interface UsersService extends BaseService<Users> {
    void testJMS();

    Users findUserByToken(String token);

}
