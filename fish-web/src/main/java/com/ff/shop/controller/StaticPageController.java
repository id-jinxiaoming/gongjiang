package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.shop.model.StaticPage;
import com.ff.shop.service.StaticPageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RequestMapping("/static/page")
@Controller
public class StaticPageController extends BaseController {

    @Reference
    private StaticPageService staticPageService;

    @RequiresPermissions(value = "static:page:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelMap map) {


        return new ModelAndView("static/page/list",map);

    }


    @RequiresPermissions(value = "static:page:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(HttpServletRequest request) {
        Page<StaticPage> page = getPage(request);
        EntityWrapper<StaticPage> ew=new EntityWrapper();
        ew.orderBy("id",false);
        Page<StaticPage> data= staticPageService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("static:page:edit")
    @RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id")Integer id, ModelMap map){

        map.put("staticPage",staticPageService.findByPrimaryKey(id.toString()));

        return new ModelAndView("/static/page/edit",map);
    }
    @RequiresPermissions("static:page:edit")
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doEdit(StaticPage model,HttpServletRequest request){
        Map<String, Object> resultMap = new LinkedHashMap();

        Integer id=staticPageService.updateByPrimaryKey(model);
        if(id!=0){
            resultMap.put("status", 200);
            resultMap.put("message", "修改成功");
        }
        else
        {
            resultMap.put("status", 500);
            resultMap.put("message", "修改失败");
        }
        return resultMap;
    }
}
