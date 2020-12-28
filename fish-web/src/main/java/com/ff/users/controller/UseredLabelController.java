package com.ff.users.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.user.model.UseredLabel;
import com.ff.user.service.UseredLabelService;
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
@RequestMapping("/usered/userLabel")
public class UseredLabelController extends BaseController {


    @Reference
    private UseredLabelService useredLabelService;



    @RequiresPermissions(value = "usered:userLabel:list")
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("id")Integer id, ModelMap map) {
        map.put("userId",id);

        return new ModelAndView("/usered/userLabel/list",map);

    }


    @RequiresPermissions(value = "usered:userLabel:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(Integer id,HttpServletRequest request) {
        Page<UseredLabel> page = getPage(request);
        EntityWrapper<UseredLabel> ew=new EntityWrapper();
        ew.eq("user_id",id);
        ew.orderBy("id",false);
        Page<UseredLabel> data= useredLabelService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("usered:userLabel:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        useredLabelService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }

}
