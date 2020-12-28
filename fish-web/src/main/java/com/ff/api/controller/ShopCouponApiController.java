package com.ff.api.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.IpAddress;
import com.ff.common.util.JsonUtils;
import com.ff.common.util.OrderUtils;
import com.ff.order.model.ex.Cart;
import com.ff.shop.model.*;
import com.ff.shop.model.bo.*;
import com.ff.shop.service.*;
import com.ff.users.model.Users;
import com.ff.users.model.UsersAccount;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.service.UsersAccountService;
import com.ff.users.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;


@Api(value="商城代金券接口",description="商城代金券接口")
@Controller
@RequestMapping(value="/api/shopCoupon")
public class ShopCouponApiController extends BaseController {
    @Reference
    private ShopCouponService shopCouponService;
    @Reference
    private ShopCouponLogsService shopCouponLogsService;
    @Reference
    private ShopService shopService;
    @Reference
    private UsersService usersService;


    @Reference
    private UsersAccountService usersAccountService;
    @Reference
    ShopCouponOrderService shopCouponOrderService;

//    @ApiOperation(value="获取商家代金券",notes="获取商家代金券")
//    @RequestMapping(value="/getShopCouponList",method= RequestMethod.GET)
//    @ResponseBody
//    public ResponseData getShopCouponList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
//                                         @ApiParam(required=true,name="shopId",value="shopId")@RequestParam(value="shopId",required=false)Integer shopId
//    ){
//
//
//
//
//        ResponseData result = new ResponseData();
//        result.setState(200);
//        Page<ShopCoupon> page = getPage(request);
//        EntityWrapper<ShopCoupon> ew=new EntityWrapper();
//        ew.eq("shop_id",shopId);
//        ew.eq("status",1);
//        ew.orderBy("coupon_id",false);
//        Page<ShopCoupon> list=shopCouponService.selectPage(page,ew);
//        result.setDatas(list);
//        result.setMessage("");
//        return result;
//    }


    @ApiOperation(value="获取用户代金券",notes="获取用户代金券")
    @RequestMapping(value="/getUserShopCouponLogsList",method= RequestMethod.GET)
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
        Page<ShopCouponLogs> page = getPage(request);
        EntityWrapper<ShopCouponLogs> ew=new EntityWrapper();
        ew.eq("user_id",model.getUserId());
        ew.orderBy("log_id",false);
        Page<ShopCouponLogs> list=shopCouponLogsService.selectPage(page,ew);

