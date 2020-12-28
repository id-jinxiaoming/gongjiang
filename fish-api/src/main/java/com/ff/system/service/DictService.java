package com.ff.system.service;


import com.ff.common.base.BaseService;
import com.ff.system.model.Dict;

import java.util.List;

public interface DictService extends BaseService<Dict> {
	List<Dict> selectByCode(String code);
}
