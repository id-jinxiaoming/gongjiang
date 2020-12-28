package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.SignUtils;
import com.ff.common.util.StringUtils;
import com.ff.shop.model.AccessToken;
import com.ff.shop.service.WxTokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/api/wxToken")
@Controller
public class WxTokenApiController extends BaseController {

    @Reference
    private WxTokenService wxTokenService;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "获取签名",notes = "获取签名")
    @RequestMapping(value = "/jsApiTicket",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData jsApiTicket(HttpServletRequest request,
           @ApiParam(required = true,name = "url",value = "url")@RequestParam(required = false,value = "url")String url
    ) {

        ResponseData result = new ResponseData();
        if(StringUtils.isEmpty(url)){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        AccessToken accessToken = wxTokenService.findByPrimaryKey("1");
        if(accessToken.getJsapiTicket().isEmpty()){
            result.setState(500);
            result.setMessage("参数为空");
            return result;
        }
        Map map =SignUtils.sign(accessToken.getJsapiTicket(),url);
        result.setState(200);
        result.setDatas(map);
        result.setMessage("成功");
        return result;
    }


}
