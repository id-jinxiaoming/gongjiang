package com.ff.users.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.user.model.Usered;
import com.ff.user.model.UseredLabel;
import com.ff.user.service.UseredExperienceService;
import com.ff.user.service.UseredLabelService;
import com.ff.user.service.UseredService;
import com.ff.user.service.UseredWorkTypeService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usered/usered")
public class UseredController extends BaseController {


    @Reference
    private UseredService useredService;

    @Reference
    private UseredLabelService useredLabelService;

    @Reference
    private UseredWorkTypeService useredWorkTypeService;

    @Reference
    private UseredExperienceService useredExperienceService;

    @RequiresPermissions(value = "usered:usered:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(String key, ModelMap map) {
        key= StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("key", key);
        return new ModelAndView("usered/usered/list",map);
    }

    @RequiresPermissions(value = "usered:usered:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(HttpServletRequest request, String key) {

        Page<Usered> page = getPage(request);
        EntityWrapper<Usered> ew=new EntityWrapper();
        ew.eq("user_status",1);
        ew.like("real_name","%"+key+"%");
        Page<Usered> data= useredService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("usered:usered:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        useredService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }

    @RequiresPermissions(value = "usered:usered:check")
    @RequestMapping(value = "/check/{id}", method = RequestMethod.GET)
    public ModelAndView check(@PathVariable("id")Integer id,ModelMap map) {
        map.put("id", id);
        return new ModelAndView("usered/usered/check",map);
    }



    @RequiresPermissions(value = "usered:usered:check")
    @RequestMapping(value = "/doCheck", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doCheck(HttpServletRequest request, Integer id,Integer status) {
        Usered usered = useredService.findByPrimaryKey(id.toString());
        Map<String, Object> resultMap = new LinkedHashMap();
        if(status==1){//1通过3  2失败1
            usered.setForemanStatus(3);
            usered.setUserStatus(1);
            Integer userStatus =useredService.updateByPrimaryKey(usered);
            if(userStatus>0){
                resultMap.put("msg", "操作成功");
                resultMap.put("status", 200);
            }else{
                resultMap.put("msg", "操作失败");
                resultMap.put("status", 500);
            }
        }else{
            usered.setForemanStatus(1);
            Integer userStatus =useredService.updateByPrimaryKey(usered);
            if(userStatus>0){
                resultMap.put("msg", "操作成功");
                resultMap.put("status", 200);
            }else{
                resultMap.put("msg", "操作失败");
                resultMap.put("status", 500);
            }
        }
        return resultMap;
    }

}
