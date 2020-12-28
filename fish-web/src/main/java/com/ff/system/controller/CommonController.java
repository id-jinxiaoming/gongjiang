package com.ff.system.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ff.common.base.BaseController;
import com.ff.common.model.QiniuConfig;
import com.ff.common.model.SystemConfig;
import com.ff.common.model.ZTree;
import com.ff.common.oss.CloudStorageConfig;
import com.ff.common.oss.CloudStorageService;
import com.ff.common.oss.OSSFactory;
import com.ff.common.util.JsonUtils;
import com.ff.common.util.UploadUtil;
import com.ff.common.util.UtilPath;
import com.ff.system.model.Role;
import com.ff.system.model.Setting;
import com.ff.system.service.CommonService;
import com.ff.system.service.RoleService;
import com.ff.system.service.SettingService;
import com.ff.system.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Yzx on 2017/5/16.
 */
@Controller
public class CommonController extends BaseController {
    @Reference
    SettingService settingService;
    @Reference
    SmsService smsService;
    @Reference
    CommonService commonService;

    //上传图片 本地
    @RequestMapping(value="/common/upload")
    @ResponseBody
    public String uploads(MultipartFile file, HttpServletRequest request) {

        String path = request.getSession().getServletContext().getRealPath("uploads");

        String fileName = UploadUtil.uploadFile(file,path);
        return "http://" + request.getServerName()+ ":"+ request.getServerPort()+ "/"+ request.getContextPath()+"/uploads/"+fileName;


    }
    //发送短信测试
    @RequestMapping(value="/common/sms/{mobile}")
    @ResponseBody
    public String sms(@PathVariable("mobile")String mobile) {
        int a = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;// 产生1000-9999的随机数
        String json="{\"code\":\""+a+"\"}";

        if(!UtilPath.isMobile(mobile)){//验证手机号码格式是否正确

            return "手机号码格式不正确！";
        }
        commonService.sendSMS("sms_code", mobile, json);//调运发送短信的方法
        return "发送成功";
    }



    //上传图片 OSS
    @RequestMapping(value="/common/uploadOss",method = RequestMethod.POST)
    @ResponseBody
    public String uploadOss(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获取文件
        MultipartFile file = multipartRequest.getFile("upfile");
        Setting setting = settingService.findOneByKey("system");
        //将json字符串转为对象
        SystemConfig config= JsonUtils.jsonToPojo(setting.getV(),SystemConfig.class);
        //将json字符串转为对象

        CloudStorageConfig cfg=new CloudStorageConfig();
       //本地
        if(config.getUpload()==0)
        {
            String path = request.getSession().getServletContext().getRealPath("uploads");

            String fileName = UploadUtil.uploadFile(file,path);
            /*
            String strBackUrl = "http://" + request.getServerName() //服务器地址
                    + ":"
                    + request.getServerPort()           //端口号
                    + httpRequest.getContextPath()      //项目名称
                    + httpRequest.getServletPath()      //请求页面或其他地址
                    + "?" + (httpRequest.getQueryString()); //参数
            */
            return "http://" + request.getServerName()+ ":"+ request.getServerPort()+ "/"+ request.getContextPath()  +"/uploads/"+fileName;

        }
        else if(config.getUpload()==1) //七牛
        {
            Setting qiniusStting = settingService.findOneByKey("qiniu");
            //将json字符串转为对象
            QiniuConfig qiniuConfig=JsonUtils.jsonToPojo(qiniusStting.getV(),QiniuConfig.class);


            cfg.setType(1);
            cfg.setQiniuAccessKey(qiniuConfig.getAccesskey());
            cfg.setQiniuSecretKey(qiniuConfig.getSecretkey());
            cfg.setQiniuBucketName(qiniuConfig.getBucketname());
            cfg.setQiniuDomain(qiniuConfig.getDomain());

            OSSFactory.cloudStorageConfig=cfg;


            String url = OSSFactory.build().upload(file.getBytes());
            return url;
        }
        return "";


    }


    //上传图片
    @RequestMapping(value="/common/editUpload",method = RequestMethod.POST)
    @ResponseBody
    public Object editUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获取文件
        MultipartFile file = multipartRequest.getFile("imgFile");
        Setting setting = settingService.findOneByKey("system");
        //将json字符串转为对象
        SystemConfig config= JsonUtils.jsonToPojo(setting.getV(),SystemConfig.class);
        //将json字符串转为对象

        CloudStorageConfig cfg=new CloudStorageConfig();
        //本地
        if(config.getUpload()==0)
        {
            String path = request.getSession().getServletContext().getRealPath("uploads");

            String fileName = UploadUtil.uploadFile(file,path);
            /*
            String strBackUrl = "http://" + request.getServerName() //服务器地址
                    + ":"
                    + request.getServerPort()           //端口号
                    + httpRequest.getContextPath()      //项目名称
                    + httpRequest.getServletPath()      //请求页面或其他地址
                    + "?" + (httpRequest.getQueryString()); //参数
            */
            Map<String, Object> resultMap = new LinkedHashMap();
            resultMap.put("error",0);
            resultMap.put("url","http://" + request.getServerName()+ ":"+ request.getServerPort()+ "/"+ request.getContextPath()  +"/uploads/"+fileName);
            return resultMap;

        }
        else if(config.getUpload()==1) //七牛
        {
            Setting qiniusStting = settingService.findOneByKey("qiniu");
            //将json字符串转为对象
            QiniuConfig qiniuConfig=JsonUtils.jsonToPojo(qiniusStting.getV(),QiniuConfig.class);


            cfg.setType(1);
            cfg.setQiniuAccessKey(qiniuConfig.getAccesskey());
            cfg.setQiniuSecretKey(qiniuConfig.getSecretkey());
            cfg.setQiniuBucketName(qiniuConfig.getBucketname());
            cfg.setQiniuDomain(qiniuConfig.getDomain());

            OSSFactory.cloudStorageConfig=cfg;


            String url = OSSFactory.build().upload(file.getBytes());
            Map<String, Object> resultMap = new LinkedHashMap();
            resultMap.put("error",0);
            resultMap.put("url",url);
            return resultMap;
        }
        return null;




    }

    // 选择图标库
    @RequestMapping(value = "/common/icon", method = RequestMethod.GET)
    public ModelAndView icon() {

        return new ModelAndView("/common/icon");
    }


    // 发送推送
    @RequestMapping(value="/common/sendPushById",method = RequestMethod.POST)
    @ResponseBody
    public Object sendPushById(String id) {
        Map<String,String> obj=new HashMap<>();
        obj.put("type","fast");
        commonService.sendPushByRegistrationId(id, "测试", "测试数据", obj);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("status",200);
        return resultMap;
    }


    //下载APP
    @RequestMapping(value="/download")
    public ModelAndView download() {

        Setting setting = settingService.findOneByKey("system");
        //将json字符串转为对象
        SystemConfig config= JsonUtils.jsonToPojo(setting.getV(),SystemConfig.class);
        //将json字符串转为对象
        Map<String,Object> map=new HashMap<>();
        map.put("android",config.getAndroid());
        map.put("ios",config.getIos());
        return new ModelAndView("download",map);


    }



}
