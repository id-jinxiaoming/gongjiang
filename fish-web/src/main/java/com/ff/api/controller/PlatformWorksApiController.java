package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.StringUtils;
import com.ff.shop.model.IngenuityImg;
import com.ff.shop.model.IngenuityUsered;
import com.ff.shop.model.IngenuityWorks;
import com.ff.shop.model.bo.IngenuityWorksBO;
import com.ff.shop.model.bo.IngenuityWorksDetailBO;
import com.ff.shop.service.IngenuityImgService;
import com.ff.shop.service.IngenuityUseredService;
import com.ff.shop.service.IngenuityWorksService;
import com.ff.user.model.Usered;
import com.ff.user.model.UseredWorkType;
import com.ff.user.model.bo.Craftsman;
import com.ff.user.service.UseredService;
import com.ff.user.service.UseredWorkTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping("/api/platformWorks")
@Controller
public class PlatformWorksApiController extends BaseController {

    @Reference
    private IngenuityWorksService ingenuityWorksService;

    @Reference
    private IngenuityUseredService ingenuityUseredService;

    @Reference
    private IngenuityImgService ingenuityImgService;

    @Reference
    private UseredService useredService;

    @Reference
    private UseredWorkTypeService useredWorkTypeService;

    //type:1:酒店/办公2:学校/医院3:商贸/住宅 4:展览/展示 5:幕墙/机电
    //page 1 row 10
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取平台业绩列表",notes="获取平台业绩列表")
    @RequestMapping(value="/getCraftsmanList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            @ApiParam(required = true,name = "type",value = "type")@RequestParam(required = false,value = "type") Integer type) {

        ResponseData result = new ResponseData();

        Page<IngenuityWorks> page = getPage(request);
        Map<String ,Object> map = new HashMap<>();
        EntityWrapper<IngenuityWorks> ew = new EntityWrapper<>();
        ew.eq("type",type);
        ew.orderBy("id",false);

        Page<IngenuityWorks> list = ingenuityWorksService.selectPage(page,ew);
        List<IngenuityWorksBO> list1 = new ArrayList<>();
        for (IngenuityWorks ingenuityWorks:list.getRecords()){
            Integer id = ingenuityWorks.getId();

            IngenuityWorksBO ingenuityWorksBO = new IngenuityWorksBO();
            ingenuityWorksBO.setIngenuityId(id);
            ingenuityWorksBO.setEngineeringCost(ingenuityWorks.getEngineeringCost());
            ingenuityWorksBO.setProjectName(ingenuityWorks.getProjectName());
            ingenuityWorksBO.setList(getIngenuityWorksCraftsman(id));
            //ingenuityWorksBO.setImgList(getIngenuityWokrsImgList(id,request));
            ingenuityWorksBO.setImgList(getIngenuityWokrsImgList(id));
            list1.add(ingenuityWorksBO);
        }
        map.put("pages", list.getPages());
        map.put("total", list.getTotal());
        map.put("data",list1);

        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }

    public List<String> getIngenuityWorksCraftsman(Integer id){
        List<String> list = new ArrayList<>();
        IngenuityUsered ingenuityUsered = new IngenuityUsered();
        ingenuityUsered.setIngenuityId(id);
        List<IngenuityUsered> listst = ingenuityUseredService.selectByT(ingenuityUsered);
        if(listst.size()>0){
            for (IngenuityUsered ingenuityUsered1:listst){
                Usered usered = useredService.findByPrimaryKey(ingenuityUsered1.getUseredId().toString());
                list.add(usered.getRealName());
            }
        }
        return list;
    }

