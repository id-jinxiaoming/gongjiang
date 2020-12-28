package com.ff.api.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.OrderUtils;
import com.ff.coupon.model.Coupon;
import com.ff.coupon.model.CouponLogs;
import com.ff.coupon.service.CouponLogsService;
import com.ff.coupon.service.CouponService;
import com.ff.shop.model.ShopCoupon;
import com.ff.shop.model.ShopCouponLogs;
import com.ff.users.model.Users;
import com.ff.users.service.UsersService;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Api(value="优惠券接口",description="优惠券接口")
@Controller
@RequestMapping(value="/api/coupon")
public class CouponApiController  extends BaseController {
    @Reference
    CouponService couponService;
    @Reference
    CouponLogsService couponLogsService;
    @Reference
    private UsersService usersService;

    @ApiOperation(value="获取优惠券",notes="获取优惠券")
    @RequestMapping(value="/getCouponList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getUserShopCouponLogsList(HttpServletRequest request, HttpServletResponse response, HttpSession session
    ){
        ResponseData result = new ResponseData();
        result.setState(200);
        result.setDatas(couponService.selectCoupon());
        result.setMessage("");
        return result;
    }

    @ApiOperation(value="领取优惠券",notes="领取优惠券")
    @RequestMapping(value="/pullCoupon",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData pullCoupon(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                   @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                   @ApiParam(required=true,name="couponId",value="couponId")@RequestParam(value="couponId",required=false)Integer couponId
    ){

        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        Coupon coupon=couponService.findByPrimaryKey(couponId.toString());
        if(coupon==null||coupon.getStatus()==0){
            result.setState(500);
            result.setMessage("优惠券不存在");
            return result;
        }
        CouponLogs log=new CouponLogs();
        log.setUserId(model.getUserId());
        log.setCouponId(couponId);
        CouponLogs logs= couponLogsService.findOne(log);
        if(logs!=null){
            result.setState(500);
            result.setMessage("您已经领取了此券");
            return result;
        }
        Integer count= couponLogsService.selectCount(couponId);
        if(count>=coupon.getCount()){
            result.setState(500);
            result.setMessage("此券已领完");
            return result;
        }
        log.setCreateTime(new Date());
        log.setLimitTime(coupon.getEndTime());
        log.setPrice(coupon.getPrice());
        log.setMinPrice(coupon.getMinPrice());
        log.setStatus(0);
        log.setType(coupon.getType());

        couponLogsService.insert(log);
        coupon.setCount(coupon.getCount()+1);
        couponService.updateByPrimaryKey(coupon);
        result.setState(200);
        result.setMessage("领取成功");
        return result;
    }

    @ApiOperation(value="获取用户优惠券",notes="获取用户优惠券")
    @RequestMapping(value="/getUserCouponLogsList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getUserShopCouponLogsList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                                  @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        result.setState(200);
        Page<CouponLogs> page = getPage(request);
        EntityWrapper<CouponLogs> ew=new EntityWrapper();
        ew.eq("user_id",model.getUserId());
        ew.orderBy("log_id",false);
        Page<CouponLogs> list=couponLogsService.selectPage(page,ew);
        result.setDatas(list.getRecords());
        result.setMessage("");
        return result;
    }
    @ApiOperation(value="获取用户可用优惠券",notes="获取用户可用优惠券")
    @RequestMapping(value="/getEnableCouponLogsList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getEnableCouponLogsList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                                  @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                                  @ApiParam(required=true,name="amount",value="amount")@RequestParam(value="amount",required=false)BigDecimal amount
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        result.setState(200);
        List<CouponLogs> list=couponLogsService.selectEnableCoupon(amount,model.getUserId());
        result.setDatas(list);
        result.setMessage("");
        return result;
    }
}
