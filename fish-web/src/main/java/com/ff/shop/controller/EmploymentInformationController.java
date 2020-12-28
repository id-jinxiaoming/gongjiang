package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.DateUtils;
import com.ff.shop.model.EmploymentInformation;
import com.ff.shop.service.EmploymentInformationService;
import com.ff.shop.service.ProvinceService;
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
@RequestMapping("/employment/information")
public class EmploymentInformationController extends BaseController {

    @Reference
    private EmploymentInformationService employmentInformationService;

    @Reference
    private ProvinceService provinceService;

    @RequiresPermissions(value = "employment:information:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(String key, ModelMap map) {
        key= StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
           //e.printStackTrace();
        }
        map.put("key", key);
        return new ModelAndView("employment/information/list",map);
    }


    @RequiresPermissions(value = "employment:information:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(String key, HttpServletRequest request) {
        Page<EmploymentInformation> page = getPage(request);
        EntityWrapper<EmploymentInformation> ew=new EntityWrapper();
        ew.like("project_name","%"+key+"%");
        ew.orderBy("id",false);
        Page<EmploymentInformation> data= employmentInformationService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("employment:information:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        employmentInformationService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }

    @RequiresPermissions("employment:information:add")
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public ModelAndView add(ModelMap map){
        map.put("brand", provinceService.selectAll());
        return new ModelAndView("/employment/information/add",map);
    }
    @RequiresPermissions("employment:information:add")
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doAdd(EmploymentInformation model,HttpServletRequest request) {
        Map<String, Object> resultMap = new LinkedHashMap();
        model.setCreateTime(DateUtils.getDate());
        Integer ids = employmentInformationService.insert(model);
        if(ids>0){
            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        }else{
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }

    @RequiresPermissions("employment:information:edit")
    @RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id")Integer id, ModelMap map){
        map.put("brand", provinceService.selectAll());
        map.put("goodsItem",employmentInformationService.findByPrimaryKey(id.toString()));
        return new ModelAndView("/employment/information/edit",map);
    }
    @RequiresPermissions("employment:information:edit")
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doEdit(EmploymentInformation model,HttpServletRequest request){
        Map<String, Object> resultMap = new LinkedHashMap();
        Integer id=employmentInformationService.updateByPrimaryKey(model);
        if(id!=0){
            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        }else{
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }
}
