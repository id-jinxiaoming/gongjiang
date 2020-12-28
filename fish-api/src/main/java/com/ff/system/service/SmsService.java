package com.ff.system.service;


import com.ff.common.base.BaseService;
import com.ff.system.model.Dict;
import com.ff.system.model.Sms;

public interface SmsService extends BaseService<Sms> {

    Sms findBysmsTemplateId(String code);
	
}