        result.setDatas(list.getRecords());
        result.setMessage("");
        return result;
    }

    @ApiOperation(value="获取代金券",notes="获取代金券")
    @RequestMapping(value="/getCoupon",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getCoupon(HttpServletRequest request, HttpServletResponse response, HttpSession session,
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

        ShopCoupon shopCoupon=shopCouponService.findByPrimaryKey(couponId.toString());
        if(shopCoupon==null||shopCoupon.getStatus()==0){
            result.setState(500);
            result.setMessage("代金券不存在");
            return result;
        }

        Map<String,Object> map=new HashMap<>();
        map.put("shop", shopService.findByPrimaryKey(shopCoupon.getShopId().toString()));
        map.put("coupon",shopCoupon);
        result.setState(200);
        result.setDatas(map);
        return result;
    }



    @ApiOperation(value="创建快捷支付订单",notes="创建快捷支付订单")
    @RequestMapping(value="/createOrder",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData createOrder(HttpServletRequest request,
                                    @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                    @ApiParam(required=true,name="couponId",value="couponId")@RequestParam(value="couponId",required=false)Integer couponId,
                                    @ApiParam(required=true,name="payType",value="payType")@RequestParam(value="payType",required=false)Integer payType


    ){

        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        ShopCoupon shopCoupon=shopCouponService.findByPrimaryKey(couponId.toString());
        if(shopCoupon==null||shopCoupon.getStatus()==0){
            result.setState(500);
            result.setMessage("代金券不存在");
            return result;
        }
        ShopCouponLogs log=new ShopCouponLogs();
        log.setUserId(model.getUserId());
        log.setCouponId(couponId);
        ShopCouponLogs logs= shopCouponLogsService.findOne(log);
        if(logs!=null){
            result.setState(500);
            result.setMessage("您已经领取了此券");
            return result;
        }
        Integer count= shopCouponLogsService.selectCount(couponId);
        if(count>=shopCoupon.getCount()){
            result.setState(500);
            result.setMessage("此券已领完");
            return result;
        }

        Shop shop=shopService.findByPrimaryKey(shopCoupon.getShopId().toString());



        ShopCouponOrder order=new ShopCouponOrder();
        order.setOrderSn(OrderUtils.getOrderSN());
        order.setClosed(0);
        order.setCreateTime(new Date());
        order.setIsPay(0);
        order.setCouponId(couponId);
        order.setMoney(shopCoupon.getPrice());
        order.setTotalMoney(shopCoupon.getPrice());
        order.setPayType(payType);
        if(payType==1){
            UsersAccount usersAccount= usersAccountService.findByPrimaryKey(model.getUserId().toString());
            int integral= shopCoupon.getPrice().multiply(BigDecimal.valueOf(shop.getIntegral())).divide(BigDecimal.valueOf(100)).intValue();

            if(usersAccount!=null&&usersAccount.getPoints()>=integral){


                UsersAccountLog usersAccountLog=new UsersAccountLog();
                usersAccountLog.setUserId(model.getUserId());
                usersAccountLog.setExp(0);
                usersAccountLog.setBalance(new BigDecimal(0));
                usersAccountLog.setPoints(-integral);
                usersAccountLog.setCause("您在"+shop.getTitle()+"消费"+order.getMoney()+"元，使用积分:"+integral);
                usersAccountLog.setAdminId("0");
                Long time=new Date().getTime()/1000;
                usersAccountLog.setDateline(Integer.parseInt(time.toString()));
                usersAccountService.updateAccount(usersAccountLog);
                order.setIntegral(integral);
                order.setMoney(order.getMoney().subtract(BigDecimal.valueOf(integral)));
            }
        }
        else if(payType==2){//满减

            if(shopCoupon.getPrice().compareTo(shop.getMinMoney())>=0){
                order.setMoney(shopCoupon.getPrice().subtract(shop.getJianMoney()));
            }
        }
        order.setShopId(shop.getShopId());
        order.setUserId(model.getUserId());
        if(shopCouponOrderService.insert(order)>0){
            result.setState(200);
            result.setDatas(order.getOrderSn());
            result.setMessage("订单创建成功");
        }
        else{
            result.setState(500);
            result.setMessage("创建失败");
        }

        return result;
    }

    @ApiOperation(value="获取快捷支付订单",notes="获取快捷支付订单")
    @RequestMapping(value="/getOrder",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getOrder(HttpServletRequest request,
                                 @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                 @ApiParam(required=true,name="orderSN",value="orderSN")@RequestParam(value="orderSN",required=false)String orderSN


    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        ShopCouponOrder map=new ShopCouponOrder();
        map.setOrderSn(orderSN);
        map.setUserId(model.getUserId());
        ShopCouponOrder order= shopCouponOrderService.findOne(map);
        if(order==null){
            result.setState(500);
            result.setMessage("订单不存在");
        }
        else{
            result.setState(200);
            result.setDatas(order);
            result.setMessage("");
        }
        return result;
    }

    @ApiOperation(value="支付",notes="支付")
    @RequestMapping(value="/payment",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData payment(HttpServletRequest request,
                                @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                @ApiParam(required=true,name="orderSN",value="orderSN")@RequestParam(value="orderSN",required=false)String orderSN,
                                @ApiParam(required=true,name="paymentId",value="paymentId")@RequestParam(value="paymentId",required=false)Integer paymentId


    ){

        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        ShopCouponOrder map=new ShopCouponOrder();
        map.setOrderSn(orderSN);
        map.setUserId(model.getUserId());
        ShopCouponOrder order= shopCouponOrderService.findOne(map);

        if(order==null){
            result.setState(500);
            result.setMessage("订单不存在");
        }
        else{

            order.setPaymentMethod(paymentId);
            shopCouponOrderService.updateByPrimaryKey(order);
            result.setState(200);
            result.setMessage("");
        }
        return result;
    }




    @ApiOperation(value="领取代金券",notes="领取代金券")
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

        ShopCoupon shopCoupon=shopCouponService.findByPrimaryKey(couponId.toString());
        if(shopCoupon==null||shopCoupon.getStatus()==0){
            result.setState(500);
            result.setMessage("代金券不存在");
            return result;
        }
        ShopCouponLogs log=new ShopCouponLogs();
        log.setUserId(model.getUserId());
        log.setCouponId(couponId);
        ShopCouponLogs logs= shopCouponLogsService.findOne(log);
        if(logs!=null){
            result.setState(500);
            result.setMessage("您已经领取了此券");
            return result;
        }
        Integer count= shopCouponLogsService.selectCount(couponId);
        if(count>=shopCoupon.getCount()){
            result.setState(500);
            result.setMessage("此券已领完");
            return result;
        }
        log.setCode(OrderUtils.getOrderSN());
        log.setCreateTime(new Date());
        log.setLimitTime(shopCoupon.getEndTime());
        log.setPrice(shopCoupon.getPrice());
        log.setStatus(0);
        log.setShopId(shopCoupon.getShopId());
        try
        {
            log.setShopName( shopService.findByPrimaryKey(shopCoupon.getShopId().toString()).getTitle());
        }
        catch (Exception e){

        }
        shopCouponLogsService.insert(log);
        shopCouponService.updateCount(couponId);
        result.setState(200);
        result.setMessage("领取成功");
        return result;
    }


    @ApiOperation(value="通过CODE抵扣代金券",notes="通过CODE抵扣代金券")
    @RequestMapping(value="/offsetCouponByCode",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData offsetCouponByCode(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                  @ApiParam(required=true,name="code",value="code")@RequestParam(value="code",required=false)String code,
                                  @ApiParam(required=true,name="shopId",value="shopId")@RequestParam(value="shopId",required=false)Integer shopId
    ){
        ResponseData result = new ResponseData();
        ShopCouponLogs log=new ShopCouponLogs();
        log.setCode(code);
        log.setShopId(shopId);
        ShopCouponLogs logs= shopCouponLogsService.findOne(log);
        if(logs==null){
            result.setState(500);
            result.setMessage("此券不存在");
            return result;
        }

        if(logs.getLimitTime().getTime()<new Date().getTime()){
            result.setState(500);
            result.setMessage("此券已过期");
            return result;
        }
        if(logs.getStatus()!=0){
            result.setState(500);
            result.setMessage("此券已使用");
            return result;
        }
        logs.setUseTime(new Date());
        logs.setStatus(1);
        shopCouponLogsService.updateByPrimaryKey(logs);

        result.setState(200);
        result.setMessage("操作成功");
        return result;
    }

    @ApiOperation(value="获取商家代金券",notes="获取商家代金券")
    @RequestMapping(value="/getShopCouponLogsList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getShopCouponLogsList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                              @ApiParam(required=true,name="shopId",value="shopId")@RequestParam(value="shopId",required=false)Integer shopId
    ){
        ResponseData result = new ResponseData();

        result.setState(200);
        Page<ShopCouponLogs> page = getPage(request);
        EntityWrapper<ShopCouponLogs> ew=new EntityWrapper();
        ew.eq("shop_id",shopId);
        ew.eq("status",1);
        ew.orderBy("log_id",false);
        Page<ShopCouponLogs> list=shopCouponLogsService.selectPage(page,ew);
        result.setDatas(list.getRecords());
        result.setMessage("");
        return result;
    }




}
