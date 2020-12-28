package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.shop.model.IngenuityUsered;
import com.ff.shop.model.IngenuityWorks;
import com.ff.shop.service.IngenuityUseredService;
import com.ff.shop.service.IngenuityWorksService;
import com.ff.user.model.Usered;
import com.ff.user.service.UseredService;
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
@RequestMapping("/ingenuity/usered")
public class IngenuityUseredController extends BaseController {

    @Reference
    private IngenuityWorksService ingenuityWorksService;

    @Reference
    private IngenuityUseredService ingenuityUseredService;

    @Reference
    private UseredService useredService;

    @RequiresPermissions(value = "ingenuity:usered:list")
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("id")Integer id, String key,ModelMap map) {

        map.put("worksId",id);

        IngenuityWorks projectQuotation = ingenuityWorksService.findByPrimaryKey(id.toString());

        if(projectQuotation!=null) {
            map.put("projectName", projectQuotation.getProjectName());
        }

        key= StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("key", key);
        return new ModelAndView("ingenuity/usered/list",map);

    }


    @RequiresPermissions(value = "ingenuity:usered:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(Integer id, String key, HttpServletRequest request) {
        Page<IngenuityUsered> page = getPage(request);
        EntityWrapper<IngenuityUsered> ew=new EntityWrapper();
        ew.like("usered_name",key);
        ew.eq("ingenuity_id",id);
        ew.orderBy("id",false);
        Page<IngenuityUsered> data= ingenuityUseredService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("ingenuity:usered:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        ingenuityUseredService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","操作成功");
        resultMap.put("status",200);
        return resultMap;
    }

    @RequiresPermissions("ingenuity:usered:add")
    @RequestMapping(value="/add/{id}",method=RequestMethod.GET)
    public ModelAndView add(@PathVariable("id")Integer id,ModelMap map){
        map.put("id",id);
        return new ModelAndView("/ingenuity/usered/add",map);
    }

    @RequiresPermissions("ingenuity:usered:add")
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doAdd(IngenuityUsered model){
        Map<String, Object> resultMap = new LinkedHashMap();
        Usered usered = useredService.findByPrimaryKey(model.getUseredId().toString());
        model.setMobile(usered.getUsername());
        model.setUseredName(usered.getRealName());
        if(ingenuityUseredService.insert(model)!=0){
            resultMap.put("status", 200);
            resultMap.put("message", "添加成功");
        }
        else
        {
            resultMap.put("status", 500);
            resultMap.put("message", "添加失败");
        }
        return resultMap;
    }

    @RequiresPermissions("ingenuity:usered:edit")
    @RequestMapping(value="/edit/{id}/{worksId}",method=RequestMethod.GET)
    public ModelAndView add(@PathVariable("id")Integer id,@PathVariable("worksId")Integer optionalId,ModelMap map){
        IngenuityUsered quotationMaterial = ingenuityUseredService.findByPrimaryKey(optionalId.toString());
        map.put("optional",quotationMaterial);
        map.put("worksId",id);
        return new ModelAndView("ingenuity/usered/edit",map);
    }

    @RequiresPermissions("ingenuity:usered:edit")
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doEdit(IngenuityUsered model){
        Map<String, Object> resultMap = new LinkedHashMap();
        Usered usered=useredService.findByPrimaryKey(model.getUseredId().toString());
        model.setUseredName(usered.getRealName());
        model.setMobile(usered.getUsername());
        if(ingenuityUseredService.updateByPrimaryKey(model)!=0){

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
