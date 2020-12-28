package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.MD5Utils;
import com.ff.common.util.StringUtils;
import com.ff.shop.model.CraftsmanAchievement;
import com.ff.shop.model.CraftsmanAchievementImg;
import com.ff.shop.model.CraftsmanAchievementTimes;
import com.ff.shop.model.bo.CraftsmanAchievementBO;
import com.ff.shop.model.bo.CraftsmanAchievementSingleBO;
import com.ff.shop.service.CraftsmanAchievementImgService;
import com.ff.shop.service.CraftsmanAchievementService;
import com.ff.shop.service.CraftsmanAchievementTimesService;
import com.ff.user.model.Usered;
import com.ff.user.service.UseredService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
@RequestMapping("/api/craftsmanAchievement")
public class CraftsmanAchievementApiController extends BaseController {

    @Reference
    private CraftsmanAchievementService craftsmanAchievementService;

    @Reference
    private CraftsmanAchievementImgService craftsmanAchievementImgService;

    @Reference
    private CraftsmanAchievementTimesService craftsmanAchievementTimesService;

    @Reference
    private UseredService useredService;
    //status:123
    //page 1
    //rows 10
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="根据状态获取工匠业绩列表",notes="根据状态获取工匠业绩列表")
    @RequestMapping(value="/getCraftsmanAchievementByType",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanList(HttpServletRequest request, HttpServletResponse response,
                @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                @ApiParam(required=true,name="status",value="状态")@RequestParam(value="status",required=false)Integer status) {
        ResponseData result = new ResponseData();
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(status.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        Usered usered = useredService.findUserByToken(token);

        Page<CraftsmanAchievement> page = getPage(request);
        EntityWrapper<CraftsmanAchievement> ew = new EntityWrapper<>();
        ew.eq("status",status);
        ew.eq("usered_id",usered.getId());
        ew.orderBy("id",false);

        Page<CraftsmanAchievement> craftsmanAchievementList = craftsmanAchievementService.selectPage(page,ew);
        List<CraftsmanAchievementBO> list = new ArrayList<>();
        if(craftsmanAchievementList.getSize()>0){
            for (CraftsmanAchievement craftsmanAchievement:craftsmanAchievementList.getRecords()){
                Integer id = craftsmanAchievement.getId();
                CraftsmanAchievementBO craftsmanAchievementBO = new CraftsmanAchievementBO();
                craftsmanAchievementBO.setId(id);
                craftsmanAchievementBO.setCreateTime(craftsmanAchievement.getCreateTime());
                craftsmanAchievementBO.setProjectName(craftsmanAchievement.getProjectName());
                craftsmanAchievementBO.setList(getCraftsmanAchievementImgList(id));
                craftsmanAchievementBO.setYear(craftsmanAchievement.getYear());
                craftsmanAchievementBO.setMonth(craftsmanAchievement.getMonth());
                list.add(craftsmanAchievementBO);
            }
        }
        map.put("data",list);
        map.put("pages",craftsmanAchievementList.getPages());
        map.put("total",craftsmanAchievementList.getTotal());
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return  result;
    }
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "点击添加业绩按钮之前判断队伍信息状态",notes = "点击添加业绩按钮之前判断队伍信息状态")
    @RequestMapping(value = "/beforeAddCraftsmanAchievement",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData beforeAddCraftsmanAchievement(
            @ApiParam(required = true,name = "token",value = "token")@RequestParam(required = false,name = "token")String token
    ){
        ResponseData result = new ResponseData();

        if (StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String id = MD5Utils.parseToken(token);
        if(id==null) {
            result.setState(1000);
            result.setMessage("登入失效");
            return result;
        }
        Usered model=useredService.findUserByToken(token);
        if(model==null) {
            result.setState(1000);
            result.setMessage("未登入");
            return result;
        }
        Integer teamInformationStatus = model.getTeamInformationStatus();
        if(teamInformationStatus!=4) {
            switch (teamInformationStatus) {
                case 1:
                    result.setState(600);
                    result.setMessage("工长队伍信息未组建");
                    break;
                case 2:
                    result.setState(700);
                    result.setMessage("队伍信息审核未通过");
                    break;
                case 3:
                    result.setState(800);
                    result.setMessage("队伍信息审核中");
                    break;
                default:
                    System.out.println("出错了");
            }
            return result;
        }

        result.setState(200);
        result.setMessage("成功");
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "添加业绩",notes = "添加业绩")
    @RequestMapping(value = "/addCraftsmanAchievement",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addCraftsmanAchievement(HttpServletRequest request,
                                          @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                          @ApiParam(required=true,name="craftsmanAchievement",value="craftsmanAchievement") CraftsmanAchievement craftsmanAchievement,
                                          @ApiParam(required=true,name="img",value="img")@RequestParam(value="img",required=false)String[] img
    ) {
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(img.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String id = MD5Utils.parseToken(token);
        if(id==null) {
            result.setState(1000);
            result.setMessage("登入失效");
            return result;
        }
        Usered model=useredService.findUserByToken(token);
        if(model==null)
        {
            result.setState(1000);
            result.setMessage("未登入");
            return result;
        }


        Integer useredId = model.getId();
        CraftsmanAchievement craftsmanAchievement1 = new CraftsmanAchievement();
        craftsmanAchievement1.setProjectName(craftsmanAchievement.getProjectName());
        craftsmanAchievement1.setCreateTime(new Date());
        craftsmanAchievement1.setProvince(craftsmanAchievement.getProvince());
        craftsmanAchievement1.setCity(craftsmanAchievement.getCity());
        craftsmanAchievement1.setType(craftsmanAchievement.getType());
        craftsmanAchievement1.setDescription(craftsmanAchievement.getDescription());
        craftsmanAchievement1.setUseredId(useredId);
        craftsmanAchievement1.setStatus(1);
        craftsmanAchievement1.setYear(craftsmanAchievement.getYear());
        craftsmanAchievement1.setMonth(craftsmanAchievement.getMonth());
        Integer ids =craftsmanAchievementService.insert(craftsmanAchievement1);
        if (ids>0){
            if(img!=null && img.length>0){
                for(int i=0; i<img.length;i++){
                    CraftsmanAchievementImg craftsmanAchievementImg = new CraftsmanAchievementImg();
                    craftsmanAchievementImg.setAchievementId(ids);
                    craftsmanAchievementImg.setImg(img[i]);
                    craftsmanAchievementImgService.insert(craftsmanAchievementImg);
                }
                addCraftsmanAchievement(ids,useredId,craftsmanAchievement.getYear());
                result.setState(200);
                result.setMessage("上传成功");
            }else{
                result.setState(500);
                result.setMessage("上传失败");
            }
            result.setState(200);
            result.setMessage("上传成功");
        }else{
            result.setState(500);
            result.setMessage("上传失败");
        }
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "获取工长上传业绩详情",notes = "获取工长上传业绩详情")
    @RequestMapping(value = "/getCraftsmanAchievementDetails",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editCraftsmanAchievement(
            @ApiParam(required = true,name = "id",value = "id")@RequestParam(required = false,value = "id")Integer id
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(id.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        Map map = new HashMap();
        CraftsmanAchievement craftsmanAchievement =craftsmanAchievementService.findByPrimaryKey(id.toString());
        CraftsmanAchievementImg craftsmanAchievementImg = new CraftsmanAchievementImg();
        craftsmanAchievementImg.setAchievementId(id);
        List<CraftsmanAchievementImg> craftsmanAchievementImgList = craftsmanAchievementImgService.selectByT(craftsmanAchievementImg);
        map.put("craftsmanAchievement",craftsmanAchievement);
        map.put("img",craftsmanAchievementImgList);
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }



    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "修改业绩",notes = "修改业绩")
    @RequestMapping(value = "/editCraftsmanAchievement",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editCraftsmanAchievement(HttpServletRequest request,
                                                @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                                @ApiParam(required=true,name="craftsmanAchievement",value="craftsmanAchievement") CraftsmanAchievement craftsmanAchievement,
                                                @ApiParam(required=true,name="img",value="img")@RequestParam(value="img",required=false)String[] img
    ) {
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        if(StringUtils.isEmpty(img.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        String id = MD5Utils.parseToken(token);
        if(id==null) {
            result.setState(1000);
            result.setMessage("登入失效");
            return result;
        }
        Usered model=useredService.findUserByToken(token);
        Integer useredId = model.getId();
        craftsmanAchievement.setUpdateTime(new Date());
        craftsmanAchievement.setStatus(1);
        Integer ids =craftsmanAchievementService.updateByPrimaryKey(craftsmanAchievement);
        if(ids>0){
            craftsmanAchievementImgService.deleteById(craftsmanAchievement.getId());
            if(img!=null && img.length>0){
                for(int i=0; i<img.length;i++){
                    CraftsmanAchievementImg craftsmanAchievementImg = new CraftsmanAchievementImg();
                    craftsmanAchievementImg.setAchievementId(craftsmanAchievement.getId());
                    craftsmanAchievementImg.setImg(img[i]);
                    craftsmanAchievementImgService.insert(craftsmanAchievementImg);
                }
                addCraftsmanAchievement(craftsmanAchievement.getId(),useredId,craftsmanAchievement.getYear());
                result.setState(200);
                result.setMessage("修改成功");
            }else{
                result.setState(500);
                result.setMessage("修改失败");
            }
            result.setState(200);
            result.setMessage("修改成功");
        }else{
            result.setState(500);
            result.setMessage("修改失败");
        }
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value = "删除业绩",notes = "删除业绩")
    @RequestMapping(value = "/removeCraftsmanAchievement",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData removeCraftsmanAchievement(
               @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)String[] id){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(id.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }

        String str3 = StringUtils.join(id);
        CraftsmanAchievementImg craftsmanAchievementImg = new CraftsmanAchievementImg();
        craftsmanAchievementImg.setAchievementId(Integer.parseInt(str3));
        List<CraftsmanAchievementImg> craftsmanAchievementImgList = craftsmanAchievementImgService.selectByT(craftsmanAchievementImg);
        if(craftsmanAchievementImgList.size()>0){
            for(CraftsmanAchievementImg craftsmanAchievementImg1:craftsmanAchievementImgList){
                String[] ids={craftsmanAchievementImg1.getId().toString()};
                craftsmanAchievementImgService.deleteByPrimaryKey(ids);
            }
        }

        CraftsmanAchievementTimes craftsmanAchievementTimes = new CraftsmanAchievementTimes();
        craftsmanAchievementTimes.setAchievementId(Integer.parseInt(str3));
        List<CraftsmanAchievementTimes> list = craftsmanAchievementTimesService.selectByT(craftsmanAchievementTimes);
        if(list.size()>0){
            for (CraftsmanAchievementTimes craftsmanAchievementTimes1:list){
                String[] ids={craftsmanAchievementTimes1.getId().toString()};
                craftsmanAchievementTimesService.deleteByPrimaryKey(ids);
            }
        }


        Integer ids = craftsmanAchievementService.deleteByPrimaryKey(id);

        if(ids>0){
            result.setState(200);
            result.setMessage("删除成功");
        }else{
            result.setState(500);
            result.setMessage("删除失败");
        }
        return result;
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取工长上传审核通过的业绩数量",notes="获取工长上传审核通过的业绩数量")
    @RequestMapping(value="/getCraftsmanAchievementNumber",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanAchievementNumber(HttpServletRequest request, HttpServletResponse response,
                                         @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }

        Usered usered = useredService.findUserByToken(token);

        if(usered==null){
            result.setState(500);
            result.setMessage("数据为空");
            return result;
        }
        CraftsmanAchievement craftsmanAchievement = new CraftsmanAchievement();
        craftsmanAchievement.setStatus(2);
        craftsmanAchievement.setUseredId(usered.getId());
        List<CraftsmanAchievement> list = craftsmanAchievementService.selectByT(craftsmanAchievement);
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(list.size());
        return result;
    }

    //根据年份区分，获取工长主页下部的业绩列表
    //page 1 rows 10
    //token 唯一标识
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取工长上传审核通过的业绩列表",notes="获取工长上传审核通过的业绩列表")
    @RequestMapping(value="/getCraftsmanAchievementList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanAchievementList(HttpServletRequest request, HttpServletResponse response,
           @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token) {
        ResponseData result = new ResponseData();
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(token)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        Usered usered = useredService.findUserByToken(token);
        if(usered==null){
            result.setState(500);
            result.setMessage("数据为空");
            return result;
        }
        CraftsmanAchievementTimes craftsmanAchievementTimes = new CraftsmanAchievementTimes();
        craftsmanAchievementTimes.setUserId(usered.getId());
        List<CraftsmanAchievementTimes> craftsmanAchievementTimesList = craftsmanAchievementTimesService.selectByT(craftsmanAchievementTimes);
        if(craftsmanAchievementTimesList.size()==0){
            result.setState(200);
            result.setMessage("成功");
            result.setDatas(map);
            return result;
        }
        for(CraftsmanAchievementTimes cat:craftsmanAchievementTimesList){
            Integer year = cat.getName();
            List<CraftsmanAchievement> list = getListDataByIdAndYear(usered.getId(),year);
            List<CraftsmanAchievementBO> list1 =getList(list);
            Collections.sort(list1, new Comparator<CraftsmanAchievementBO>() {
                @Override
                public int compare(CraftsmanAchievementBO o1, CraftsmanAchievementBO o2) {
                    if(o1.getMonth() < o2.getMonth()){
                        return 1;
                    }
                    if(o1.getMonth() == o2.getMonth()){
                        return 0;
                    }
                    return -1;
                }
            });
            map.put(year.toString(),list1);
        }
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="根据id获取电子名片制作业绩",notes="根据id获取电子名片制作业绩")
    @RequestMapping(value="/getListByCraftsmanId",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getListByCraftsmanId(
            @ApiParam(required = true,name = "craftsmanId",value = "craftsmanId")@RequestParam(required = false,value = "craftsmanId")String[] craftsmanId
    ){
        ResponseData result = new ResponseData();
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(craftsmanId.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }

        List<CraftsmanAchievementTimes> listed = new ArrayList<>();

        for (String id :craftsmanId){
            CraftsmanAchievementTimes craftsmanAchievementTime = new CraftsmanAchievementTimes();
            craftsmanAchievementTime.setAchievementId(Integer.parseInt(id));
            List<CraftsmanAchievementTimes> craftsmanAchievementTimesList = craftsmanAchievementTimesService.selectByT(craftsmanAchievementTime);
            if(craftsmanAchievementTimesList.size()>0){
                listed.add(craftsmanAchievementTimesList.get(0));
            }
        }
        if(listed.size()==0){
            result.setState(200);
            result.setMessage("成功");
            result.setDatas(map);
            return result;
        }
        for(CraftsmanAchievementTimes cat:listed){
            Integer year = cat.getName();
            List<CraftsmanAchievement> list = getListDataByIdAndYear(cat.getUserId(),year);
            List<CraftsmanAchievementBO> list1 =getList(list);
            Collections.sort(list1, new Comparator<CraftsmanAchievementBO>() {
                @Override
                public int compare(CraftsmanAchievementBO o1, CraftsmanAchievementBO o2) {
                    if(o1.getMonth() < o2.getMonth()){
                        return 1;
                    }
                    if(o1.getMonth() == o2.getMonth()){
                        return 0;
                    }
                    return -1;
                }
            });
            map.put(year.toString(),list1);
        }
        result.setMessage("成功");
        result.setDatas(map);
        result.setState(200);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="根据id获取业绩详情",notes="根据id获取业绩详情")
    @RequestMapping(value="/getDetailByAchievementId",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getDetailByAchievementId(
                    @ApiParam(required=true,name="achievementId",value="achievementId")@RequestParam(value="achievementId",required=false)Integer achievementId) {
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(achievementId.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        CraftsmanAchievement craftsmanAchievement = craftsmanAchievementService.findByPrimaryKey(achievementId.toString());
        Integer year = craftsmanAchievement.getYear();
        Integer month = craftsmanAchievement.getMonth();
        String time = year.toString()+"年"+month.toString();
        Integer status = craftsmanAchievement.getType();
        String type ="";
        switch (status){
            case 1:
                type="装饰装修";
                break;
            case 2:
                type="机电安装";
                break;
            case 3:
                type="幕墙工程";
                break;
            case 4:
                type="土建施工";
                break;
            case 5:
                type="给排水工程";
                break;
                default:
                    System.out.println("啥也不是");
        }
        CraftsmanAchievementSingleBO craftsmanAchievementSingleBO = new CraftsmanAchievementSingleBO();
        craftsmanAchievementSingleBO.setId(craftsmanAchievement.getId());
        craftsmanAchievementSingleBO.setCompletionTime(time);
        craftsmanAchievementSingleBO.setDescription(craftsmanAchievement.getDescription());
        craftsmanAchievementSingleBO.setProjectName(craftsmanAchievement.getProjectName());
        craftsmanAchievementSingleBO.setType(type);
        craftsmanAchievementSingleBO.setLocation(craftsmanAchievement.getProvince()+craftsmanAchievement.getCity());
        craftsmanAchievementSingleBO.setList(getCraftsmanAchievementImgList(achievementId));
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(craftsmanAchievementSingleBO);
        return result;
    }


    //根据List集合封装返回数据
    public List<CraftsmanAchievementBO> getList(List<CraftsmanAchievement> list){
        List<CraftsmanAchievementBO> list1 = new ArrayList<>();
        for(CraftsmanAchievement craftsmanAchievement:list){
            CraftsmanAchievementBO craftsmanAchievementBO = new CraftsmanAchievementBO();
            craftsmanAchievementBO.setId(craftsmanAchievement.getId());
            craftsmanAchievementBO.setCreateTime(craftsmanAchievement.getCreateTime());
            craftsmanAchievementBO.setProjectName(craftsmanAchievement.getProjectName());
            craftsmanAchievementBO.setList(getCraftsmanAchievementImgList(craftsmanAchievement.getId()));
            craftsmanAchievementBO.setYear(craftsmanAchievement.getYear());
            craftsmanAchievementBO.setMonth(craftsmanAchievement.getMonth());
            list1.add(craftsmanAchievementBO);
        }
        return list1;
    }

    //根据用户Id 与年份获取列表
    public List<CraftsmanAchievement> getListDataByIdAndYear(Integer id,Integer year){
        CraftsmanAchievement craftsmanAchievement = new CraftsmanAchievement();
        craftsmanAchievement.setUseredId(id);
        craftsmanAchievement.setStatus(2);
        craftsmanAchievement.setYear(year);
        List<CraftsmanAchievement> list = craftsmanAchievementService.selectByT(craftsmanAchievement);
        return list;
    }



    //根据useredID与业绩ID保存区分年得时间
    public void addCraftsmanAchievement(Integer id,Integer useredId,Integer year){
        CraftsmanAchievementTimes craftsmanAchievementTimes = new CraftsmanAchievementTimes();
        craftsmanAchievementTimes.setAchievementId(id);
        craftsmanAchievementTimes.setUserId(useredId);
        craftsmanAchievementTimes.setName(year);
        List<CraftsmanAchievementTimes> list = craftsmanAchievementTimesService.selectByT(craftsmanAchievementTimes);
        if(list.size()==0){
            CraftsmanAchievementTimes craftsmanAchievementTimes1 = new CraftsmanAchievementTimes();
            craftsmanAchievementTimes1.setAchievementId(id);
            craftsmanAchievementTimes1.setUserId(useredId);
            craftsmanAchievementTimes1.setName(year);
            craftsmanAchievementTimesService.insert(craftsmanAchievementTimes1);
        }
    }


    //根据工匠上传业绩id获取业绩图片
    public List<String> getCraftsmanAchievementImgList(Integer id){
        List<String> imgList = new ArrayList<>();
        CraftsmanAchievementImg craftsmanAchievementImg = new CraftsmanAchievementImg();
        craftsmanAchievementImg.setAchievementId(id);
        List<CraftsmanAchievementImg> list = craftsmanAchievementImgService.selectByT(craftsmanAchievementImg);
        if(list.size()>0){
            for(CraftsmanAchievementImg caList:list){
                imgList.add(caList.getImg());
            }
        }
        return imgList;
    }
}
