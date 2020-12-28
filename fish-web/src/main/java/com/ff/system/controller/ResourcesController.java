package com.ff.system.controller;





import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.model.ResourcesTree;
import com.ff.system.model.Dict;
import com.ff.system.model.Resources;
import com.ff.system.service.ResourcesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.ff.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/system/resources")
public class ResourcesController extends BaseController {
    @Reference
    private ResourcesService resourcesService;
    @RequiresPermissions(value = "system:resources:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list( ) {

        return "system/resources/list";

    }


    @RequiresPermissions(value = "system:resources:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(HttpServletRequest request) {

        List<ResourcesTree> data= resourcesService.selectResourcesTree();



        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("system:resources:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        resourcesService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }


    @RequiresPermissions("system:resources:add")
    @RequestMapping(value="/add/{id}",method=RequestMethod.GET)
    public ModelAndView add(@PathVariable("id")String id,ModelMap resultMap){
        if(id.equals("0"))
        {
            id="";
        }
        resultMap.put("id",id);
        return new ModelAndView("/system/resources/add",resultMap);
    }


    @RequiresPermissions("system:resources:add")
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doAdd(Resources resources){
        Map<String, Object> resultMap = new LinkedHashMap();
        if(resourcesService.insert(resources)!=0){

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

    @RequiresPermissions("system:resources:edit")
    @RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
        Resources resources = resourcesService.findByPrimaryKey(id);
        map.put("item", resources);
        return new ModelAndView("/system/resources/edit",map);

    }
    @RequiresPermissions("system:resources:edit")
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doEdit(Resources model){
        Map<String, Object> resultMap = new LinkedHashMap();
        if(resourcesService.updateByPrimaryKey(model)!=0){

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
