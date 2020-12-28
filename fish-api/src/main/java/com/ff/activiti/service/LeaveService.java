package com.ff.activiti.service;

import com.ff.activiti.model.Leave;
import com.ff.common.base.BaseService;
import com.ff.system.model.User;




public interface LeaveService extends BaseService<Leave>{
	void save(Leave leave,User user) ;
}
