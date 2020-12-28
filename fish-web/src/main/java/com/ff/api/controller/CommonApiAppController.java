package com.ff.api.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.model.QiniuConfig;
import com.ff.common.model.ResponseData;
import com.ff.common.model.SystemConfig;
import com.ff.common.oss.CloudStorageConfig;
import com.ff.common.oss.OSSFactory;
import com.ff.common.util.JsonUtils;
import com.ff.common.util.UploadUtil;
import com.ff.system.model.Setting;
import com.ff.system.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Api(value="公共接口",description="公共接口")
@Controller
@RequestMapping(value="/api")
public class CommonApiAppController {
    @Reference
    SettingService settingService;

    @ApiOperation(value="获取管家信息",notes="获取管家信息")
    @RequestMapping(value="/getServiceInfo",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getServiceInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session
    ){
        Setting setting = settingService.findOneByKey("system");
        //将json字符串转为对象
        SystemConfig config= JsonUtils.jsonToPojo(setting.getV(),SystemConfig.class);
        ResponseData result = new ResponseData();
        result.setState(200);
        result.setDatas(config.getServiceInfo());
        result.setMessage("");
        return result;
    }
    @ApiOperation(value="上传图片",notes="上传图片")
    @RequestMapping(value="/common/upload",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(value = "*",maxAge = 3600)
    public ResponseData upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        ResponseData result = new ResponseData();
        // 获取文件
        MultipartFile file = multipartRequest.getFile("file");
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
            result.setState(200);
            result.setDatas("http://" + request.getServerName()+ ":"+ request.getServerPort()+ "/"+ request.getContextPath()  +"/uploads/"+fileName);
            return result;

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
            result.setState(200);
            result.setMessage("上传成功");
            result.setDatas(url);
            return result;
        }
        result.setState(500);
        result.setMessage("上传失败");
        return result;


    }
}
