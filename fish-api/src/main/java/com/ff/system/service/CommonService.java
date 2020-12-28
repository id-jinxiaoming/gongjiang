package com.ff.system.service;



import java.util.Map;

public interface CommonService {

    Boolean sendSMS(String code,String mobile,String json);
    Boolean sendPushByRegistrationId(String registrationId,String title,String sendMessage,Map<String, String> map);
    Boolean sendPushByAlias(String alias,String title,String sendMessage,Map<String, String> map);


}
