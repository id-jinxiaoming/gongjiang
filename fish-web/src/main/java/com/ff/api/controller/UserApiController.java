package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.*;
import com.ff.shop.model.CraftsmanAchievement;
import com.ff.shop.model.CraftsmanAchievementTimes;
import com.ff.shop.model.bo.CraftsmanAchievementBO;
import com.ff.shop.service.CraftsmanAchievementService;
import com.ff.system.service.CommonService;
import com.ff.user.model.*;
import com.ff.user.model.bo.Craftsman;
import com.ff.user.model.bo.StrengthCraftsman;
import com.ff.user.model.bo.UserSingle;
import com.ff.user.service.*;
import com.sun.javafx.collections.MappingChange;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.SwitchNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/api/user")
public class UserApiController extends BaseController {


    @Reference
    private UseredService useredService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private CommonService commonService;

    @Reference
    private UseredLabelService useredLabelService;

    @Reference
    private UseredWorkTypeService useredWorkTypeService;

    @Reference
    private UseredExperienceService useredExperienceService;

    @Reference
    private UseredPhotoService useredPhotoService;

    @Reference
    private UseredRanksInformationService useredRanksInformationService;

    @Reference
    private CraftsmanAchievementService craftsmanAchievementService;

    @Reference
    private AuthenticationService authenticationService;



