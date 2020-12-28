package com.ff.system.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.common.model.JPushConfig;
import com.ff.common.model.QiniuConfig;
import com.ff.common.model.SmsConfig;
import com.ff.common.model.SystemConfig;
import com.ff.common.util.JsonUtils;
import com.ff.system.model.Setting;
import com.ff.system.service.SettingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-07-14 9:49
 **/
@Controller
@RequestMapping("/system/setting")
public class SettingController extends BaseController{
    @Reference
    private SettingService settingService;

    @RequiresPermissions("system:setting:system")
    @RequestMapping(value="/system",method=RequestMethod.GET)
    public ModelAndView edit(ModelMap map){
        Setting setting = settingService.findOneByKey("system");
        //将json字符串转为对象
        SystemConfig config= JsonUtils.jsonToPojo(setting.getV(),SystemConfig.class);
        map.put("active", "system:setting:system");
        map.put("config", config);
        map.put("setting", setting);
        return new ModelAndView("/system/setting/system",map);

    }

    @RequiresPermissions("system:setting:system")
    @RequestMapping(value="/systemEdit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> systemEdit(Setting setting, SystemConfig config){
        Map<String, Object> resultMap = new LinkedHashMap();
        setting.setK("system");
        setting.setV(JsonUtils.objectToJson(config));
        if(settingService.updateByPrimaryKey(setting)!=0){

            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        }
        else
        {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }





    @RequiresPermissions("system:setting:sms")
    @RequestMapping(value="/sms",method=RequestMethod.GET)
    public ModelAndView sms(ModelMap map){
        Setting setting = settingService.findOneByKey("sms");
        //将json字符串转为对象
        SmsConfig smsConfig=JsonUtils.jsonToPojo(setting.getV(),SmsConfig.class);
        map.put("active", "system:setting:sms");
        map.put("config", smsConfig);
        map.put("setting", setting);
        return new ModelAndView("/system/setting/sms",map);

    }

    @RequiresPermissions("system:setting:sms")
    @RequestMapping(value="/smsEdit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> smsEdit(Setting setting,SmsConfig smsConfig){
        setting.setK("sms");
        setting.setV(JsonUtils.objectToJson(smsConfig));
        Map<String, Object> resultMap = new LinkedHashMap();
        if(settingService.updateByPrimaryKey(setting)!=0){

            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        }
        else
        {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }



    @RequiresPermissions("system:setting:qiniu")
    @RequestMapping(value="/qiniu",method=RequestMethod.GET)
    public ModelAndView qiniu(ModelMap map){
        Setting setting = settingService.findOneByKey("qiniu");
        //将json字符串转为对象
        QiniuConfig config=JsonUtils.jsonToPojo(setting.getV(),QiniuConfig.class);
        map.put("config", config);
        map.put("active", "system:setting:qiniu");
        map.put("setting", setting);
        return new ModelAndView("/system/setting/qiniu",map);

    }

    @RequiresPermissions("system:setting:qiniu")
    @RequestMapping(value="/qiniuEdit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> qiniuEdit(Setting setting,QiniuConfig config){
        setting.setK("qiniu");
        setting.setV(JsonUtils.objectToJson(config));
        Map<String, Object> resultMap = new LinkedHashMap();
        if(settingService.updateByPrimaryKey(setting)!=0){

            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        }
        else
        {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }

    @RequiresPermissions("system:setting:jpush")
    @RequestMapping(value="/jpush",method=RequestMethod.GET)
    public ModelAndView jpush(ModelMap map){
        Setting setting = settingService.findOneByKey("JPush");
        //将json字符串转为对象
        JPushConfig config=JsonUtils.jsonToPojo(setting.getV(),JPushConfig.class);
        map.put("config", config);
        map.put("active", "system:setting:jpush");
        map.put("setting", setting);
        return new ModelAndView("/system/setting/jpush",map);

    }

    @RequiresPermissions("system:setting:jpush")
    @RequestMapping(value="/jpush",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> jpushEdit(Setting setting,JPushConfig config){
        setting.setK("JPush");
        setting.setV(JsonUtils.objectToJson(config));
        Map<String, Object> resultMap = new LinkedHashMap();
        if(settingService.updateByPrimaryKey(setting)!=0){

            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        }
        else
        {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }
}
