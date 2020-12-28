package com.ff.shop.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.common.base.BaseServiceImpl;
import com.ff.shop.model.AccessToken;

@Service
public class WxTokenServiceImpl extends BaseServiceImpl<AccessToken> implements WxTokenService {
}
