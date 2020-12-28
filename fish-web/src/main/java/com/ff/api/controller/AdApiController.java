package com.ff.api.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.ad.model.Ad;
import com.ff.ad.service.AdService;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.shop.model.Goods;
import com.ff.shop.model.GoodsAlbum;
import com.ff.shop.model.GoodsCate;
import com.ff.shop.model.GoodsReview;
import com.ff.shop.service.GoodsAlbumService;
import com.ff.shop.service.GoodsCateService;
import com.ff.shop.service.GoodsReviewService;
import com.ff.shop.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Api(value="广告接口",description="广告接口")
@Controller
@RequestMapping(value="/api/ad")
public class AdApiController extends BaseController {
    @Reference
    private AdService adService;



    @ApiOperation(value="获取广告",notes="获取广告")
    @RequestMapping(value="/getAdList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getAdList(HttpServletRequest request, HttpServletResponse response, HttpSession sessio
         ){

        ResponseData result = new ResponseData();
        result.setState(200);
        Ad ad=new Ad();
        ad.setStatus(1);
        result.setDatas( adService.selectByT(ad));
        result.setMessage("");
        return result;
    }





}
