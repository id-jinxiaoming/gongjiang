package com.ff.api.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.shop.model.Area;
import com.ff.shop.model.City;
import com.ff.shop.service.AreaService;
import com.ff.shop.service.CityService;
import com.ff.shop.service.ProvinceService;
import com.ff.users.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Api(value="市区接口",description="省市区接口")
@Controller
@RequestMapping(value="/api/consignee")
@CrossOrigin(value = "*",maxAge = 3600)
public class ConsigneeApiController extends BaseController{

    @Reference
    private ProvinceService provinceService;

    @Reference
    private CityService cityService;

    @Reference
    private AreaService areaService;

    @Reference
    private UsersConsigneeService usersConsigneeService;

    @ApiOperation(value="获取省",notes="获取省")
    @RequestMapping(value="/getProvince",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getProvince(HttpServletRequest request, HttpServletResponse response
    ){


        ResponseData result = new ResponseData();
        result.setState(200);
        Map<String,Object> data=new HashMap<>();



        data.put("province", provinceService.selectAll());

        result.setDatas(data);
        result.setMessage("");
        return result;
    }


    @ApiOperation(value="获取市",notes="获取市")
    @RequestMapping(value="/getCity",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getCity(HttpServletRequest request, HttpServletResponse response,
                                    @ApiParam(required=true,name="provinceId",value="provinceId")@RequestParam(value="provinceId",required=false)String provinceId
    ){

        ResponseData result = new ResponseData();
        result.setState(200);
        Map<String,Object> data=new HashMap<>();

        City map=new City();
        map.setProvinceId(provinceId);
        data.put("city", cityService.selectByT(map));

        result.setDatas(data);
        result.setMessage("");
        return result;
    }

    @ApiOperation(value="获取区域",notes="获取区域")
    @RequestMapping(value="/getArea",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getArea(HttpServletRequest request, HttpServletResponse response,
                                @ApiParam(required=true,name="cityId",value="cityId")@RequestParam(value="cityId",required=false)String cityId
    ){


        ResponseData result = new ResponseData();
        result.setState(200);
        Map<String,Object> data=new HashMap<>();

        Area map=new Area();
        map.setCityId(cityId);
        data.put("area", areaService.selectByT(map));

        result.setDatas(data);
        result.setMessage("");
        return result;
    }









}
