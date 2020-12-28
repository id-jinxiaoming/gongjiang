package com.ff.ad.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.ad.model.RotationChart;
import com.ff.ad.service.RotationChartService;
import com.ff.common.base.BaseController;
import org.apache.commons.lang.StringEscapeUtils;
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

@Controller
@RequestMapping("/rotation/chart")
public class RotationChartController extends BaseController {


    @Reference
    private RotationChartService rotationChartService;

    @RequiresPermissions(value = "rotation:chart:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(String key, ModelMap map) {


        key= StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("key", key);
        return new ModelAndView("rotation/chart/list",map);

    }


    @RequiresPermissions(value = "rotation:chart:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(HttpServletRequest request, String key) {

        Page<RotationChart> page = getPage(request);
        EntityWrapper<RotationChart> ew=new EntityWrapper();
        ew.like("name","%"+key+"%");
        Page<RotationChart> data= rotationChartService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("rotation:chart:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        rotationChartService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }

    @RequiresPermissions("rotation:chart:add")
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(){

        return "/rotation/chart/add";
    }
    @RequiresPermissions("rotation:chart:add")
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doAdd(RotationChart model){
        Map<String, Object> resultMap = new LinkedHashMap();
        if(rotationChartService.insert(model)!=0){

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

    @RequiresPermissions("rotation:chart:edit")
    @RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
        RotationChart model = rotationChartService.findByPrimaryKey(id);
        map.put("item", model);
        return new ModelAndView("/rotation/chart/edit",map);

    }
    @RequiresPermissions("rotation:chart:edit")
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doEdit(RotationChart model){
        Map<String, Object> resultMap = new LinkedHashMap();
        if(rotationChartService.updateByPrimaryKey(model)!=0){

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
