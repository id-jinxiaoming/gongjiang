package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.ad.model.RotationChart;
import com.ff.ad.service.RotationChartService;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;


@Api(value = "轮播图获取接口",description = "轮播图获取接口")
@Controller
@RequestMapping("/api/rotationChart")
public class CarouselPictureController extends BaseController {

    @Reference
    private RotationChartService rotationChartService;


    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="轮播图",notes="轮播图")
    @RequestMapping(value="/getList",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getList() {
        ResponseData result = new ResponseData();
        RotationChart rotationChart = new RotationChart();
        rotationChart.setStatus(1);
        List<RotationChart> list=rotationChartService.selectByT(rotationChart);
        if(null == list || list.size() ==0 ){
            List<RotationChart> list1 = new ArrayList<>();
            result.setState(200);
            result.setMessage("数据为空");
            result.setDatas(list1);
        }else{
            result.setState(200);
            result.setDatas(list);
            result.setMessage("成功");
        }
        return  result;
    }
}
