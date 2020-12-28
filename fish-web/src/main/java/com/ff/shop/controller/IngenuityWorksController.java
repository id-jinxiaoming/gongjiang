package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.DateUtils;
import com.ff.shop.model.*;
import com.ff.shop.service.IngenuityImgService;
import com.ff.shop.service.IngenuityWorksService;
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
import java.util.*;

@Controller
@RequestMapping("/ingenuity/works")
public class IngenuityWorksController extends BaseController {

    @Reference
    private IngenuityWorksService ingenuityWorksService;

    @Reference
    private IngenuityImgService ingenuityImgService;

    @RequiresPermissions(value = "ingenuity:works:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(String key, ModelMap map) {


        key= StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("key", key);
        return new ModelAndView("ingenuity/works/list",map);

    }


    @RequiresPermissions(value = "ingenuity:works:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(String key, HttpServletRequest request) {
        Page<IngenuityWorks> page = getPage(request);
        EntityWrapper<IngenuityWorks> ew=new EntityWrapper();
        ew.like("project_name","%"+key+"%");
        ew.orderBy("id",false);
        Page<IngenuityWorks> data= ingenuityWorksService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("ingenuity:works:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        ingenuityWorksService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }

    @RequiresPermissions("ingenuity:works:add")
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public ModelAndView add(ModelMap map){

        return new ModelAndView("/ingenuity/works/add",map);
    }

    @RequiresPermissions("ingenuity:works:add")
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doAdd(IngenuityWorks model,HttpServletRequest request){
        Map<String, Object> resultMap = new LinkedHashMap();
        model.setCreateTime(DateUtils.getDateTime());
        Integer id=ingenuityWorksService.insert(model);
        if(id!=0){
            String[] photo=request.getParameterValues("photo");
            if(photo!=null)
            {
                for (String item:photo) {
                    IngenuityImg ingenuityImg=new IngenuityImg();
                    ingenuityImg.setReservationId(id);
                    ingenuityImg.setImg(item);
                    ingenuityImgService.insert(ingenuityImg);
                }
            }

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

    @RequiresPermissions("ingenuity:works:edit")
    @RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id")Integer id, ModelMap map){
        map.put("ingenuityWorks",ingenuityWorksService.findByPrimaryKey(id.toString()));
        IngenuityImg goodsMap=new IngenuityImg();
        goodsMap.setReservationId(id);
        List<IngenuityImg> album=ingenuityImgService.selectByT(goodsMap);
        map.put("album", album);
        return new ModelAndView("/ingenuity/works/edit",map);
    }
    @RequiresPermissions("ingenuity:works:edit")
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doEdit(IngenuityWorks model,HttpServletRequest request){
        Map<String, Object> resultMap = new LinkedHashMap();
        Integer id=ingenuityWorksService.updateByPrimaryKey(model);
        if(id!=0){
            //删除之前数据
            ingenuityImgService.deleteById(model.getId());
            String[] photo=request.getParameterValues("photo");
            if(photo!=null)
            {
                for (String item:photo) {
                    IngenuityImg album=new IngenuityImg();
                    album.setReservationId(model.getId());
                    album.setImg(item);
                    ingenuityImgService.insert(album);
                }
            }
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
