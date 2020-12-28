package com.ff.system.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.system.model.User;
import com.ff.system.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Yzx on 2017/5/16.
 */
@Controller
public class IndexController extends BaseController {
    @Reference
    UserService userService;
    @RequestMapping(value = "/main")
    public String main() {

        return "main";
    }

    //修改密码
    @RequestMapping(value="/index/pwd",method = RequestMethod.GET)
    public String pwd() {
        return "pwd";
    }

    @RequestMapping(value="/index/doPwd",method =RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doPwd(String old_password, String password) {
        Subject subject = SecurityUtils.getSubject();
        User user=(User) subject.getPrincipal();
        Map<String, Object> resultMap = new LinkedHashMap();
        if(!user.getPassword().equals(DigestUtils.md5Hex(old_password)))
        {
            resultMap.put("status", 300);
            resultMap.put("message", "旧密码不正确！");
        }
        else
        {
            user.setPassword(DigestUtils.md5Hex(password));
            userService.updateByPrimaryKey(user);
            resultMap.put("status", 200);
            resultMap.put("message", "修改成功!");

            subject.logout();
        }
        return resultMap;
    }

    //修改头像
    @RequestMapping(value="/index/headImg",method =RequestMethod.GET)
    public String headImg() {
        return "headImg";
    }

    @RequestMapping(value="/index/doHeadImg",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doHeadImg(User model){
        Subject subject = SecurityUtils.getSubject();
        User user=(User) subject.getPrincipal();
        Map<String, Object> resultMap = new LinkedHashMap();
        user.setImgUrl(model.getImgUrl());
        userService.updateByPrimaryKey(user);
        resultMap.put("status", 200);
        resultMap.put("message", "修改成功!");

        return resultMap;
    }





}
