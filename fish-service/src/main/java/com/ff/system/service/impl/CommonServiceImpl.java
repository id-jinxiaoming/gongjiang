package com.ff.system.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ff.common.model.JPushConfig;
import com.ff.common.model.SmsConfig;
import com.ff.common.util.JPush;
import com.ff.common.util.JsonUtils;
import com.ff.system.model.Setting;
import com.ff.system.model.Sms;
import com.ff.system.service.CommonService;
import com.ff.system.service.SettingService;
import com.ff.system.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@Service
public class CommonServiceImpl implements CommonService{

    @Autowired
    SettingService settingService;
    @Autowired
    SmsService smsService;
    @Override
    public Boolean sendSMS(String code, String mobile, String json) {
        Setting setting=settingService.findOneByKey("sms");
        Sms sms=smsService.findBysmsTemplateId(code);

        //将json字符串转为对象
        SmsConfig smsConfig = JsonUtils.jsonToPojo(setting.getV(), SmsConfig.class);
        String appkey = null;
        String secret = null;
        String signName = null;
        String templateCode = null;
        if (smsConfig != null) {
            appkey = smsConfig.getKey();
            secret = smsConfig.getSecret();
            signName = smsConfig.getSignName();
        }
        if (sms != null) {
            templateCode = sms.getSmsTemplateId();
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = appkey;//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = secret;//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);

        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(json);
        //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return true;
            } else {

                return false;
            }
        } catch (ClientException e) {

            return false;
        }
    }

    public Boolean sendPushByRegistrationId(String registrationId,String title,String sendMessage,Map<String, String> map){
        Setting setting=settingService.findOneByKey("JPush");
        JPushConfig jPushConfig= JsonUtils.jsonToPojo(setting.getV(),JPushConfig.class);
        JPush.initJpush(jPushConfig.getAppKey(),jPushConfig.getMasterSecret());

        return JPush.sendPushRegistrationId(registrationId,title,sendMessage,map);

    }

    public Boolean sendPushByAlias(String alias,String title,String sendMessage,Map<String, String> map){
        Setting setting=settingService.findOneByKey("JPush");
        JPushConfig jPushConfig= JsonUtils.jsonToPojo(setting.getV(),JPushConfig.class);
        JPush.initJpush(jPushConfig.getAppKey(),jPushConfig.getMasterSecret());

        return JPush.sendPushAlias(alias,title,sendMessage,map);

    }



}
