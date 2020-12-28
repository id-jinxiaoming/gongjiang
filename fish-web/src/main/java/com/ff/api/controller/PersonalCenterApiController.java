package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ff.common.base.BaseController;
import com.ff.common.model.QiniuConfig;
import com.ff.common.model.ResponseData;
import com.ff.common.model.SystemConfig;
import com.ff.common.oss.CloudStorageConfig;
import com.ff.common.oss.OSSFactory;
import com.ff.common.util.*;
import com.ff.shop.model.CraftsmanAchievement;
import com.ff.shop.service.CraftsmanAchievementService;
import com.ff.system.model.Setting;
import com.ff.system.service.SettingService;
import com.ff.user.model.*;
import com.ff.user.model.bo.CraftsmanDetail;
import com.ff.user.model.bo.UserRanksInformation;
import com.ff.user.model.bo.WorkerInformation;
import com.ff.user.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/personalCenter")
public class PersonalCenterApiController extends BaseController {

    @Reference
    private UseredService useredService;

    @Reference
    private CraftsmanAchievementService craftsmanAchievementService;

    @Reference
    private UseredLabelService useredLabelService;

    @Reference
    private UseredExperienceService useredExperienceService;

    @Reference
    private UseredWorkTypeService useredWorkTypeService;

    @Reference
    private UseredRanksInformationService useredRanksInformationService;

    @Reference
    private UseredPhotoService useredPhotoService;

    @Reference
    SettingService settingService;

