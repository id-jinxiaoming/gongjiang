package com.ff.system.service;


import com.ff.common.base.BaseService;
import com.ff.system.model.Role;
import com.ff.system.model.RoleResources;
import com.ff.system.model.Setting;

import java.util.List;

public interface SettingService extends BaseService<Setting> {


    Setting findOneByKey(String key);
	

}
