package com.ff.system.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseService;
import com.ff.system.model.Log;

import java.util.Map;

public interface LogService extends BaseService<Log> {
    Page<Map<String, Object>> selectPageByAccount(Page<Map<String, Object>> page, String account);
}