    @CrossOrigin(value = "*", maxAge = 3600)
    @RequestMapping(value = "/authentication",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "点击个人中心按钮", notes = "点击个人中心按钮接口")
    public ResponseData authentication(
            @ApiParam(required = true, name = "token", value = "token") @RequestParam(required = false, value = "token") String token
    ) {
        ResponseData result = new ResponseData();

        if (StringUtils.isEmpty(token)) {
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String newJson = StringEscapeUtils.unescapeHtml(token);
        String id = MD5Utils.parseToken(newJson);
        if (id == null) {
            result.setState(1000);
            result.setMessage("登入失效");
            return result;
        }
        Usered usered = useredService.findUserByToken(newJson);
        if (usered == null) {
            result.setState(600);
            result.setMessage("用户不存在");
            return result;
        }
        Map<String, Object> map = new HashMap<>();
        Integer foremanStatus = usered.getForemanStatus();
        Integer teamInformationStatus = usered.getTeamInformationStatus();
        if (foremanStatus == 0) {
            map.put("type", 1);
            map.put("status", "请尽快上传完成工长认证");
            map.put("userName",usered.getUsername());
            result.setState(200);
            result.setMessage("成功");
            result.setDatas(map);
        } else {
            switch (foremanStatus) {
                case 1:
                    map.put("type", 2);
                    map.put("status", "您的工长认证审核未通过,请修改后重新提交");
                    map.put("integral", usered.getIntegral());
                    map.put("creditRating", CraftsmanUtils.getNumberByIntegral(usered.getIntegral()));//根据积分显示星星对应的颗数
                    map.put("userName",usered.getRealName());
                    map.put("teamInformationName",teamInformationName(teamInformationStatus));
                    map.put("avatar",usered.getAvatar());
                    map.put("number",getNumber(usered.getId()));
                    break;
                case 2:
                    map.put("type", 2);
                    map.put("status", "您的工长认证已提交，审核完成即可组建队伍");
                    map.put("integral", usered.getIntegral());
                    map.put("creditRating", CraftsmanUtils.getNumberByIntegral(usered.getIntegral()));//根据积分显示星星对应的颗数
                    map.put("userName",usered.getRealName());
                    map.put("teamInformationName",teamInformationName(teamInformationStatus));
                    map.put("avatar",usered.getAvatar());
                    map.put("number",getNumber(usered.getId()));
                    break;
                case 3:
                    map.put("type", 2);
                    map.put("status", "您的工长认证审核已通过，快来组建队伍吧");
                    map.put("integral", usered.getIntegral());
                    map.put("creditRating", CraftsmanUtils.getNumberByIntegral(usered.getIntegral()));//根据积分显示星星对应的颗数
                    map.put("userName",usered.getRealName());
                    map.put("teamInformationName",teamInformationName(teamInformationStatus));
                    map.put("avatar",usered.getAvatar());
                    map.put("number",getNumber(usered.getId()));
                    break;
                default:
                    System.out.println("状态不正确");
                break;
            }
            result.setState(200);
            result.setMessage("成功");
            result.setDatas(map);
        }
        return result;
    }

    /**
     * 工长认证接口
     * @param
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "工长认证接口",notes = "工长认证接口")
    @RequestMapping(value = "/insertInformation",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData insertInformation(HttpServletRequest request,
         @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
         @ApiParam(required=true,name="usered",value="usered") Usered usered,
         @ApiParam(required=true,name="userLabel",value="userLabel")@RequestParam(value="userLabel",required=false)String[] userLabel,
         @ApiParam(required=true,name="userExperience",value="userExperience")@RequestParam(value="userExperience",required=false)String[] userExperience,
         @ApiParam(required=true,name="workType",value="workType")@RequestParam(value="workType",required=false)String[] workType){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(userLabel.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(workType.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(userExperience.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }

        String id = MD5Utils.parseToken(token);
        if(id==null){
            result.setState(1000);
            result.setMessage("登入失效");
            return result;
        }
        Usered usered1 = useredService.findUserByToken(token);
        if(usered1==null){
            result.setState(600);
            result.setMessage("用户不存在");
            return result;
        }
        Integer userId = usered1.getId();
        usered1.setRealName(usered.getRealName());
        usered1.setHometown(usered.getHometown());
        usered1.setAvatar(usered.getAvatar());
        usered1.setCard(usered.getCard());
        usered1.setEmploymentTime(usered.getEmploymentTime());
        usered1.setWhether(usered.getWhether());//0显示1：不显示
        usered1.setQrCode(usered.getQrCode());
        usered1.setPostCertificate(usered.getPostCertificate());//岗位证书
        usered1.setForemanStatus(2);
        usered1.setUserStatus(1);//0：普通用户1；工长用户
        Integer idStatus = useredService.updateByPrimaryKey(usered1);
        if(idStatus>0){
            int number = addSlaveTableData(userId,userLabel,userExperience,workType);
            if(number==1){
                result.setState(200);
                result.setMessage("成功");
            }else{
                result.setState(400);
                result.setMessage("失败");
            }
        }
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "获取工长信息",notes = "获取工长信息")
    @RequestMapping(value = "/getCraftsman",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsman(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token
    ){
        ResponseData result = new ResponseData();

        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String ids = MD5Utils.parseToken(token);
        if(ids==null){
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
        Map<String,List> map= getAllListData(usered.getId());
        CraftsmanDetail craftsmanDetail = new CraftsmanDetail();
        craftsmanDetail.setId(usered.getId());
        craftsmanDetail.setToken(usered.getToken());
        craftsmanDetail.setRealName(usered.getRealName());
        craftsmanDetail.setHometown(usered.getHometown());
        craftsmanDetail.setAvatar(usered.getAvatar());
        craftsmanDetail.setIdCard(usered.getCard());
        craftsmanDetail.setEmploymentTime(usered.getEmploymentTime());
        craftsmanDetail.setWhether(usered.getWhether());
        craftsmanDetail.setQrCode(usered.getQrCode());
        craftsmanDetail.setPostCertificate(usered.getPostCertificate());
        craftsmanDetail.setUserLabelList(map.get("userLabel"));
        craftsmanDetail.setUserWorkTypeList(map.get("userWorkType"));
        craftsmanDetail.setUserExperiences(map.get("userExperienceList"));
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(craftsmanDetail);
        return result;
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "修改工长信息",notes = "修改工长信息")
    @RequestMapping(value = "/editUpAContingent",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editUpAContingent(
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="usered",value="usered") Usered usered,
            @ApiParam(required=true,name="userLabel",value="userLabel")@RequestParam(value="userLabel",required=false)String[] userLabel,
            @ApiParam(required=true,name="userExperience",value="userExperience")@RequestParam(value="userExperience",required=false)String[] userExperience,
            @ApiParam(required=true,name="workType",value="workType")@RequestParam(value="workType",required=false)String[] workType
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String id = MD5Utils.parseToken(token);
        if(id==null){
            result.setState(1000);
            result.setMessage("登入失效");
            return result;
        }
        Usered usered1 = useredService.findUserByToken(token);
        if(usered1==null){
            result.setState(600);
            result.setMessage("用户不存在");
            return result;
        }
        Integer userId = usered1.getId();
        usered1.setRealName(usered.getRealName());
        usered1.setHometown(usered.getHometown());
        usered1.setAvatar(usered.getAvatar());
        usered1.setCard(usered.getCard());
        usered1.setEmploymentTime(usered.getEmploymentTime());
        usered1.setWhether(usered.getWhether());//0显示1：不显示
        usered1.setQrCode(usered.getQrCode());
        usered1.setPostCertificate(usered.getPostCertificate());//岗位证书
        usered1.setTeamSize(usered1.getTeamSize());
        usered1.setForemanStatus(2);
        Integer idStatus = useredService.updateByPrimaryKey(usered1);
        if(idStatus>0){
            int number = addUpDataSlaveTableData(userId,userLabel,userExperience,workType);
            if(number==1){
                result.setState(200);
                result.setMessage("成功");
            }else{
                result.setState(400);
                result.setMessage("失败");
            }
        }
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "获取队伍组建信息页数据",notes = "获取队伍组建信息页数据")
    @RequestMapping(value = "/getCraftsmanTeamData",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanTeamData(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token
    ){
        ResponseData result = new ResponseData();
        Map map = new HashMap();

        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String ids = MD5Utils.parseToken(token);
        if(ids==null){
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

        UseredRanksInformation useredRanksInformation = new UseredRanksInformation();
        useredRanksInformation.setUserId(usered.getId());
        List<UseredRanksInformation> list = useredRanksInformationService.selectByT(useredRanksInformation);
        List<UserRanksInformation> list1 = new ArrayList<>();
        if(list.size()>0){
            for(UseredRanksInformation ranksInformation:list){
                UserRanksInformation userRanksInformation = new UserRanksInformation();
                userRanksInformation.setId(ranksInformation.getId());
                userRanksInformation.setUserId(ranksInformation.getUserId());
                userRanksInformation.setName(ranksInformation.getName());
                userRanksInformation.setHometown(ranksInformation.getHometown());
                userRanksInformation.setIdCard(ranksInformation.getCard());
                userRanksInformation.setEmploymentTime(ranksInformation.getEmploymentTime());
                userRanksInformation.setProfessionalCategory(ranksInformation.getProfessionalCategory());
                userRanksInformation.setStatus(ranksInformation.getStatus());
                userRanksInformation.setType(ranksInformation.getType());
                userRanksInformation.setUploaderType(ranksInformation.getUploaderType());
                list1.add(userRanksInformation);
            }
        }
        map.put("teamSize",usered.getTeamSize());
        map.put("dataList",list1);
        map.put("number",list.size());

        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "组建队伍信息单个上传",notes = "组建队伍信息单个上传")
    @RequestMapping(value = "/setupAContingentSingle",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData setupAContingentSingle(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token,
            @ApiParam(required = true,name = "workerInformation",value = "workerInformation") WorkerInformation workerInformation){
        ResponseData result = new ResponseData();
        Map map = new HashMap();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        Usered usered = useredService.findUserByToken(token);
        if(usered==null){
            result.setState(600);
            result.setMessage("用户不存在");
            return result;
        }
        UseredRanksInformation useredRanksInformation = new UseredRanksInformation();
        useredRanksInformation.setUserId(usered.getId());
        useredRanksInformation.setName(workerInformation.getName());
        useredRanksInformation.setHometown(workerInformation.getHometown());
        useredRanksInformation.setCard(workerInformation.getIdCard());
        useredRanksInformation.setEmploymentTime(workerInformation.getEmploymentTime());
        useredRanksInformation.setProfessionalCategory(workerInformation.getWorkType());
        useredRanksInformation.setStatus(getCategoryNumbers(workerInformation.getWorkType()));
        Integer id =useredRanksInformationService.insert(useredRanksInformation);
        if(id>0){
            UseredRanksInformation userRanksInformation1 = new UseredRanksInformation();
            userRanksInformation1.setUserId(usered.getId());
            List<UseredRanksInformation> list = useredRanksInformationService.selectByT(userRanksInformation1);
            map.put("data",useredRanksInformation);
            map.put("number",list.size());
            result.setDatas(map);
            result.setMessage("成功");
            result.setState(200);
        }else{
            result.setState(400);
            result.setMessage("失败");
            result.setDatas(map);
        }
        return result;
    }



    //队伍规模上传修改一个接口 参数个数名字一致
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "修改队伍规模",notes = "单独上传队伍规模")
    @RequestMapping(value = "/updateCraftsmanTeamSize",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateCraftsmanTeamSize(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token,
            @ApiParam(required = true,name = "number",value = "number")@RequestParam(required = false,value = "number")Integer number,
            @ApiParam(required = true,name = "teamSize",value = "teamSize")@RequestParam(required = false,value = "teamSize")String teamSize
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        Usered usered = useredService.findUserByToken(token);
        if(usered==null){
            result.setState(600);
            result.setMessage("用户不存在");
            return result;
        }



        Integer peopleNumber = getCraftsmanTeamSizePeopleNumber(teamSize);
        if(peopleNumber<=number){
            usered.setTeamSize(teamSize);
            //20200211修改工长队伍信息状态
            usered.setTeamInformationStatus(3);
            Integer id = useredService.updateByPrimaryKey(usered);
            if(id>0){
                result.setState(200);
                result.setMessage("成功");
            }else{
                result.setState(400);
                result.setMessage("失败");
            }
        }else{
            result.setState(400);
            result.setMessage("失败");
        }
        return result;
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "修改队伍工人信息接口",notes = "修改队伍工人信息接口")
    @RequestMapping(value = "/updateCraftsmanPeopleInformation",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateCraftsmanPeopleInformation(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token,
            @ApiParam(required = true,name = "id",value = "id")@RequestParam(required = false,value = "id")Integer id,
            @ApiParam(required = true,name = "workerInformation",value = "workerInformation") WorkerInformation workerInformation
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String ids = MD5Utils.parseToken(token);
        if(ids==null){
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
        UseredRanksInformation userRanksInformation1 = useredRanksInformationService.findByPrimaryKey(id.toString());
        if(userRanksInformation1==null){
            result.setState(100);
            result.setMessage("数据为空");
            return result;
        }
        UseredRanksInformation useredRanksInformation = new UseredRanksInformation();
        useredRanksInformation.setUserId(usered.getId());
        useredRanksInformation.setUploaderType(0);
        useredRanksInformation.setType(1);
        List<UseredRanksInformation> list = useredRanksInformationService.selectByT(useredRanksInformation);

        Integer peopleNumber = getCraftsmanTeamSizePeopleNumber(usered.getTeamSize());
        if(list.size()>peopleNumber){//如果上传审核通过的人数大于自己应该上传的数量 修改队伍工人信息但不修改用户的队伍状态
            userRanksInformation1.setUserId(userRanksInformation1.getUserId());
            userRanksInformation1.setName(workerInformation.getName());
            userRanksInformation1.setHometown(workerInformation.getHometown());
            userRanksInformation1.setCard(workerInformation.getIdCard());
            userRanksInformation1.setEmploymentTime(workerInformation.getEmploymentTime());
            userRanksInformation1.setProfessionalCategory(workerInformation.getWorkType());
            userRanksInformation1.setStatus(getCategoryNumbers(workerInformation.getWorkType()));
            userRanksInformation1.setStatus(0);
            userRanksInformation1.setUploaderType(0);
            Integer userId =useredRanksInformationService.updateByPrimaryKey(userRanksInformation1);
            if(userId>0){
                result.setState(200);
                result.setMessage("成功");
            }else {
                result.setMessage("失败");
                result.setState(400);
            }
        }else{//否则修改用户的队伍信息状态并且更改队伍工人信息
            usered.setForemanStatus(1);
            Integer userStatus=useredService.updateByPrimaryKey(usered);
            if(userStatus>0) {
                userRanksInformation1.setUserId(userRanksInformation1.getUserId());
                userRanksInformation1.setName(workerInformation.getName());
                userRanksInformation1.setHometown(workerInformation.getHometown());
                userRanksInformation1.setCard(workerInformation.getIdCard());
                userRanksInformation1.setEmploymentTime(workerInformation.getEmploymentTime());
                userRanksInformation1.setProfessionalCategory(workerInformation.getWorkType());
                userRanksInformation1.setStatus(getCategoryNumbers(workerInformation.getWorkType()));
                userRanksInformation1.setStatus(0);
                userRanksInformation1.setUploaderType(0);
                Integer userId = useredRanksInformationService.updateByPrimaryKey(userRanksInformation1);
                if(userId>0){
                    result.setState(200);
                    result.setMessage("成功");
                }else{
                    result.setState(400);
                    result.setMessage("失败");
                }
            }
        }
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "删除工长上传的工人信息",notes = "删除工长上传的工人信息")
    @RequestMapping(value = "/removeCraftsmanTeamInformation",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData removeCraftsmanTeamInformation(
            @ApiParam(required = true,name = "informationId",value = "informationId")@RequestParam(required = false,value = "informationId")Integer informationId,
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(informationId.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String ids = MD5Utils.parseToken(token);
        if(ids==null){
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
        String[] id={informationId.toString()};
        UseredRanksInformation useredRanksInformation = new UseredRanksInformation();
        useredRanksInformation.setUserId(usered.getId());
        useredRanksInformation.setUploaderType(0);
        useredRanksInformation.setType(1);
        List<UseredRanksInformation> list = useredRanksInformationService.selectByT(useredRanksInformation);
        Integer number = getCraftsmanTeamSizePeopleNumber(usered.getTeamSize());
        if(list.size()>number){//如果上传的工人信息数大于应有的数量，删除本条信息，不修改工长的队伍信息状态
            Integer deleteStatus =useredRanksInformationService.deleteByPrimaryKey(id);
            if(deleteStatus>0){
                result.setState(200);
                result.setMessage("删除成功");
            }else{
                result.setState(400);
                result.setMessage("删除失败");
            }
        }else{//否则删除本条记录，修改工长的队伍信息状态
            Integer deleteStatus =useredRanksInformationService.deleteByPrimaryKey(id);
            if(deleteStatus>0) {
                usered.setTeamInformationStatus(1);
                useredService.updateByPrimaryKey(usered);
                result.setState(200);
                result.setMessage("删除成功");
            }else{
                result.setState(400);
                result.setMessage("删除失败");
            }
        }
        return result;
    }
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "上传工长图片",notes = "上传工长图片")
    @RequestMapping(value = "/addSingleCraftsmanPhoto",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addSingleCraftsmanPhoto(HttpServletRequest request,
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setMessage("参数为空");
            result.setState(500);
            return result;
        }
        Usered usered = useredService.findUserByToken(token);
        if(usered==null){
            result.setState(600);
            result.setMessage("用户为空");
            return result;
        }
        try {
            String url =uploads(request);
            if(url.isEmpty()){
                result.setState(400);
                result.setMessage("上传失败");
                return result;
            }
            UseredPhoto useredPhoto = new UseredPhoto();
            useredPhoto.setImg(url);
            useredPhoto.setUserId(usered.getId());
            Integer ids =useredPhotoService.insert(useredPhoto);
            if(ids>0){
                result.setState(200);
                result.setMessage("成功");
                return result;
            }else {
                result.setState(800);
                result.setMessage("失败");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "获取工长相册",notes = "获取工长相册")
    @RequestMapping(value = "/getCraftsmanPhotoList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanPhotoList(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }

        String ids = MD5Utils.parseToken(token);
        if(ids==null){
            result.setState(1000);
            result.setMessage("登入失效");
            return result;
        }
        Usered usered = useredService.findUserByToken(token);
        UseredPhoto useredPhoto = new UseredPhoto();
        useredPhoto.setUserId(usered.getId());
        List<UseredPhoto> list = useredPhotoService.selectByT(useredPhoto);
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(list);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "删除工长上传的照片",notes = "删除工长上传的照片")
    @RequestMapping(value = "/removeCraftsmanTeamInformationPhoto",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData removeCraftsmanTeamInformationPhoto(
            @ApiParam(required = true,name = "photoId",value = "photoId")@RequestParam(required = false,value = "photoId")String[] photoId,
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,value = "token")String token
    ){
        ResponseData result = new ResponseData();

        if(StringUtils.isEmpty(photoId.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }

        String ids = MD5Utils.parseToken(token);
        if(ids==null){
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
        Integer status =useredPhotoService.deleteByPrimaryKey(photoId);
        if(status>0){
            result.setState(200);
            result.setMessage("成功");
        }else{
            result.setState(400);
            result.setMessage("失败");
        }
        return result;
    }




    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "组建队伍信息",notes = "组建队伍信息")
    @RequestMapping(value = "/setupAContingent",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData setupAContingent(HttpServletRequest request,@RequestBody JSONObject obj){
        ResponseData result = new ResponseData();
        String string = obj.toJSONString();
        JSONObject json = JSON.parseObject(string);
        String ranksInformation =json.getString("ranksInformation");
        String token =json.getString("token");
        String teamSize =json.getString("teamSize");

        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(teamSize)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }

        String ids = MD5Utils.parseToken(token);
        if(ids==null){
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
        Integer userId = usered.getId();
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
        List<WorkerInformation>list =JsonUtils.jsonToList(ranksInformation, WorkerInformation.class);
        if (list.size()>=userNumber){
            for(WorkerInformation workerInformation:list) {
                String workerType = workerInformation.getWorkType();
                UseredRanksInformation useredRanksInformation = new UseredRanksInformation();
                useredRanksInformation.setUserId(userId);
                useredRanksInformation.setName(workerInformation.getName());
                useredRanksInformation.setHometown(workerInformation.getHometown());
                useredRanksInformation.setCard(workerInformation.getIdCard());
                useredRanksInformation.setEmploymentTime(workerInformation.getEmploymentTime());
                useredRanksInformation.setProfessionalCategory(workerType);
                useredRanksInformation.setStatus(getCategoryNumbers(workerType));
                useredRanksInformationService.insert(useredRanksInformation);
            }
            result.setState(200);
            result.setMessage("成功");
        }else{
            result.setState(400);
            result.setMessage("失败");
        }
        return result;
    }







    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "普通用户上传工人信息入住工匠库",notes = "普通用户上传工人信息入住工匠库")
    @RequestMapping(value = "/addWorkersBank",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addWorksBank(
            @ApiParam(required= true,name = "token",value = "token")@RequestParam(required = false,value = "token") String token,
            @ApiParam(required = true,name = "name",value = "name")@RequestParam(required = false,value = "name") String name,
            @ApiParam(required = true,name="hometown",value = "hometown")@RequestParam(required = false,value = "hometown") String hometown,
            @ApiParam(required = true,name = "idCard",value = "idCard")@RequestParam(required = false,value = "idCard") String idCard,
            @ApiParam(required = true,name = "employmentTime",value = "employmentTime")@RequestParam(required = false,value = "employmentTime") Integer employmentTime,
            @ApiParam(required = true,name = "type",value = "type")@RequestParam(required = false,value = "type")String type
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(name)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(hometown)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(idCard)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(employmentTime.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(type)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String ids =MD5Utils.parseToken(token);
        if(ids==null){
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
        UseredRanksInformation ranksInformation = new UseredRanksInformation();
        ranksInformation.setUserId(usered.getId());
        ranksInformation.setName(name);
        ranksInformation.setHometown(hometown);
        ranksInformation.setCard(idCard);
        ranksInformation.setEmploymentTime(employmentTime);
        ranksInformation.setType(0);
        ranksInformation.setProfessionalCategory(type);
        ranksInformation.setUploaderType(1);
        ranksInformation.setStatus(getCategoryNumbers(type));
        Integer id = useredRanksInformationService.insert(ranksInformation);
        if(id>0){
            result.setState(200);
            result.setMessage("成功");
        }else{
            result.setState(400);
            result.setMessage("失败");
        }
        return result;
    }

    public int getCategoryNumbers(String name){
        Integer number =0;
        if(name.equals("瓦工")){
            number=1;
        }else if (name.equals("木工")){
            number=2;
        }else if (name.equals("油漆工")){
            number=3;
        }else if (name.equals("水暖工")){
            number=4;
        }else if (name.equals("电工")){
            number=5;
        }else if (name.equals("幕墙工")){
            number=6;
        }else if (name.equals("焊工")){
            number=7;
        }else if (name.equals("防水工")){
            number=8;
        }else if (name.equals("拆旧工")){
            number=9;
        }else if (name.equals("模板工")){
            number=10;
        }else if (name.equals("架子工")){
            number=11;
        }else if (name.equals("钢筋工")){
            number=12;
        }else if (name.equals("钣金工")){
            number=13;
        }else if (name.equals("杂工")){
            number=14;
        }else{
            number=15;
        }
        return number;
    }


    public int addSlaveTableData(Integer userId,String[] userLabel,String[] userExperience,String[] workType){
        UseredLabel userLabeled = new UseredLabel();
        userLabeled.setUserId(userId);
        List<UseredLabel> list = useredLabelService.selectByT(userLabeled);
        if(list.size()>0){
            for (UseredLabel useredLabel :list){
                String[] ids={useredLabel.getId().toString()};
                useredLabelService.deleteByPrimaryKey(ids);
            }

        }
        boolean number = true;
        for(String string:userLabel){
            UseredLabel useredLabel = new UseredLabel();
            useredLabel.setLabel(string);
            useredLabel.setUserId(userId);
            Integer a =useredLabelService.insert(useredLabel);
            if(a>0){
                number=true;
            }else{
                number=false;
            }
        }
        UseredExperience userExperienced = new UseredExperience();
        userExperienced.setUserId(userId);
        List<UseredExperience> userExperienceList = useredExperienceService.selectByT(userExperienced);
        if (userExperienceList.size()>0){
            for (UseredExperience userExperiences :userExperienceList) {
                String[] ids = {userExperiences.getId().toString()};
                useredExperienceService.deleteByPrimaryKey(ids);
            }
        }
        for (String experience:userExperience){
            String[] result = experience.split("-");
            UseredExperience useredExperience = new UseredExperience();
            useredExperience.setUserId(userId);
            useredExperience.setStartTime(result[0]);
            useredExperience.setEndTime(result[1]);
            useredExperience.setProfession(result[2]);//工种
            useredExperience.setRank(result[3]);//职级
            Integer b = useredExperienceService.insert(useredExperience);
            if(b>0){
                number=true;
            }else{
                number=false;
            }
        }
        UseredWorkType userWorkTypes = new UseredWorkType();
        userWorkTypes.setUserId(userId);
        List<UseredWorkType> list1 = useredWorkTypeService.selectByT(userWorkTypes);
        if(list1.size()>0){
            for(UseredWorkType userWorkTyped:list1){
                String[] ids = {userWorkTyped.getId().toString()};
                useredWorkTypeService.deleteByPrimaryKey(ids);
            }
        }
        for (String type:workType){
            UseredWorkType useredWorkType = new UseredWorkType();
            useredWorkType.setUserId(userId);
            useredWorkType.setProfession(type);
            Integer c =useredWorkTypeService.insert(useredWorkType);
            if(c>0){
                number=true;
            }else{
                number=false;
            }
        }
        if(number==true){
            return 1;
        }else{
            return 0;
        }
    }

    public int addUpDataSlaveTableData(Integer userId,String[] userLabel,String[] userExperience,String[] workType){
            boolean status =useredService.updateByUserIds(userId);
            if(status){
                int a =addSlaveTableData(userId,userLabel,userExperience,workType);
                if(a==1){
                    return 1;
                }else{
                    return 0;
                }
            }else{
                return 0;
            }

    }



    public String teamInformationName(Integer number){
        String teamInformationStatus= "";
        switch (number){
            case 1:
                teamInformationStatus="完成工长认证,即可组建您的队伍";
                break;
            case 2:
                teamInformationStatus="您的队伍审核未通过,请修改后重新提交";
                break;
            case 3:
                teamInformationStatus="您的队伍信息已提交,将尽快审核";
                break;
            case 4:
                teamInformationStatus="您的队伍审核已通过,快来上传业绩吧";
                break;
            default:
                System.out.println("什么也不是");
                break;
        }
        return teamInformationStatus;
    }

    public Integer getNumber(Integer id){
        CraftsmanAchievement craftsmanAchievement = new CraftsmanAchievement();
        craftsmanAchievement.setStatus(2);
        craftsmanAchievement.setUseredId(id);
        List<CraftsmanAchievement> list = craftsmanAchievementService.selectByT(craftsmanAchievement);
        return list.size();
    }

    //获取工长标签，队伍工种，从业经历
    public Map<String, List> getAllListData(Integer id){
        Map<String,List> map = new HashMap<>();
        List<String> userLabel = new ArrayList<>();
        List<String> workType = new ArrayList<>();
        List<UseredExperience> useredExperienceList = new ArrayList<>();
        UseredLabel useredLabel = new UseredLabel();
        useredLabel.setUserId(id);
        List<UseredLabel>userLabelList = useredLabelService.selectByT(useredLabel);
        if(userLabelList.size()>0) {
            for (UseredLabel userLabel1 : userLabelList) {
                userLabel.add(userLabel1.getLabel());
            }
        }
        map.put("userLabel",userLabel);

        UseredWorkType useredWorkType = new UseredWorkType();
        useredWorkType.setUserId(id);
        List<UseredWorkType> userWorkTypeList = useredWorkTypeService.selectByT(useredWorkType);
        if(userWorkTypeList.size()>0){
            for(UseredWorkType useredWorkType1:userWorkTypeList){
                workType.add(useredWorkType1.getProfession());
            }
        }
        map.put("userWorkType",workType);

        UseredExperience useredExperience = new UseredExperience();
        useredExperience.setUserId(id);
        List<UseredExperience> list = useredExperienceService.selectByT(useredExperience);
        if(list.size()>0){
            for(UseredExperience useredExperience1:list){
                useredExperienceList.add(useredExperience1);
            }
        }
        map.put("userExperienceList",useredExperienceList);
        return map;
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

    public String uploads(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String url="";
        // 获取文件
        MultipartFile file = multipartRequest.getFile("file");
        Setting setting = settingService.findOneByKey("system");
        //将json字符串转为对象
        SystemConfig config= JsonUtils.jsonToPojo(setting.getV(),SystemConfig.class);
        //将json字符串转为对象

        CloudStorageConfig cfg=new CloudStorageConfig();
        //本地
        if(config.getUpload()==0)
        {
            String path = request.getSession().getServletContext().getRealPath("uploads");

            String fileName = UploadUtil.uploadFile(file,path);

            url =("http://" + request.getServerName()+ ":"+ request.getServerPort()+ "/"+ request.getContextPath()  +"/uploads/"+fileName);
            return url;

        }
        else if(config.getUpload()==1) //七牛
        {
            Setting qiniusStting = settingService.findOneByKey("qiniu");
            //将json字符串转为对象
            QiniuConfig qiniuConfig=JsonUtils.jsonToPojo(qiniusStting.getV(),QiniuConfig.class);


            cfg.setType(1);
            cfg.setQiniuAccessKey(qiniuConfig.getAccesskey());
            cfg.setQiniuSecretKey(qiniuConfig.getSecretkey());
            cfg.setQiniuBucketName(qiniuConfig.getBucketname());
            cfg.setQiniuDomain(qiniuConfig.getDomain());

            OSSFactory.cloudStorageConfig=cfg;

            url = OSSFactory.build().upload(file.getBytes());
            return url;
        }
        return url;


    }
}