   /* public List<String> getIngenuityWokrsImgList(Integer id,HttpServletRequest request){
        List<String> list = new ArrayList<>();
        Page<IngenuityImg> page = getPage(request);

        EntityWrapper<IngenuityImg> ew = new EntityWrapper<>();
        ew.eq("reservation_id",id);
        ew.orderBy("id",true);

        Page<IngenuityImg> page1 = ingenuityImgService.selectPage(page,ew);
        if(page1.getSize()>0){
            for(IngenuityImg ingenuityImg:page1.getRecords().subList(0,3)){
                list.add(ingenuityImg.getImg());
            }
        }
        return list;
    }
*/
    public List<String> getIngenuityWokrsImgList(Integer id){
        List<String> list = new ArrayList<>();

        IngenuityImg ingenuityImg = new IngenuityImg();
        ingenuityImg.setReservationId(id);
        List<IngenuityImg> list1 = ingenuityImgService.selectByT(ingenuityImg);


        if(list1.size()>0){
            Collections.sort(list1, new Comparator<IngenuityImg>(){
                //排序
                public int compare(IngenuityImg p1, IngenuityImg p2) {
                    //按照Student的age进行升序排列
                    if(p1.getId() > p2.getId()){
                        return 1;
                    }
                    if(p1.getId() == p2.getId()){
                        return 0;
                    }
                    return -1;
                }
            });
            for(IngenuityImg img:list1.subList(0,3)){
                list.add(img.getImg());
            }
        }
        return list;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取平台业绩详情",notes="获取平台业绩详情")
    @RequestMapping(value="/getIngenuityWorksDetails",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanList(
            @ApiParam(required = true,name = "id",value = "id")@RequestParam(required = false,value = "id") Integer id) {
        ResponseData result = new ResponseData();
        if (StringUtils.isEmpty(id.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        IngenuityWorks ingenuityWorks = ingenuityWorksService.findByPrimaryKey(id.toString());
        IngenuityWorksDetailBO ingenuityWorksDetailBO = new IngenuityWorksDetailBO();
        if(ingenuityWorks!=null){
           ingenuityWorksDetailBO.setProjectName(ingenuityWorks.getProjectName());
           ingenuityWorksDetailBO.setLocation(ingenuityWorks.getLocation());
           ingenuityWorksDetailBO.setCompletionTime(ingenuityWorks.getCompletionTime());
           ingenuityWorksDetailBO.setEngineeringCost(ingenuityWorks.getEngineeringCost());
           ingenuityWorksDetailBO.setProjectDescription(ingenuityWorks.getProjectDescription());
           ingenuityWorksDetailBO.setImgList(getIngenuityWorksDetailImgList(id));
           ingenuityWorksDetailBO.setList(getIngenuityDetailCraftsmanList(id));
        }
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(ingenuityWorksDetailBO);
        return result;
    }

        //根据匠心作品id获取匠心作品页图片列表
        public List<String> getIngenuityWorksDetailImgList(Integer id){
            List<String> imgList = new ArrayList<>();
            IngenuityImg ingenuityImg = new IngenuityImg();
            ingenuityImg.setReservationId(id);
            List<IngenuityImg> list = ingenuityImgService.selectByT(ingenuityImg);
            if(list.size()>0){
                for(IngenuityImg ingenuityImg1:list){
                    imgList.add(ingenuityImg1.getImg());
                }
            }
            return imgList;
        }

        //根据匠心作品id获取匠心作品详情页中得主力工长列表
        public List<Craftsman> getIngenuityDetailCraftsmanList(Integer id){
         List<Craftsman> list = new ArrayList<>();
         IngenuityUsered ingenuityUsered = new IngenuityUsered();
         ingenuityUsered.setIngenuityId(id);
         List<IngenuityUsered> ingenuityUseredList = ingenuityUseredService.selectByT(ingenuityUsered);
         if(ingenuityUseredList.size()>0){
             for(IngenuityUsered ingenuityUsered1:ingenuityUseredList){
                 Usered usered = useredService.findByPrimaryKey(ingenuityUsered1.getUseredId().toString());
                 Craftsman craftsman = new Craftsman();
                 craftsman.setId(usered.getId());
                 craftsman.setToken(usered.getToken());
                 craftsman.setPhoto(usered.getAvatar());
                 craftsman.setRealName(usered.getRealName());
                 craftsman.setTeamSize(usered.getTeamSize());
                 craftsman.setEmploymentTime(usered.getEmploymentTime());
                 craftsman.setList(getUseredWorkTypeList(usered.getId()));
                 list.add(craftsman);
             }

         }
         return list;
        }

        public List<UseredWorkType> getUseredWorkTypeList(Integer id){
            UseredWorkType useredWorkType = new UseredWorkType();
            useredWorkType.setUserId(id);
            List<UseredWorkType> list = useredWorkTypeService.selectByT(useredWorkType);
            return list;
        }
}
