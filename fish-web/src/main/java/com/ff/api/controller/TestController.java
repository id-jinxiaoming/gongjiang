package com.ff.api.controller;


import com.ff.common.model.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Api(value="测试接口",description="This is Test!")
@Controller
@RequestMapping(value="/api/test")
public class TestController{


    @ApiOperation(value="设置数据",notes="获取所有数据")
    @RequestMapping(value="/setCookies",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData setCookies(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        session.setAttribute("sess","ok");

        ResponseData result = new ResponseData();
        result.setState(200);
        result.setDatas(null);
        result.setMessage("获取数据成功！");
        return result;
    }


    @ApiOperation(value="获取数据",notes="获取所有数据")
    @RequestMapping(value="/getCookies",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getCookies(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String a=session.getAttribute("sess").toString();
        System.out.println("name:"+a);

        ResponseData result = new ResponseData();
        result.setState(200);
        result.setDatas(null);
        result.setMessage("获取数据成功！");
        return result;
    }


    @ApiOperation(value="设置数据",notes="获取所有数据")
    @RequestMapping(value="/deleteCookies",method= RequestMethod.DELETE)
    @ResponseBody
    public ResponseData deleteCookies(String id){

        ResponseData result = new ResponseData();
        result.setState(200);
        result.setDatas(null);
        result.setMessage("获取数据成功！");
        return result;
    }

    @ApiOperation(value="设置数据",notes="获取所有数据")
    @RequestMapping(value="/putCookies",method= RequestMethod.PUT)
    @ResponseBody
    public ResponseData putCookies(String id){

        ResponseData result = new ResponseData();
        result.setState(200);
        result.setDatas(null);
        result.setMessage("获取数据成功！");
        return result;
    }


}
