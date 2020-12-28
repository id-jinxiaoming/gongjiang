package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.StringUtils;
import com.ff.shop.model.EmploymentInformation;
import com.ff.shop.service.EmploymentInformationService;
import com.ff.user.service.UseredService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/employmentInformation")
public class EmploymentInformationApiController extends BaseController {

    @Reference
    private EmploymentInformationService employmentInformationService;

    @Reference
    private UseredService useredService;

    //page 1 row:10
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取用工信息列表",notes="获取用工信息列表")
    @RequestMapping(value="/getEmploymentInformationList",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getEmploymentInformationList(HttpServletRequest request){

        ResponseData result = new ResponseData();

        Map map1 = new HashMap();
        List listed = new ArrayList();
        Page<EmploymentInformation> page =getPage(request);
        EntityWrapper<EmploymentInformation> ew = new EntityWrapper<>();
        ew.orderBy("id",false);
        Page<EmploymentInformation> list = employmentInformationService.selectPage(page,ew);
        if(list.getSize()>0){
            for(EmploymentInformation employmentInformation:list.getRecords()){
                Map map = new HashMap();
                map.put("province",employmentInformation.getProvince());
                map.put("id",employmentInformation.getId());
                map.put("projectName",employmentInformation.getProjectName());
                map.put("createTime",employmentInformation.getCreateTime());
                listed.add(map);
            }
        }

        map1.put("data",listed);
        map1.put("pages",list.getPages());
        map1.put("total",list.getTotal());
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map1);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取用工信息详情",notes="获取用工信息详情")
    @RequestMapping(value="/getEmploymentInformationDetail",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getEmploymentInformationDetail(
            @ApiParam(required = true,name = "id",value = "id")@RequestParam(required = false,value = "id")Integer id
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(id.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        EmploymentInformation employmentInformation = employmentInformationService.findByPrimaryKey(id.toString());
        if(employmentInformation==null){
            result.setState(100);
            result.setMessage("数据为空");
            return result;
        }
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(employmentInformation);
        return result;
    }
}
