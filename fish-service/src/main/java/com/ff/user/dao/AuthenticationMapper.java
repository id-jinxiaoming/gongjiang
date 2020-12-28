package com.ff.user.dao;

import com.ff.common.base.BaseMapper;
import com.ff.user.model.Authentication;
import org.apache.ibatis.annotations.Delete;

public interface AuthenticationMapper extends BaseMapper<Authentication> {
    @Delete("TRUNCATE TABLE tb_authentication")
    int removalAllData();
}
