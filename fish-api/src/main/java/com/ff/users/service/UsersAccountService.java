package com.ff.users.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.users.model.UsersAccount;
import com.ff.users.model.UsersAccountLog;

import java.util.Map;

public interface UsersAccountService extends BaseService<UsersAccount> {

    boolean updateAccount(UsersAccountLog log);

    Page<Map<String, Object>> selectPageByUsersName(Page<Map<String, Object>> page, String username);


}
