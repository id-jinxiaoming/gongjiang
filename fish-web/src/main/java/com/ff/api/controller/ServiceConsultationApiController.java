package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.MD5Utils;
import com.ff.common.util.StringUtils;
import com.ff.user.model.ServiceConsultation;
import com.ff.user.model.Usered;
import com.ff.user.service.ServiceConsultationService;
import com.ff.user.service.UseredService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/serviceConsultation")
public class ServiceConsultationApiController extends BaseController {

    @Reference
    private ServiceConsultationService serviceConsultationService;

    @Reference
    private UseredService useredService;


    @Autowired
    private RedisTemplate redisTemplate;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="判断用户是否登入",notes="判断用户是否登入")
    @RequestMapping(value="/judgeUserIsLogin",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData judgeUserIsLogin(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token
    ){
        ResponseData result = new ResponseData();

        Map map = new HashMap();
        if(StringUtils.isEmpty(token)){
            map.put("name","");
            map.put("telephone","");
            map.put("status",1);
            result.setState(500);
            result.setMessage("参数为空");
            result.setDatas(map);//没登入
            return result;
        }
        Usered usered = useredService.findUserByToken(token);

        map.put("name",usered.getRealName());
        map.put("telephone",usered.getUsername());
        map.put("status",2);
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="咨询服务添加",notes="咨询服务添加")
    @RequestMapping(value="/addServiceConsultation",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData addServiceConsultation(
            @ApiParam(required = true,name = "status",value = "status")@RequestParam(required = false,value = "status")Integer stauts,
            @ApiParam(required = true,name = "code",value = "code")@RequestParam(required = false,value = "code")String code,
            @ApiParam(required = true,name = "describe",value = "describe")@RequestParam(required = false,value = "describe")String describe ,
            @ApiParam(required = true,name = "img",value = "img")@RequestParam(required = false,value = "img")String img,
            @ApiParam(required = true,name = "contacts",value = "contacts")@RequestParam(required = false,value = "contacts")String contacts,
            @ApiParam(required = true,name = "contactNumber",value = "contactNumber")@RequestParam(required = false,value = "contactNumber")String contactNumber
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(stauts.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(stauts==1){
            ServiceConsultation serviceConsultation1 = new ServiceConsultation();
            serviceConsultation1.setDescribe(describe);
            serviceConsultation1.setImg(img);
            serviceConsultation1.setContacts(contacts);
            serviceConsultation1.setContactNumber(contactNumber);
            Integer ids =serviceConsultationService.insert(serviceConsultation1);
            if(ids>0){
                result.setState(200);
                result.setMessage("成功");
                return result;
            }else{
                result.setState(400);
                result.setMessage("失败");
                return result;
            }
        }else{
            String checkCode=redisTemplate.opsForValue().get("smsCode:"+contactNumber).toString();
            if(!checkCode.equals(code)){
                result.setState(500);
                result.setMessage("验证码不正确");
                return result;
            }
            ServiceConsultation serviceConsultation1= new ServiceConsultation();
            serviceConsultation1.setDescribe(describe);
            serviceConsultation1.setImg(img);
            serviceConsultation1.setContacts(contacts);
            serviceConsultation1.setContactNumber(contactNumber);
            Integer ids =serviceConsultationService.insert(serviceConsultation1);
            Usered usered =new Usered();
            usered.setUsername(contactNumber);
            Usered model= useredService.findOne(usered);
            if(model!=null && ids>0){
                result.setState(200);
                result.setMessage("成功");
                return result;
            }else if (model==null && ids>0){
                usered.setUsername(contactNumber);
                usered.setCreateTime(new Date());
                useredService.insert(usered);
                result.setState(200);
                result.setMessage("成功");
                return result;
            }else{
                result.setState(400);
                result.setMessage("失败");
                return result;
            }
        }
    }
}
