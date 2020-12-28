package com.ff.users.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.users.dao.UsersAccountLogMapper;
import com.ff.users.dao.UsersAccountMapper;
import com.ff.users.model.UsersAccount;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.service.UsersAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Map;


@Service("usersAccountService")
@Transactional(readOnly=true)
public class UsersAccountServiceImpl extends BaseServiceImpl<UsersAccount> implements UsersAccountService {

	@Autowired
    protected UsersAccountMapper mapper;


    @Autowired
    protected UsersAccountLogMapper usersAccountLogMapper;

    //事务处理
    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean updateAccount(UsersAccountLog log){

        UsersAccount account=mapper.selectById(log.getUserId());
        if(account==null)
        {
            return false;
        }



        account.setBalance( account.getBalance().add(log.getBalance()));
        account.setPoints( account.getPoints()+log.getPoints());
        account.setExp( account.getExp()+log.getExp());

        try {
            mapper.updateById(account);
            usersAccountLogMapper.insert(log);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;

        }
    }

    @Override
    public Page<Map<String, Object>> selectPageByUsersName(Page<Map<String, Object>> page, String username) {
        page.setRecords(usersAccountLogMapper.selectPageByUserName(page, username));
        return page;
    }


}
