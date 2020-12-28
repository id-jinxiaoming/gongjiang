package com.ff.api.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.IpAddress;
import com.ff.common.util.OrderUtils;
import com.ff.rechage.model.Rechage;
import com.ff.rechage.model.RechageOrder;
import com.ff.rechage.service.RechageOrderService;
import com.ff.rechage.service.RechageService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Api(value="充值接口",description="充值接口")
@Controller
@RequestMapping(value="/api/rechage")
public class RechageApiController extends BaseController {

    @Reference
    private RechageService rechageService;
    @Reference
    private UsersService usersService;
    @Reference
    private RechageOrderService rechageOrderService;

    @ApiOperation(value="获取充值套餐",notes="获取充值套餐")
    @RequestMapping(value="/getRechageList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getRechageList(HttpServletRequest request){

        ResponseData result = new ResponseData();
        result.setState(200);
        result.setDatas(rechageService.selectAll());

        result.setMessage("");
        return result;
    }


    @ApiOperation(value="生成订单",notes="生成订单")
    @RequestMapping(value="/createOrder",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData createOrder(
            HttpServletRequest request,
            @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="rechageId",value="套餐ID")@RequestParam(value="rechageId",required=false)Integer rechageId
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        Rechage rechage= rechageService.findByPrimaryKey(rechageId.toString());

       if(rechage==null){

            result.setState(500);
            result.setMessage("套餐不存在");
            return result;
        }
        else{
            RechageOrder order=new RechageOrder();
            order.setCreateTime(new Date());
            order.setCoupon(rechage.getCoupon());
            order.setIsPay(0);
            order.setMoney(rechage.getMoney());
            order.setRechageId(rechage.getRechageId());
            order.setTitle(rechage.getTitle());
            order.setUserId(model.getUserId());
            order.setOrderSn(OrderUtils.getOrderSN());
            order.setCreateIp(IpAddress.getIp(request));
            Integer id=rechageOrderService.insert(order);
            if(id>0)
            {
                result.setState(200);
                result.setDatas(order.getOrderSn());
                result.setMessage("订单创建成功");
                return result;

            }
            else
            {
                result.setState(500);
                result.setMessage("操作失败");
                return result;
            }


        }
    }

    @ApiOperation(value="获取订单数据",notes="获取订单数据")
    @RequestMapping(value="/getOrder",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getUsersPackage(
            @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="orderSN",value="订单ID")@RequestParam(value="orderSN",required=false)String orderSN
    ){

        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }


        RechageOrder mapRechageOrder=new RechageOrder();
        mapRechageOrder.setOrderSn(orderSN);
        RechageOrder order= rechageOrderService.findOne(mapRechageOrder);
        if(order==null){

            result.setState(500);
            result.setMessage("订单不存在");
            return result;
        }
        else if(order.getUserId()!=model.getUserId()){

            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }

        Map<String,Object> map=new HashMap<>();
        map.put("order",order);
        result.setState(200);
        result.setDatas(map);
        result.setMessage("");
        return result;
    }

    @ApiOperation(value="支付提交",notes="支付提交")
    @RequestMapping(value="/payment",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData payment(
            HttpServletRequest request,
            @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="orderSN",value="订单ID")@RequestParam(value="orderSN",required=false)String orderSN,
            @ApiParam(required=true,name="type",value="支付类型")@RequestParam(value="type",required=false)Integer type
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        RechageOrder mapRechageOrder=new RechageOrder();
        mapRechageOrder.setOrderSn(orderSN);
        RechageOrder order= rechageOrderService.findOne(mapRechageOrder);
        if(order==null){

            result.setState(500);
            result.setMessage("订单不存在");
            return result;
        }
        else if(order.getUserId()!=model.getUserId()){

            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }

        order.setPaymentMethod(type);
        rechageOrderService.updateByPrimaryKey(order);
        result.setState(200);
        result.setDatas(null);
        result.setMessage("提交成功");
        return result;
    }


    @ApiOperation(value="获取用户订单列表",notes="获取用户订单列表")
    @RequestMapping(value="/getOrderList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getOrderList(HttpServletRequest request,
                                        @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        Page<RechageOrder> p = getPage(request);
        EntityWrapper<RechageOrder> ew=new EntityWrapper<>();
        ew.orderBy("create_time",false);
        RechageOrder orderModel=new RechageOrder();
        orderModel.setUserId(model.getUserId());
        ew.setEntity(orderModel);
        Page<RechageOrder> data= rechageOrderService.selectPage(p,ew);
        result.setState(200);
        result.setDatas(data);
        result.setMessage("");
        return result;
    }
    @ApiOperation(value="删除用户订单",notes="删除用户订单")
    @RequestMapping(value="/delOrder",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData delOrder(HttpServletRequest request,
                                        @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
                                        @ApiParam(required=true,name="id",value="订单ID")@RequestParam(value="id",required=false)Integer id
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        RechageOrder order= rechageOrderService.findByPrimaryKey(id.toString());
       if(order==null){

            result.setState(500);
            result.setMessage("订单不存在");
            return result;
        }
        else if(order.getUserId()!=model.getUserId()){

            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }
        String[] ids={id.toString()};
        if(rechageOrderService.deleteByPrimaryKey(ids)>0)
        {
            result.setState(200);
            result.setDatas(null);
            result.setMessage("操作成功");
        }
        else{
            result.setState(500);
            result.setDatas(null);
            result.setMessage("操作失败");
        }

        return result;
    }





}