    /**
     * 手机号验证码登入
     * @param mobile
     * @param code
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="根据手机号登录",notes="根据手机号登录")
    @RequestMapping(value="/loginByMobile",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData LoginByMobile(
            @ApiParam(required=true,name="mobile",value="手机号")@RequestParam(value="mobile",required=false)String mobile,
            @ApiParam(required=true,name="code",value="验证码")@RequestParam(value="code",required=false)String code
    ){
        ResponseData result = new ResponseData();
        String checkCode=redisTemplate.opsForValue().get("smsCode:"+mobile).toString();

        if(!checkCode.equals(code)){
            result.setState(500);
            result.setMessage("验证码不正确");
            return result;
        }
        Usered usered =new Usered();
        usered.setUsername(mobile);
        Usered model= useredService.findOne(usered);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在");
        } else{
            //更新用户登录信息
            String token= MD5Utils.createToken(model.getUsername());
            model.setToken(token);
            model.setUpateTime(new Date());
            useredService.updateByPrimaryKey(model);
            //返回数据
            Map<String,Object> map= new HashMap<>();
            map.put("token",model.getToken());
            map.put("name",model.getRealName());
            result.setState(200);
            result.setDatas(map);
            result.setMessage(" 登入成功");
        }
        return result;
    }


    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="发送短信验证码",notes="发送短信验证码", httpMethod = "POST")
    @RequestMapping(value="/sendIdentifyingCode",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData sendIdentifyingCode(
            @ApiParam(required=true,name="sms_code",value="验证码类型")@RequestParam(value="sms_code",required=false)String sms_code,
            @ApiParam(required=true,name="mobile",value="手机号")@RequestParam(value="mobile",required=false)String mobile){
        int code = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;// 产生1000-9999的随机数


        String json="{\"code\":\""+code+"\"}";
        ResponseData result = new ResponseData();
        if(!UtilPath.isMobile(mobile)){//验证手机号码格式是否正确
            result.setState(500);
            result.setMessage("手机号码格式不正确！");
            return result;
        }
        try {
            commonService.sendSMS("sms_code", mobile, json);//调运发送短信的方法
            //创建session，并将手机号于验证码存入session
            redisTemplate.opsForValue().set("smsCode:"+mobile,code, 180000, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            result.setState(500);
            result.setMessage("短信发送失败！");
            return result;
        }
        result.setState(200);
        result.setMessage("短信发送成功！");
        result.setDatas(code);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="发送短信验证码",notes="发送短信验证码", httpMethod = "POST")
    @RequestMapping(value="/sendCodes",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData sendCodes(){

        String name = "中石化研究院办公楼";
        String material ="防火岩棉板、双面铝箔岩棉保温板";
        String json="{\"name\":\""+name+"\",\"material\":\""+material+"\"}";
        ResponseData result = new ResponseData();
        String mobile="13161602850";
        try {
            commonService.sendSMS("sms_codes", mobile, json);//调运发送短信的方法
        }catch (Exception e){
            result.setState(500);
            result.setMessage("短信发送失败！");
            return result;
        }
        result.setState(200);
        result.setMessage("短信发送成功！");
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="发送短信验证码",notes="发送短信验证码", httpMethod = "POST")
    @RequestMapping(value="/sendCoded",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData sendCoded(){

        String name = "中石化研究院办公楼";
        String value ="防火岩棉板、双面铝箔岩棉保温板";
        String time ="2020-07-20";
        String json="{\"name\":\""+name+"\",\"value\":\""+value+"\",\"time\":\""+time+"\"}";
        ResponseData result = new ResponseData();
        String mobile="13930670087,15188393065";
        try {
            commonService.sendSMS("sms_coded", mobile, json);//调运发送短信的方法
        }catch (Exception e){
            result.setState(500);
            result.setMessage("短信发送失败！");
            return result;
        }
        result.setState(200);
        result.setMessage("短信发送成功！");
        return result;
    }

    /**
     * 手机号注册（账号为手机号）
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="会员注册",notes="会员注册", httpMethod = "POST")
    @RequestMapping(value="/register",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData register(HttpServletRequest request, HttpServletResponse response,
                                 @ApiParam(required=true,name="username",value="用户名(手机号)")@RequestParam(value="username",required=false)String username,
                                 @ApiParam(required=true,name="code",value="验证码")@RequestParam(value="code",required=false)String code){
        ResponseData result = new ResponseData();
        String invitationCode= request.getParameter("invitationCode");

        //验证手机号码格式是否正确
        if(!UtilPath.isMobile(username)){
            result.setState(500);
            result.setMessage("手机号码格式不正确！");
            return result;
        }
        String checkCode=redisTemplate.opsForValue().get("smsCode:"+username).toString();
        if(!checkCode.equals(code)){
            result.setState(700);
            result.setMessage("验证码不正确");
            return result;
        }
        Usered usered =new Usered();
        usered.setUsername(username);
        Usered model= useredService.findOne(usered);
        if(model!=null){
            result.setState(800);
            result.setMessage("此手机号已存在");
            return result;
        }
        if(!invitationCode.equals("")|| ! invitationCode.equals(null)){
            usered.setInvitedBy(invitationCode);
        }
        usered.setUsername(username);
        usered.setCreateTime(new Date());

        Integer id= useredService.insert(usered);
        if(id>0) {
            result.setState(200);
            result.setMessage("注册成功！");
        }
        return result;

    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取工匠列表",notes="获取工匠列表")
    @RequestMapping(value="/getCraftsmanList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        ResponseData result = new ResponseData();
        Map map = new HashMap();
        Page<Usered> page = getPage(request);
        EntityWrapper<Usered> ew = new EntityWrapper();
        ew.eq("user_status", 1);
        ew.eq("foreman_status",3);
        ew.eq("team_information_status",4);
        ew.orderBy("integral",false);

        Page<Usered> list = useredService.selectPage(page, ew);

        List<Craftsman> list2 =addDataUser(list.getRecords());
        map.put("pages",list.getPages());
        map.put("total",list.getTotal());
        map.put("data",list2);
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取工匠列表分页",notes="获取工匠列表分页")
    @RequestMapping(value="/getCraftsmanListPage",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanListPage(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(required = true,name = "type",value = "type")@RequestParam(required = false,value = "type")String type,
                                             @ApiParam(required = true,name = "size",value = "size")@RequestParam(required = false,value = "size")String size,
                                             @ApiParam(required = true,name = "hometown",value = "hometown")@RequestParam(required = false,value = "hometown")String hometown,
                                             @ApiParam(required = true,name = "number",value = "number")@RequestParam(required = false,value = "number")Integer number) {

        ResponseData result = new ResponseData();
        String all ="全部";
        Map map = new HashMap();
        switch(number){
            case 0 :
                map = gongTongDeJie(request);//首次进入页面默认状态（三个筛选条件都是全部默认情况）
                break;
            case 1 :
                if (StringUtils.isEmpty(type)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                if(all.equals(type)){
                  map = gongTongDeJie(request);
                  break;
                }
                map =getByTypeData(request,type);//根据type条件查询
                break;
            case 2:
                if(StringUtils.isEmpty(size)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                if(all.equals(size)){
                    map = gongTongDeJie(request);//默认返回全部的
                    break;
                }
                map = getBySizeData(request,size);//根据size返回
                break;
            case 3:
                if(StringUtils.isEmpty(hometown)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                if(all.equals(hometown)){
                    map = gongTongDeJie(request);//默认返回全部的
                    break;
                }
                map = getDataByHometown(request,hometown);//根据家乡获取
                break;
            case 4:
                if(StringUtils.isEmpty(type) || StringUtils.isEmpty(size)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                map = getDataByTypeAndSize(request,type,size);
                break;
            case 5:
                if(StringUtils.isEmpty(type) || StringUtils.isEmpty(hometown)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                map = getDataByTypeAndHometown(request,type,hometown);//根据工匠列表获取符合type的，然后再筛选符合hometown的
                break;
            case 6:
                if(StringUtils.isEmpty(size) || StringUtils.isEmpty(hometown)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                map = getDataBySizeAndHometown(request,size,hometown);//根据size和hometown获取
                break;
            default:
                if(StringUtils.isEmpty(type) || StringUtils.isEmpty(size) || StringUtils.isEmpty(hometown)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                map = getDataByTypeAndSizeAndHometown(request,type,size,hometown);
                break;
        }
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }

    public Map gongTongDeJie(HttpServletRequest request){
        Map map = new HashMap();
        Page<Usered> page = getPage(request);
        EntityWrapper<Usered> ew = new EntityWrapper();
        ew.eq("user_status", 1);
        ew.eq("foreman_status",3);
        ew.eq("team_information_status",4);
        ew.orderBy("integral",false);

        Page<Usered> list = useredService.selectPage(page, ew);
        List<Craftsman> list2 =addDataUser(list.getRecords());
        map.put("pages",list.getPages());
        map.put("total",list.getTotal());
        map.put("data",list2);
        return map;
    }

    public Map getByTypeData(HttpServletRequest request,String type){
        Map map = new HashMap();

        Usered user = new Usered();
        user.setUserStatus(1);
        user.setForemanStatus(3);
        user.setTeamInformationStatus(4);
        List<Usered> userList = useredService.selectByT(user);
        Collections.sort(userList, new Comparator<Usered>() {
            @Override
            public int compare(Usered o1, Usered o2) {
                //升序
                return o2.getIntegral().compareTo(o1.getIntegral());
            }
        });
        List<Usered> list1 = new ArrayList<>();
        for(Usered usered :userList){
            UseredWorkType useredWorkType = new UseredWorkType();
            useredWorkType.setUserId(usered.getId());
            useredWorkType.setProfession(type);
            List<UseredWorkType> list2 = useredWorkTypeService.selectByT(useredWorkType);
            if(list2.size()>0) {
                list1.add(usered);
            }
        }
        List<Craftsman> list2 =addDataUser(list1);
        Integer page1 = Integer.valueOf(request.getParameter("page"));
        Integer rows1 = Integer.valueOf(request.getParameter("rows"));
        --page1;
        if(list2.size()>0) {
            Paging paging = Paging.pagination(list2.size(), rows1, page1);
            int fromIndex = paging.getQueryIndex();
            int toIndex = 0;
            if (fromIndex + paging.getPageSize() >= list2.size()) {
                toIndex = list2.size();
            } else {
                toIndex = fromIndex + paging.getPageSize();
            }
            if (fromIndex > toIndex) {

                return map;
            }

            map.put("pages",paging.getTotalPage());
            map.put("total",paging.getTotalNum());
            map.put("data",list2.subList(fromIndex, toIndex));
        }
        return map;
    }

    public Map getBySizeData(HttpServletRequest request,String size){
        Map map = new HashMap();
        Page<Usered> page = getPage(request);
        EntityWrapper<Usered> ew = new EntityWrapper();
        ew.eq("user_status", 1);
        ew.eq("foreman_status",3);
        ew.eq("team_information_status",4);
        ew.eq("team_size",size);
        ew.orderBy("integral",false);

        Page<Usered> list = useredService.selectPage(page, ew);

        List<Craftsman> list2 =addDataUser(list.getRecords());

        map.put("pages",list.getPages());
        map.put("total",list.getTotal());
        map.put("data",list2);
        return map;
    }

    public Map getDataByHometown (HttpServletRequest request,String hometown){
        Map map = new HashMap();
        Page<Usered> page = getPage(request);
        EntityWrapper<Usered> ew = new EntityWrapper();
        ew.eq("user_status", 1);
        ew.eq("foreman_status",3);
        ew.eq("team_information_status",4);
        ew.eq("hometown",hometown);
        ew.orderBy("integral",false);

        Page<Usered> list = useredService.selectPage(page, ew);

        List<Craftsman> list2 =addDataUser(list.getRecords());

        map.put("pages",list.getPages());
        map.put("total",list.getTotal());
        map.put("data",list2);

        return map;
    }

    public Map getDataByTypeAndSize (HttpServletRequest request,String type,String size){
        Map map = new HashMap();
        String all ="全部";

        if(type.equals(all)&& size.equals(all)){
            map =gongTongDeJie(request);
        }else if(!type.equals(all) && !size.equals(all)){
            List<Craftsman> listed = new ArrayList<>();
            Usered user = new Usered();
            user.setUserStatus(1);
            user.setForemanStatus(3);
            user.setTeamInformationStatus(4);
            user.setTeamSize(size);
            List<Usered> userList = useredService.selectByT(user);

            Collections.sort(userList, new Comparator<Usered>() {
                @Override
                public int compare(Usered o1, Usered o2) {
                    //升序
                    return o2.getIntegral().compareTo(o1.getIntegral());
                }
            });

            List<Craftsman> list1= getListByWorkTypes(userList,type);//通过工长遍历获取
            if(list1.size()>0){
                for(Craftsman craftsman:list1){
                    Usered usered = useredService.findByPrimaryKey(craftsman.getId().toString());
                    if(usered.getTeamSize().equals(size)){
                        listed.add(craftsman);
                    }
                }
            }
            Integer page1 = Integer.valueOf(request.getParameter("page"));
            Integer rows1 = Integer.valueOf(request.getParameter("rows"));
            --page1;
            if(listed.size()>0){
                Paging paging = Paging.pagination(listed.size(), rows1, page1);
                int fromIndex = paging.getQueryIndex();
                int toIndex = 0;
                if (fromIndex + paging.getPageSize() >= listed.size()) {
                     toIndex = listed.size();
                } else {
                    toIndex = fromIndex + paging.getPageSize();
                }
                if (fromIndex > toIndex) {
                    return map;
                }
                map.put("pages",paging.getTotalPage());
                map.put("total",paging.getTotalNum());
                map.put("data",listed.subList(fromIndex, toIndex));
                return map;
            }

        }else if(!type.equals(all) && size.equals(all)){
            map =getByTypeData(request,type);
        }else{//type==all&& size!=all
            map=getBySizeData(request,size);
        }

        return map;
    }

    public List<Craftsman> getListByWorkTypes(List<Usered> user,String workType){
        List<Craftsman> list = new ArrayList<>();
        if(user.size()>0){
            for(Usered usered :user){
                UseredWorkType useredWorkType = new UseredWorkType();
                useredWorkType.setUserId(usered.getId());
                useredWorkType.setProfession(workType);
                List<UseredWorkType> list1 = useredWorkTypeService.selectByT(useredWorkType);
                if(list1.size()>0){
                    Craftsman craftsman = new Craftsman();
                    craftsman.setId(usered.getId());
                    craftsman.setToken(usered.getToken());
                    craftsman.setEmploymentTime(usered.getEmploymentTime());
                    craftsman.setTeamSize(usered.getTeamSize());
                    craftsman.setRealName(usered.getRealName());
                    craftsman.setPhoto(usered.getAvatar());
                    craftsman.setList(list1);
                    list.add(craftsman);
                }
            }
        }


        return list;
    }

    public Map getDataByTypeAndHometown (HttpServletRequest request,String type,String hometown){
        Map map = new HashMap();
        String all ="全部";

        if (type.equals(all) && hometown.equals(all)) {
            map =gongTongDeJie(request);
        }else if(!type.equals(all) && !hometown.equals(all)){
            List<Craftsman> listed = new ArrayList<>();
            Usered user = new Usered();
            user.setUserStatus(1);
            user.setForemanStatus(3);
            user.setTeamInformationStatus(4);
            user.setHometown(hometown);
            List<Usered> userList = useredService.selectByT(user);

            Collections.sort(userList, new Comparator<Usered>() {
                @Override
                public int compare(Usered o1, Usered o2) {
                    //升序
                    return o2.getIntegral().compareTo(o1.getIntegral());
                }
            });

            List<Craftsman> list1= getListByWorkTypes(userList,type);//通过工长遍历获取
            if(list1.size()>0){
                for(Craftsman craftsman:list1){
                    Usered usered = useredService.findByPrimaryKey(craftsman.getId().toString());
                    if(usered.getHometown().equals(hometown)){
                        listed.add(craftsman);
                    }
                }
            }
            Integer page1 = Integer.valueOf(request.getParameter("page"));
            Integer rows1 = Integer.valueOf(request.getParameter("rows"));
            --page1;
                if(listed.size()>0) {
                    Paging paging = Paging.pagination(listed.size(), rows1, page1);
                    int fromIndex = paging.getQueryIndex();
                    int toIndex = 0;
                    if (fromIndex + paging.getPageSize() >= listed.size()) {
                        toIndex = listed.size();
                    } else {
                        toIndex = fromIndex + paging.getPageSize();
                    }
                    if (fromIndex > toIndex) {
                        return map;
                    }
                    map.put("pages",paging.getTotalPage());
                    map.put("total",paging.getTotalNum());
                    map.put("data",listed.subList(fromIndex, toIndex));
                    return map;
            }
        }else if(!type.equals(all) && hometown.equals(all)){
            //根据type获取
            map = getByTypeData(request,type);
        }else{//type==all && hometown!=all
            //根据hometown获取
            map = getDataByHometown(request,hometown);
        }
        return map;
    }

    public Map getDataBySizeAndHometown (HttpServletRequest request,String size,String hometown){
        Map map = new HashMap();
        String all ="全部";
            if(size.equals(all) && hometown.equals(all)){
                //返回默认的
                map = gongTongDeJie(request);
            }else if(!size.equals(all) && !hometown.equals(all)){
                Page<Usered> page1 = getPage(request);
                EntityWrapper<Usered> ew1 = new EntityWrapper();
                ew1.eq("user_status", 1);
                ew1.eq("foreman_status",3);
                ew1.eq("team_information_status",4);
                ew1.orderBy("integral",false);
                ew1.eq("team_size",size);
                ew1.eq("hometown",hometown);
                Page<Usered> listed = useredService.selectPage(page1, ew1);
                List<Craftsman> list2 =addDataUser(listed.getRecords());
                map.put("pages",listed.getPages());
                map.put("total",listed.getTotal());
                map.put("data",list2);
                return map;
            }else if(!size.equals(all) && hometown.equals(all)){
                //根据队伍规模获取列表
                map = getBySizeData(request,size);
            }else{//size==all && hometown!=all
                //根据家乡获取列表
                map = getDataByHometown(request,hometown);
            }
        return map;
    }

    public Map getDataByTypeAndSizeAndHometown (HttpServletRequest request,String type,String size,String hometown){
        Map map = new HashMap();
        String all = "全部";
        if(type.equals(all) && size.equals(all) && hometown.equals(all)){
            //返回默认
            map = gongTongDeJie(request);
        }else if(!type.equals(all) && !size.equals(all) && !hometown.equals(all)){
            //先根据后面两个(size,hometown)条件查询，最后根据type(工种)进行比较获取
            Page<Usered> page = getPage(request);
            EntityWrapper<Usered> ew = new EntityWrapper();
            ew.eq("user_status", 1);
            ew.eq("foreman_status",3);
            ew.eq("team_information_status",4);
            ew.orderBy("integral",false);
            ew.eq("team_size",size);
            ew.eq("hometown",hometown);
            Page<Usered> listed = useredService.selectPage(page, ew);
            List<Usered> userList = new ArrayList<>();
            if(listed.getRecords().size()>0){
                for(Usered user: listed.getRecords()){
                    UseredWorkType useredWorkType = new UseredWorkType();
                    useredWorkType.setUserId(user.getId());
                    useredWorkType.setProfession(type);
                    List<UseredWorkType> userWorkTypeList = useredWorkTypeService.selectByT(useredWorkType);
                    if(userWorkTypeList.size()>0){
                        userList.add(user);
                    }
                }
                if(userList.size()>0){
                    List<Craftsman> list2 =addDataUser(userList);
                    Integer page1 = Integer.valueOf(request.getParameter("page"));
                    Integer rows1 = Integer.valueOf(request.getParameter("rows"));
                    --page1;
                    Paging paging = Paging.pagination(list2.size(), rows1, page1);
                    int fromIndex = paging.getQueryIndex();
                    int toIndex = 0;
                    if (fromIndex + paging.getPageSize() >= list2.size()) {
                        toIndex = list2.size();
                     } else {
                        toIndex = fromIndex + paging.getPageSize();
                     }
                     if (fromIndex > toIndex) {
                            return map;
                     }
                        map.put("pages",paging.getTotalPage());
                        map.put("total",paging.getTotalNum());
                        map.put("data",list2.subList(fromIndex, toIndex));
                        return map;
                }else{
                    List<Craftsman> craftsmanList = new ArrayList<>();
                    map.put("pages",0);
                    map.put("total",0);
                    map.put("data",craftsmanList);
                    return map;
                }

            }
        }else if(type.equals(all) && size.equals(all) && !hometown.equals(all)){
            //根据hometown获取列表
            map = getDataByHometown(request,hometown);
        }else if(type.equals(all) && !size.equals(all) && hometown.equals(all)){
            //根据size获取列表
            map = getBySizeData(request,size);
        }else if(type.equals(all) && !size.equals(all) && !hometown.equals(all)){
            //根据size和hometown获取列表
            map = getDataBySizeAndHometown(request,size,hometown);
        }else if(!type.equals(all) && size.equals(all) && hometown.equals(all)){
            //根据type获取列表
            map = getByTypeData(request, type);
        }else if(!type.equals(all) && size.equals(all) && !hometown.equals(all)){
            //根据type和hometown获取
            map = getDataByTypeAndHometown(request,type,hometown);
        }else if (!type.equals(all) && !size.equals(all) && hometown.equals(all)){
            //根据type和size查询
            map = getDataByTypeAndSize(request,type,size);
        }

        return map;
    }


    /**
     * 筛选接口
     * type:专业类别
     * size:队伍规模
     * hometown:家乡
     * number:1:专业类别 2:队伍规模 3:家乡 4:专业类别与队伍规模 5:专业类别与家乡
     *        6:队伍规模与家乡 7:专业类别、队伍规模、家乡
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "工长主页筛选接口",notes = "工长主页筛选接口")
    @RequestMapping(value = "/getScreenList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getScreenList(
            @ApiParam(required = true,name = "type",value = "type")@RequestParam(required = false,value = "type")String type,
            @ApiParam(required = true,name = "size",value = "size")@RequestParam(required = false,value = "size")String size,
            @ApiParam(required = true,name = "hometown",value = "hometown")@RequestParam(required = false,value = "hometown")String hometown,
            @ApiParam(required = true,name = "number",value = "number")@RequestParam(required = false,value = "number")Integer number,
            @ApiParam(required = true,name = "page",value = "page")@RequestParam(required = false,value = "page")Integer page,
            @ApiParam(required = true,name = "rows",value = "rows")@RequestParam(required = false,value = "rows")Integer rows

    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(number.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        List<Craftsman> list = new ArrayList<>();
        List<Craftsman> listst = new ArrayList<>();
        switch(number)
        {
            case 1 :
                if (StringUtils.isEmpty(type)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                list = getListByWorkType(type);

                break;
            case 2 :
                if (StringUtils.isEmpty(size)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                list =getUserListBySizeOrHometown(1,size);
                break;
            case 3 :
                if (StringUtils.isEmpty(hometown)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                list =getUserListBySizeOrHometown(2,hometown);
                break;
            case 4 :
                if (StringUtils.isEmpty(type)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                if (StringUtils.isEmpty(size)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }

                List<Craftsman> list1 = getListByWorkType(type);
                List<Craftsman> list2 = new ArrayList<>();
                if(list1.size()>0){
                    for(Craftsman craftsman:list1){
                        Usered usered = useredService.findByPrimaryKey(craftsman.getId().toString());
                        if(usered.getTeamSize().equals(size)){
                            list2.add(craftsman);
                        }
                    }
                }
                list = list2;
                break;
            case 5:
                if (StringUtils.isEmpty(type)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                if (StringUtils.isEmpty(hometown)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                List<Craftsman> list3 = getListByWorkType(type);
                List<Craftsman> list4 = new ArrayList<>();
                if(list3.size()>0){
                    for (Craftsman craftsman:list3) {
                        Usered usered = useredService.findByPrimaryKey(craftsman.getId().toString());
                        if(usered.getHometown().equals(hometown)){
                            list4.add(craftsman);
                        }
                    }
                }
                list=list4;
                break;
            case 6:
                if (StringUtils.isEmpty(size)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                if (StringUtils.isEmpty(hometown)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }

                List<Craftsman> list5 = getUserListBySizeOrHometown(1,size);
                List<Craftsman> list6 = new ArrayList<>();
                if(list5.size()>0){
                    for (Craftsman craftsman:list5){
                        Usered usered = useredService.findByPrimaryKey(craftsman.getId().toString());
                        if(usered.getHometown().equals(hometown)){
                            list6.add(craftsman);
                        }
                    }
                }
                list=list6;
                break;
            case 7:
                if (StringUtils.isEmpty(type)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                if (StringUtils.isEmpty(size)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                if (StringUtils.isEmpty(hometown)){
                    result.setState(500);
                    result.setMessage("参数为空");
                    return result;
                }
                List<Craftsman> list7 = getListByWorkType(type); //根据专业类别获取工长列表
                List<Craftsman> list8 = new ArrayList<>(); //获取队伍规模相等的列表 一二两个参数对应
                if(list7.size()>0){
                    for(Craftsman craftsman:list7){
                        Usered usered = useredService.findByPrimaryKey(craftsman.getId().toString());
                        if(usered.getTeamSize().equals(size)){
                            list8.add(craftsman);
                        }
                    }
                }
                List<Craftsman> list9 = new ArrayList<>();
                for (Craftsman craftsman:list8){
                    Usered usered = useredService.findByPrimaryKey(craftsman.getId().toString());
                    if(usered.getHometown().equals(hometown)){
                        list9.add(craftsman);
                    }
                }
                /*List<Craftsman> list9 = getUserListBySizeOrHometown(2,hometown);//根据家乡获取列表
                list8.retainAll(list9);
                list=list8;*/
                list=list9;
                break;
            default :
                System.out.println("未知等级");
                break;
        }
        if(list.size()>0){
            for(Craftsman craftsman:list){
                Integer userId = craftsman.getId();
                Usered usered = useredService.findByPrimaryKey(userId.toString());
                if(usered.getUserStatus()==1){
                    listst.add(craftsman);
                }
            }
        }
        //Integer rows = 10;
        //Integer page = 0;
        --page;
        if(list.size()>0) {
            //Paging paging = Paging.pagination(list.size(), rows, page);
            Paging paging = Paging.pagination(listst.size(), rows, page);
            int fromIndex = paging.getQueryIndex();
            int toIndex = 0;
            if (fromIndex + paging.getPageSize() >= list.size()) {
                toIndex = list.size();
            } else {
                toIndex = fromIndex + paging.getPageSize();
            }
            if (fromIndex > toIndex) {
                result.setMessage("");
                return result;
            }

            result.setDatas(list.subList(fromIndex, toIndex));
            result.setState(200);
            result.setMessage("成功");
        }else{
            result.setState(200);
            result.setMessage("数据为空");
            result.setDatas(list);
        }

        return result;
    }


    /**
     * 根据姓名进行搜索
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "根据姓名搜索接口",notes = "根据姓名搜索接口")
    @RequestMapping(value = "/getDataByName",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getDataByName(
            @ApiParam(required = true,name = "name",value = "name")@RequestParam(required = false,value = "name")String name
    ){
        ResponseData result = new ResponseData();

        if(StringUtils.isEmpty(name)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        Usered usered = new Usered();
        usered.setRealName(name);

        List<Usered> list = useredService.selectByT(usered);
        if(list.size()>0){
            result.setState(200);
            result.setMessage("成功");
            result.setDatas(addDataUser(list));

        }else{
            List<Craftsman> list1 = new ArrayList<>();
            result.setState(200);
            result.setMessage("数据为空");
            result.setDatas(list1);
        }
        return result;
    }

    /**
     * 根据用户id获取用户详情
     * userId
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "根据用户id获取用户详情",notes = "根据用户id获取用户详情")
    @RequestMapping(value = "/getUserSingleData",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getUserSingleData(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        Usered usered = useredService.findUserByToken(token);
        if (null== usered){
            result.setState(500);
            result.setMessage("用户不存在");
            return result;
        }
        Integer integral = usered.getIntegral();
        UserSingle userSingle = new UserSingle();
        userSingle.setId(usered.getId());
        userSingle.setAvatar(usered.getAvatar());
        userSingle.setRealName(usered.getRealName());
        double number = CraftsmanUtils.getNumberByIntegral(integral);
        userSingle.setCreditRating(number);
        userSingle.setHometown(usered.getHometown());
        if(usered.getWhether()==1){
           String telephone = usered.getUsername().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
           userSingle.setTelephone(telephone);
        }else {
            userSingle.setTelephone(usered.getUsername());
        }
        userSingle.setQrCode(usered.getQrCode());
        userSingle.setUseredLabelList(getUserLabelList(usered.getId()));
        userSingle.setEmploymentTime(usered.getEmploymentTime());
        //获取业绩审核通过的数量
        CraftsmanAchievement craftsmanAchievement = new CraftsmanAchievement();
        craftsmanAchievement.setStatus(2);
        craftsmanAchievement.setUseredId(usered.getId());
        List<CraftsmanAchievement> list = craftsmanAchievementService.selectByT(craftsmanAchievement);

        userSingle.setProjectNumber(list.size());

        userSingle.setTeamSize(usered.getTeamSize());
        userSingle.setUseredWorkTypeList(getUserWorkType(usered.getId()));
        userSingle.setPostCertificate(usered.getPostCertificate());
        userSingle.setUseredExperienceList(getUserExperienceList(usered.getId()));
        userSingle.setStrengthCraftsmanList(getUserRanksInformation(usered.getId()));
        userSingle.setUseredPhotoList(getUserPhotoList(usered.getId()));
        //userSingle.setMap(usered.getId());CraftsmanAchievementApiController类中的getCraftsmanAchievementList方法
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(userSingle);
        return result;
    }

    /**
     * 获取工匠集群中每个分类中得人数
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "获取工匠集群分类人数",notes = "获取工匠集群分类人数")
    @RequestMapping(value = "/getCraftsmanListPeople",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanListPeople(){

        ResponseData result = new ResponseData();

        Map<String,Integer> map  = new HashMap<>();
        UseredRanksInformation useredRanksInformation = new UseredRanksInformation();
        for(int number=1; number<16; number++){
            switch(number){
                case 1:
                    useredRanksInformation.setProfessionalCategory("瓦工");
                    useredRanksInformation.setStatus(1);
                    useredRanksInformation.setType(1);
                    break;
                case 2:
                    useredRanksInformation.setProfessionalCategory("木工");
                    useredRanksInformation.setStatus(2);
                    useredRanksInformation.setType(1);
                    break;
                case 3:
                    useredRanksInformation.setProfessionalCategory("油漆工");
                    useredRanksInformation.setStatus(3);
                    useredRanksInformation.setType(1);
                    break;
                case 4:
                    useredRanksInformation.setProfessionalCategory("水暖工");
                    useredRanksInformation.setStatus(4);
                    useredRanksInformation.setType(1);
                    break;
                case 5:
                    useredRanksInformation.setProfessionalCategory("电工");
                    useredRanksInformation.setStatus(5);
                    useredRanksInformation.setType(1);
                    break;
                case 6:
                    useredRanksInformation.setProfessionalCategory("幕墙工");
                    useredRanksInformation.setStatus(6);
                    useredRanksInformation.setType(1);
                    break;
                case 7:
                    useredRanksInformation.setProfessionalCategory("焊工");
                    useredRanksInformation.setStatus(7);
                    useredRanksInformation.setType(1);
                    break;
                case 8:
                    useredRanksInformation.setProfessionalCategory("防水工");
                    useredRanksInformation.setStatus(8);
                    useredRanksInformation.setType(1);
                    break;
                case 9:
                    useredRanksInformation.setProfessionalCategory("拆旧工");
                    useredRanksInformation.setStatus(9);
                    useredRanksInformation.setType(1);
                    break;
                case 10:
                    useredRanksInformation.setProfessionalCategory("模板工");
                    useredRanksInformation.setStatus(10);
                    useredRanksInformation.setType(1);
                    break;
                case 11:
                    useredRanksInformation.setProfessionalCategory("架子工");
                    useredRanksInformation.setStatus(11);
                    useredRanksInformation.setType(1);
                    break;
                case 12:
                    useredRanksInformation.setProfessionalCategory("钢筋工");
                    useredRanksInformation.setStatus(12);
                    useredRanksInformation.setType(1);
                    break;
                case 13:
                    useredRanksInformation.setProfessionalCategory("钣金工");
                    useredRanksInformation.setStatus(13);
                    useredRanksInformation.setType(1);
                    break;
                case 14:
                    useredRanksInformation.setProfessionalCategory("杂工");
                    useredRanksInformation.setStatus(14);
                    useredRanksInformation.setType(1);
                    break;
                case 15:
                    useredRanksInformation.setProfessionalCategory("其他工种");
                    useredRanksInformation.setStatus(15);
                    useredRanksInformation.setType(1);
                    break;
                 default:
                     System.out.println("什么也不是");
            }
            List<UseredRanksInformation> useredRanksInformationList = useredRanksInformationService.selectByT(useredRanksInformation);
            Integer key = number;
            map.put(key.toString(), useredRanksInformationList.size());
        }
        result.setDatas(map);
        result.setMessage("成功");
        result.setState(200);
        return result;
    }

    /**
     *获取集群分类中得列表数据
     * typeName 分类名称
     * number   分类顺序
     * page 1
     * rows 10
     * @return
     */
    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "获取工匠集群分类列表数据",notes = "获取工匠集群分类列表数据")
    @RequestMapping(value = "/getCraftsmanTypeDataList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanTypeDataList(HttpServletRequest request,
            @ApiParam(required = true,name = "typeName",value = "typeName")@RequestParam(required = false,value = "typeName")String typeName,
            @ApiParam(required = true,name = "number",value = "number")@RequestParam(required = false,value = "number")Integer number){
        ResponseData result = new ResponseData();
        Map map = new HashMap();
        List listed = new ArrayList();
        Page page = getPage(request);
        EntityWrapper<UseredRanksInformation> ew = new EntityWrapper<>();
        ew.eq("professional_category",typeName);
        ew.eq("status",number);
        Page<UseredRanksInformation> list = useredRanksInformationService.selectPage(page,ew);
        for (UseredRanksInformation useredRanksInformation:list.getRecords()){
            String idCard = useredRanksInformation.getCard();
            UseredRanksInformation userRanksInformation1 = new UseredRanksInformation();
            userRanksInformation1.setName(useredRanksInformation.getName());
            userRanksInformation1.setCard(idCard.substring(0,6)+"******"+idCard.substring(idCard.length()-4));
            userRanksInformation1.setHometown(useredRanksInformation.getHometown());
            userRanksInformation1.setEmploymentTime(useredRanksInformation.getEmploymentTime());
            listed.add(userRanksInformation1);
        }

        map.put("data",listed);
        map.put("pages",list.getPages());
        map.put("total",list.getTotal());
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }

    //认可他接口
    //token点击认证按钮的人 唯一标识
    //useredId 被认可人的唯一id
    @CrossOrigin(value = "*" ,maxAge = 3600)
    @ApiOperation(value = "认可他接口",notes = "认可他接口")
    @RequestMapping(value = "/authentication",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData authentication(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token,
            @ApiParam(required = true,name = "useredId",value = "useredId")@RequestParam(required = false,value = "useredId")Integer useredId){

        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }

        if(StringUtils.isEmpty(useredId.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String isId = MD5Utils.parseToken(token);
        if(isId==null){
            result.setState(1000);
            result.setMessage("登入失效");
            return result;
        }

        Usered usered = useredService.findUserByToken(token);
        if(usered==null){
            result.setState(600);
            result.setMessage("用户不存在");
            return result;
        }

        Usered usered1 = useredService.findByPrimaryKey(useredId.toString());
        if(usered==null){
            result.setState(600);
            result.setMessage("用户不存在");
            return result;
        }
        //一天内是否可以对同一个人进行认证 根据两个id去数据库查询 存在不可以继续认证，不存在继续认证
        Authentication authentications = new Authentication();
        authentications.setAuthenticatorId(usered.getId());//认证人id
        authentications.setAuthenticatedPersonId(usered1.getId());//被认证人id
        List<Authentication> listed = authenticationService.selectByT(authentications);
        if(listed.size()>0){
            result.setState(700);
            result.setMessage("已认证");
            return result;
        }

        Integer userStatus = usered1.getUserStatus();
        Authentication authentication = new Authentication();
        authentication.setAuthenticatorId(usered.getId());
        List<Authentication> list = authenticationService.selectByT(authentication);
        if(list.size()==0 || list.size()<10){
            Authentication authentication1 = new Authentication();
            authentication1.setAuthenticatorId(usered.getId());
            authentication1.setAuthenticatedPersonId(useredId);
            Integer id =authenticationService.insert(authentication1);
            if(id>0){
                switch (userStatus){
                    case 0:
                        usered1.setIntegral(usered1.getIntegral()+2);
                        useredService.updateByPrimaryKey(usered1);
                        break;
                    case 1:
                        usered1.setIntegral(usered1.getIntegral()+10);
                        useredService.updateByPrimaryKey(usered1);
                        break;
                }
                result.setState(200);
                result.setMessage("认证成功");
                return result;
            }else{
                result.setState(400);
                result.setMessage("认证失败");
                return result;
            }
        }else{
            result.setState(800);
            result.setMessage("今日认证次数以达到上限！");
            return result;
        }

    }




    //根据工长积分判断用户显示得星星图片
    public Integer getNumberByIntegral(Integer integral){
        Integer number=0;
        if(0<integral && integral<=10){
            number=1;
        }else if (10 < integral && integral <= 20){
            number=2;
        }else if (20 < integral && integral <= 50){
            number=3;
        }else if (50 < integral && integral <= 70){
            number=4;
        }else if (70 < integral && integral <= 100){
            number=5;
        }else if (100 < integral && integral <= 150){
            number=6;
        }else{
            number=7;
        }
        return number;
    }

    //根据工长id获取工长标签
    public List<UseredLabel> getUserLabelList(Integer userId){
        UseredLabel useredLabel = new UseredLabel();
        useredLabel.setUserId(userId);
        List<UseredLabel> useredLabelList = useredLabelService.selectByT(useredLabel);

        return useredLabelList;
    }

    //根据工长id获取队伍工种
    public List<UseredWorkType> getUserWorkType(Integer id){
        UseredWorkType useredWorkType = new UseredWorkType();
        useredWorkType.setUserId(id);
        List<UseredWorkType> list = useredWorkTypeService.selectByT(useredWorkType);
        return list;
    }
    //根据工长ID获取主力工长
    public List<UseredExperience> getUserExperienceList(Integer id){
        UseredExperience useredExperience = new UseredExperience();
        useredExperience.setUserId(id);
        List<UseredExperience> list = useredExperienceService.selectByT(useredExperience);
        return list;
    }
    //根据工长ID获取工长相册集合
    public List<UseredPhoto> getUserPhotoList(Integer id){
        UseredPhoto useredPhoto = new UseredPhoto();
        useredPhoto.setUserId(id);
        List<UseredPhoto> list = useredPhotoService.selectByT(useredPhoto);
        return list;
    }
    //根据工长ID获取工长队伍信息
    public List<StrengthCraftsman> getUserRanksInformation(Integer id){
        List<StrengthCraftsman> list = new ArrayList<>();
        UseredRanksInformation useredRanksInformation = new UseredRanksInformation();
        useredRanksInformation.setUserId(id);
        useredRanksInformation.setType(1);
        List<UseredRanksInformation> useredRanksInformationList = useredRanksInformationService.selectByT(useredRanksInformation);
        if(useredRanksInformationList.size()>0){
            for (UseredRanksInformation useredRanksInformation1 : useredRanksInformationList){
                StrengthCraftsman strengthCraftsman = new StrengthCraftsman();
                strengthCraftsman.setName(useredRanksInformation1.getName());
                strengthCraftsman.setNumber(useredRanksInformation1.getEmploymentTime());
                strengthCraftsman.setType(useredRanksInformation1.getProfessionalCategory());
                list.add(strengthCraftsman);
            }
        }
        return list;
    }




    public List<Craftsman> addDataUser(List<Usered> usered1){
        List<Craftsman> list2 =new ArrayList<>();
        if(usered1.size()>0) {

            for (Usered usered : usered1) {
                Craftsman craftsman = new Craftsman();
                Integer id = usered.getId();
                UseredWorkType useredWorkType = new UseredWorkType();
                useredWorkType.setUserId(id);
                List<UseredWorkType> list1 = useredWorkTypeService.selectByT(useredWorkType);
                craftsman.setList(list1);
                craftsman.setEmploymentTime(usered.getEmploymentTime());
                craftsman.setRealName(usered.getRealName());
                craftsman.setTeamSize(usered.getTeamSize());
                craftsman.setPhoto(usered.getAvatar());
                craftsman.setId(id);
                craftsman.setToken(usered.getToken());
                list2.add(craftsman);
            }

        }
        return list2;
    }

    //根据专业类别获取工长列表
    public List<Craftsman> getListByWorkType(String workType){
        List<Craftsman> list2 =new ArrayList<>();
        UseredWorkType useredWorkType = new UseredWorkType();
        useredWorkType.setProfession(workType);
        List<UseredWorkType> useredWorkTypeList = useredWorkTypeService.selectByT(useredWorkType);
        if(useredWorkTypeList.size()>0){

            for (UseredWorkType useredWorkType1 : useredWorkTypeList){
                Craftsman craftsman = new Craftsman();

                Integer id = useredWorkType1.getUserId();
                Usered usered = useredService.findByPrimaryKey(id.toString());
                if(usered!=null&&usered.getUserStatus()==1){
                    UseredWorkType useredWorkType2 = new UseredWorkType();
                    useredWorkType2.setUserId(id);
                    List<UseredWorkType> list1 = useredWorkTypeService.selectByT(useredWorkType2);

                    craftsman.setList(list1);
                    craftsman.setEmploymentTime(usered.getEmploymentTime());
                    craftsman.setRealName(usered.getRealName());
                    craftsman.setTeamSize(usered.getTeamSize());
                    craftsman.setPhoto(usered.getAvatar());
                    craftsman.setId(usered.getId());//修改之前为id  修改之后为usered.getId()
                    craftsman.setToken(usered.getToken());
                    list2.add(craftsman);
                }
            }

        }
        return list2;
    }

    public List<Craftsman> getUserListBySizeOrHometown(Integer first,String second){
      Usered usered = new Usered();
      if(first==1){
          usered.setTeamSize(second);
      }else{
          usered.setHometown(second);
      }
      List<Usered> list = useredService.selectByT(usered);

      return addDataUser(list);

    }



}
