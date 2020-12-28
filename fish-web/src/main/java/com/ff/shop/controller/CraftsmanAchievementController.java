package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.shop.model.CraftsmanAchievement;
import com.ff.shop.model.CraftsmanAchievementImg;
import com.ff.shop.model.IngenuityWorks;
import com.ff.shop.service.CraftsmanAchievementImgService;
import com.ff.shop.service.CraftsmanAchievementService;
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
@RequestMapping("/craftsman/achievement")
public class CraftsmanAchievementController extends BaseController {

    @Reference
    private CraftsmanAchievementService craftsmanAchievementService;

    @Reference
    private CraftsmanAchievementImgService craftsmanAchievementImgService;

    @Reference
    private UseredService useredService;

    @RequiresPermissions(value = "craftsman:achievement:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(String key, ModelMap map) {

        key= StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("key", key);
        return new ModelAndView("craftsman/achievement/list",map);
    }


    @RequiresPermissions(value = "craftsman:achievement:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(String key, HttpServletRequest request) {
        Page<CraftsmanAchievement> page = getPage(request);
        EntityWrapper<CraftsmanAchievement> ew=new EntityWrapper();
        ew.like("project_name","%"+key+"%");
        ew.orderBy("id",false);
        Page<CraftsmanAchievement> data= craftsmanAchievementService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions(value = "craftsman:achievement:check")
    @RequestMapping(value = "/check/{id}", method = RequestMethod.GET)
    public ModelAndView check(@PathVariable("id")Integer id, ModelMap map) {
        map.put("id", id);
        return new ModelAndView("craftsman/achievement/check",map);
    }



    @RequiresPermissions(value = "craftsman:achievement:check")
    @RequestMapping(value = "/doCheck", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doCheck(HttpServletRequest request, Integer id,Integer status) {
        CraftsmanAchievement craftsmanAchievement = craftsmanAchievementService.findByPrimaryKey(id.toString());
        Map<String, Object> resultMap = new LinkedHashMap();
        if(status==1){
            craftsmanAchievement.setStatus(2);
            Integer craftsmanAchievementStatus =craftsmanAchievementService.updateByPrimaryKey(craftsmanAchievement);
            Integer userId = craftsmanAchievement.getUseredId();
            Usered usered =  useredService.findByPrimaryKey(userId.toString());
            Integer integral = usered.getIntegral()+10;
            usered.setIntegral(integral);
            useredService.updateByPrimaryKey(usered);
            if(craftsmanAchievementStatus>0){
                resultMap.put("msg", "操作成功");
                resultMap.put("status", 200);
            }else{
                resultMap.put("msg", "操作失败");
                resultMap.put("status", 500);
            }
        }else{
            craftsmanAchievement.setStatus(3);
            Integer craftsmanAchievementStatus =craftsmanAchievementService.updateByPrimaryKey(craftsmanAchievement);
            if(craftsmanAchievementStatus>0){
                resultMap.put("msg", "操作成功");
                resultMap.put("status", 200);
            }else{
                resultMap.put("msg", "操作失败");
                resultMap.put("status", 500);
            }
        }
        return resultMap;
    }

    @RequiresPermissions("craftsman:achievement:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        craftsmanAchievementService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }


    @RequiresPermissions(value = "craftsman:achievement:listed")
    @RequestMapping(value = "/listed/{id}", method = RequestMethod.GET)
    public ModelAndView listed(@PathVariable("id")Integer id, ModelMap map) {
        map.put("craftsmanAchievementId",id);

        return new ModelAndView("/craftsman/achievement/listed",map);

    }


    @RequiresPermissions(value = "craftsman:achievement:listed")
    @RequestMapping(value = "/listed", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doListed(Integer id,HttpServletRequest request) {
        Page<CraftsmanAchievementImg> page = getPage(request);
        EntityWrapper<CraftsmanAchievementImg> ew=new EntityWrapper();
        ew.eq("achievement_id",id);
        ew.orderBy("id",false);
        Page<CraftsmanAchievementImg> data= craftsmanAchievementImgService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("craftsman:achievement:remove")
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Object removeData(String[] id) {
        craftsmanAchievementImgService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }


}
