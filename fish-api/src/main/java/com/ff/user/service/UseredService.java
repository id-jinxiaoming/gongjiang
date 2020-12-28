package com.ff.user.service;

import com.ff.common.base.BaseService;
import com.ff.user.model.Usered;

public interface UseredService extends BaseService<Usered> {

    Usered findUserByToken(String token);

    boolean updateByUserIds(Integer userId);
}
