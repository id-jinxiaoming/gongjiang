package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.StringUtils;
import com.ff.shop.model.TextManagement;
import com.ff.shop.service.TextManagementService;
import com.ff.user.model.Usered;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/craftsmanInformation")
@Controller
public class CraftsmanInformationController extends BaseController {

    @Reference
    private TextManagementService textManagementService;


    /**
     *
     * page 1
     * rows 10
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取工匠资讯列表",notes="获取工匠资讯列表")
    @RequestMapping(value="/getCraftsmanInformationList",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanInformationList(HttpServletRequest request){

        ResponseData result = new ResponseData();
        Map map = new HashMap();
        Page<TextManagement> page = getPage(request);
        EntityWrapper<TextManagement> ew = new EntityWrapper<>();
        ew.orderBy("id",false);
        Page<TextManagement> list = textManagementService.selectPage(page,ew);

        map.put("pages", list.getPages());
        map.put("size", list.getSize());
        map.put("total", list.getTotal());
        map.put("data",list.getRecords());
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(map);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @ApiOperation(value="获取工匠资讯详情",notes="获取工匠资讯详情")
    @RequestMapping(value="/getCraftsmanInformationDetail",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getCraftsmanInformationDetail(
            @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id
    ){
        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(id.toString())){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        TextManagement textManagement =textManagementService.findByPrimaryKey(id.toString());
        result.setState(200);
        result.setMessage("成功");
        result.setDatas(textManagement);
        return result;
    }


}
