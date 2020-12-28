package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.DateUtils;
import com.ff.shop.model.TextManagement;
import com.ff.shop.service.TextManagementService;
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
@RequestMapping("/text/management")
public class TextManagementController extends BaseController {

    @Reference
    private TextManagementService textManagementService;


    @RequiresPermissions(value = "text:management:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(String key, ModelMap map) {

        key = StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("key", key);
        return new ModelAndView("/text/management/list", map);

    }

    @RequiresPermissions(value = "text:management:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(String key, HttpServletRequest request) {
        Page<TextManagement> page = getPage(request);
        EntityWrapper<TextManagement> ew = new EntityWrapper<>();
        ew.like("title", "%" + key + "%");
        Page<TextManagement> data = textManagementService.selectPage(page, ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data", data);
        return resultMap;
    }

    @RequiresPermissions("text:management:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        textManagementService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg", "删除成功");
        resultMap.put("status", 200);
        return resultMap;
    }

    @RequiresPermissions("text:management:add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {

        return "/text/management/add";
    }

    @RequiresPermissions("text:management:add")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doAdd(TextManagement model) {
        Map<String, Object> resultMap = new LinkedHashMap();
        model.setContent(StringEscapeUtils.unescapeHtml(model.getContent()));
        model.setTitle(StringEscapeUtils.unescapeHtml(model.getTitle()));
        model.setCreateTime(DateUtils.getDate());
        Integer id = textManagementService.insert(model);
        if (id > 0) {
            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        } else {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }

    @RequiresPermissions("text:management:edit")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Integer id, ModelMap map) {
        TextManagement textManagement = textManagementService.findByPrimaryKey(id.toString());
        map.put("textManagement", textManagement);
        map.put("titles", StringEscapeUtils.escapeHtml(textManagement.getTitle()));
        return new ModelAndView("/text/management/edit", map);
    }

    @RequiresPermissions("text:management:edit")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doEdit(TextManagement model, HttpServletRequest request) {
        Map<String, Object> resultMap = new LinkedHashMap();
        model.setContent(StringEscapeUtils.unescapeHtml(model.getContent()));
        model.setTitle(StringEscapeUtils.unescapeHtml(model.getTitle()));
        Integer id = textManagementService.updateByPrimaryKey(model);
        if (id > 0) {
            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        } else {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }



}
