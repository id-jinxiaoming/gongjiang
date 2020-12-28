package com.ff.users.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.user.model.Usered;
import com.ff.user.model.UseredRanksInformation;
import com.ff.user.service.UseredRanksInformationService;
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
import javax.xml.ws.Response;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usered/userRanksInformation")
public class UseredRanksInformationController extends BaseController {

    @Reference
    private UseredRanksInformationService useredRanksInformationService;

    @Reference
    private UseredService useredService;

    @RequiresPermissions(value = "usered:userRanksInformation:list")
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("id")Integer id, ModelMap map) {
        map.put("userId",id);

        return new ModelAndView("/usered/userRanksInformation/list",map);

    }


    @RequiresPermissions(value = "usered:userRanksInformation:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(Integer id, HttpServletRequest request) {
        Page<UseredRanksInformation> page = getPage(request);
        EntityWrapper<UseredRanksInformation> ew=new EntityWrapper();
        ew.eq("user_id",id);
        ew.orderBy("id",false);
        Page<UseredRanksInformation> data= useredRanksInformationService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();

        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions(value = "usered:userRanksInformation:check")
    @RequestMapping(value = "/check/{id}", method = RequestMethod.GET)
    public ModelAndView check(@PathVariable("id")Integer id,ModelMap map) {
        map.put("id", id);
        return new ModelAndView("usered/userRanksInformation/check",map);
    }

    @RequiresPermissions(value = "usered:userRanksInformation:check")
    @RequestMapping(value = "/doCheck", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doCheck(HttpServletRequest request, Integer id,Integer status) {
        UseredRanksInformation useredRanksInformation = useredRanksInformationService.findByPrimaryKey(id.toString());
        Map<String, Object> resultMap = new LinkedHashMap();
        if(useredRanksInformation!=null){
                Integer ids =useredRanksInformation.getUserId();
                Usered usered = useredService.findByPrimaryKey(ids.toString());
                UseredRanksInformation userRanksInformation1 = new UseredRanksInformation();
                userRanksInformation1.setUserId(ids);
                userRanksInformation1.setType(1);
                userRanksInformation1.setUploaderType(0);
                //审核通过的工人信息数量
                List<UseredRanksInformation> list = useredRanksInformationService.selectByT(userRanksInformation1);
                //工长应该上传的工人信息数量
                Integer number =getCraftsmanTeamSizePeopleNumber(usered.getTeamSize());
                switch (status) {
                    case 1:
                        if (number == list.size()) {
                            resultMap = updateStatusAll(usered,useredRanksInformation);
                        } else if (number < list.size()) {
                            resultMap = updateStatusAll(usered,useredRanksInformation);
                        } else if (number > list.size()) {
                                Integer nb = list.size()+1;
                                if(nb==number){
                                    resultMap = updateStatusAll(usered,useredRanksInformation);
                                }else {
                                    usered.setTeamInformationStatus(1);
                                    //start
                                    Integer integral =usered.getIntegral()+5;
                                    usered.setIntegral(integral);
                                    //end
                                    Integer userStatus = useredService.updateByPrimaryKey(usered);
                                    useredRanksInformation.setType(1);
                                    Integer ranksInformationStatus = useredRanksInformationService.updateByPrimaryKey(useredRanksInformation);
                                    if (userStatus > 0 && ranksInformationStatus > 0) {
                                        resultMap.put("msg", "操作成功");
                                        resultMap.put("status", 200);
                                    } else {
                                        resultMap.put("msg", "操作失败");
                                        resultMap.put("status", 500);
                                    }
                                }
                        }
                    break;

                    case 2:
                        if (number == list.size()) {
                            resultMap = updateStatusByReduce(usered,useredRanksInformation);
                        } else if (number < list.size()) {
                            usered.setTeamInformationStatus(4);
                            Integer userStatus =useredService.updateByPrimaryKey(usered);
                            useredRanksInformation.setType(2);
                            Integer ranksInformationStatus =useredRanksInformationService.updateByPrimaryKey(useredRanksInformation);
                            if(userStatus>0 && ranksInformationStatus>0){
                                resultMap.put("msg","操作成功");
                                resultMap.put("status",200);
                            }else{
                                resultMap.put("msg","操作失败");
                                resultMap.put("status",500);
                            }
                        } else if (number > list.size()) {
                            resultMap =updateStatusByReduce(usered,useredRanksInformation);
                        }
                    break;
                    default:
                        System.out.println("未知错误");
                }
            }else{
                resultMap.put("msg","操作失败");
                resultMap.put("status",500);
            }
        return resultMap;

    }

    public int getCraftsmanTeamSizePeopleNumber(String teamSize){
        Integer userNumber=0;
        if(teamSize.equals("5人以下")){
            userNumber=2;
        }else if(teamSize.equals("5-10人")){
            userNumber=3;
        }else if(teamSize.equals("10-20人")){
            userNumber=5;
        }else if(teamSize.equals("20-30人")){
            userNumber=7;
        }else{
            userNumber=10;
        }
        return userNumber;
    }

    public Map<String, Object> updateStatusAll(Usered usered,UseredRanksInformation useredRanksInformation){
        Map<String, Object> resultMap = new LinkedHashMap();
        usered.setTeamInformationStatus(4);
        //start
        Integer integral = usered.getIntegral()+5;
        usered.setIntegral(integral);
        //end
        Integer userStatus =useredService.updateByPrimaryKey(usered);
        useredRanksInformation.setType(1);
        Integer ranksInformationStatus =useredRanksInformationService.updateByPrimaryKey(useredRanksInformation);
        if(userStatus>0 && ranksInformationStatus>0){
            resultMap.put("msg","操作成功");
            resultMap.put("status",200);
        }else{
            resultMap.put("msg","操作失败");
            resultMap.put("status",500);
        }
        return resultMap;
    }

    public Map<String,Object> updateStatusByReduce(Usered usered,UseredRanksInformation useredRanksInformation){
        Map<String, Object> resultMap = new LinkedHashMap();
        usered.setTeamInformationStatus(1);
        Integer userStatus =useredService.updateByPrimaryKey(usered);
        useredRanksInformation.setType(2);
        Integer ranksInformationStatus =useredRanksInformationService.updateByPrimaryKey(useredRanksInformation);
        if(userStatus>0 && ranksInformationStatus>0){
            resultMap.put("msg","操作成功");
            resultMap.put("status",200);
        }else{
            resultMap.put("msg","操作失败");
            resultMap.put("status",500);
        }
        return resultMap;
    }

    @RequiresPermissions(value = "usered:userRanksInformation:listed")
    @RequestMapping(value = "/listed", method = RequestMethod.GET)
    public ModelAndView listed(String key, ModelMap map) {
        key= StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("key", key);
        return new ModelAndView("usered/userRanksInformation/listed",map);
    }

    @RequiresPermissions(value = "usered:userRanksInformation:listed")
    @RequestMapping(value = "/listed", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doListed(HttpServletRequest request, String key) {

        Page<UseredRanksInformation> page = getPage(request);
        EntityWrapper<UseredRanksInformation> ew=new EntityWrapper();
        ew.eq("uploader_type",1);
        ew.like("name","%"+key+"%");
        Page<UseredRanksInformation> data= useredRanksInformationService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("data",data);
        return resultMap;
    }


    @RequiresPermissions(value = "usered:userRanksInformation:checked")
    @RequestMapping(value = "/checked/{id}", method = RequestMethod.GET)
    public ModelAndView checked(@PathVariable("id")Integer id,ModelMap map) {
        map.put("id", id);
        return new ModelAndView("usered/userRanksInformation/checked",map);
    }



    @RequiresPermissions(value = "usered:userRanksInformation:checked")
    @RequestMapping(value = "/doChecked", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doChecked(HttpServletRequest request, Integer id,Integer status) {
        UseredRanksInformation useredRanksInformation = useredRanksInformationService.findByPrimaryKey(id.toString());
        Map<String, Object> resultMap = new LinkedHashMap();
        if(status==1){//1通过1 2失败0
            useredRanksInformation.setType(1);
            Integer userRanksInformationStatus =useredRanksInformationService.updateByPrimaryKey(useredRanksInformation);
            if(userRanksInformationStatus>0){
                resultMap.put("msg", "操作成功");
                resultMap.put("status", 200);
            }else{
                resultMap.put("msg", "操作失败");
                resultMap.put("status", 500);
            }
        }else{
            useredRanksInformation.setType(2);
            Integer userRanksInformationStatus =useredRanksInformationService.updateByPrimaryKey(useredRanksInformation);
            if(userRanksInformationStatus>0){
                resultMap.put("msg", "操作成功");
                resultMap.put("status", 200);
            }else{
                resultMap.put("msg", "操作失败");
                resultMap.put("status", 500);
            }
        }
        return resultMap;
    }

    @RequiresPermissions("usered:userRanksInformation:remove")
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Object delete(String[] id) {
        useredRanksInformationService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }


}
