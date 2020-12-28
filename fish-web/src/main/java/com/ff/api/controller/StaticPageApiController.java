package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.StringUtils;
import com.ff.shop.model.StaticPage;
import com.ff.shop.service.StaticPageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/staticPage")
public class StaticPageApiController extends BaseController {

    @Reference
    private StaticPageService staticPageService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取静态页面",notes="获取静态页面")
    @RequestMapping(value="/getData",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getData(
            @ApiParam(required = true,name = "type",value = "type")@RequestParam(required = false,value = "type")Integer type
    ){
        ResponseData result = new ResponseData();
        Map map = new HashMap();
        if (StringUtils.isEmpty(type.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        StaticPage staticPage = staticPageService.findByPrimaryKey("1");
        if(type==1){//信用评价
            map.put("creditEvaluation",staticPage.getCreditEvaluation());
        }else if(type==2){
            map.put("consultingService",staticPage.getConsultingService());
        }else{
            map.put("aboutUs",staticPage.getAboutUs());
            map.put("customerService",staticPage.getCustomerService());
            map.put("workmates",staticPage.getWorkmates());
        }
        result.setDatas(map);
        result.setState(200);
        result.setMessage("成功");
        return result;
    }
}
