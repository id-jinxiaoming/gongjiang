package com.ff.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.Kuaidi100Data;
import com.ff.common.model.ResponseData;
import com.ff.common.model.SystemConfig;
import com.ff.common.util.JsonUtils;
import com.ff.common.util.Kuaidi100;
import com.ff.common.util.OrderUtils;
import com.ff.common.util.StringUtils;
import com.ff.coupon.model.CouponLogs;
import com.ff.coupon.service.CouponLogsService;
import com.ff.order.model.*;
import com.ff.order.model.bo.OrderBO;
import com.ff.order.model.bo.OrderGoodsBO;
import com.ff.order.model.bo.OrderShippingBO;
import com.ff.order.model.ex.Cart;
import com.ff.order.model.ex.Optional;
import com.ff.order.service.*;
import com.ff.shop.model.*;
import com.ff.shop.service.*;
import com.ff.system.model.Setting;
import com.ff.system.service.SettingService;
import com.ff.users.model.Users;
import com.ff.users.model.UsersAccount;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.model.UsersConsignee;
import com.ff.users.service.UsersAccountService;
import com.ff.users.service.UsersConsigneeService;
import com.ff.users.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.Kernel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Api(value="商城订单接口",description="商城订单接口")
@Controller
@RequestMapping(value="/api/order")
public class OrderApiController extends BaseController {
    @Reference
    private UsersService usersService;
    @Reference
    private GoodsService goodsService;

    @Reference
    private UsersConsigneeService usersConsigneeService;
    @Reference
    private GoodsOptionalService goodsOptionalService;
    @Reference
    private ShippingMethodService shippingMethodService;

    @Reference
    private OrderService orderService;

    @Reference
    private OrderGoodsService orderGoodsService;

    @Reference
    private ProvinceService provinceService;

    @Reference
    private CityService cityService;

    @Reference
    private OrderConsigneeService orderConsigneeService;

    @Reference
    private OrderGoodsOptionalService orderGoodsOptionalService;

    @Reference
    private AreaService areaService;

    @Reference
    private GoodsReviewService goodsReviewService;


    @Reference
    private OrderShippingService orderShippingService;

    @Reference
    private SettingService settingService;

    @Reference
    private UsersAccountService usersAccountService;


    @Reference
    private GoodsSpecService goodsSpecService;

    @Reference
    private AftersalesService aftersalesService;

    @Reference
    private CouponLogsService couponLogsService;


    @ApiOperation(value="获取商城订单列表",notes="获取商城订单列表")
    @RequestMapping(value="/getOrderList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getOrderList(HttpServletRequest request,
                                     @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
                                     @ApiParam(required=true,name="status",value="状态")@RequestParam(value="status",required=false)Integer status
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        Page<Order> p = getPage(request);
        EntityWrapper<Order> ew=new EntityWrapper<>();
        ew.orderBy("created_date",false);
        Order orderModel=new Order();
        orderModel.setUserId(model.getUserId());
        if(status!=0){
            orderModel.setOrderStatus(status);
        }
        else
        {
            ew.notIn("order_status","0");
        }

        ew.setEntity(orderModel);

        Page<Order> data= orderService.selectPage(p,ew);

        List<OrderBO> orders=new ArrayList<>();
        for(Order o:data.getRecords()){
            OrderBO bo=new OrderBO();
            bo.setOrder(o);
            OrderGoods map=new OrderGoods();
            map.setOrderId(o.getOrderId());
            List<OrderGoods> orderGoods= orderGoodsService.selectByT(map);

            List<OrderGoodsBO> orderGoodsBOList=new ArrayList<>();
            boolean flag=false;
            for(OrderGoods temp:orderGoods){
                OrderGoodsOptional mapOpt=new OrderGoodsOptional();
                mapOpt.setMapId(temp.getId());
                List<OrderGoodsOptional> list=orderGoodsOptionalService.selectByT(mapOpt);
                OrderGoodsBO orderGoodsBO=new OrderGoodsBO();
                orderGoodsBO.setGoodsImage(temp.getGoodsImage());
                orderGoodsBO.setGoodsPrice(temp.getGoodsPrice());
                orderGoodsBO.setGoodsQty(temp.getGoodsQty());
                orderGoodsBO.setGoodsName(temp.getGoodsName());
                orderGoodsBO.setIsReviewed(temp.getIsReviewed());
                orderGoodsBO.setId(temp.getId());
                orderGoodsBO.setOrderId(temp.getOrderId());
                orderGoodsBO.setOrderGoodsOptionals(list);
                if(temp.getIsReviewed()==1){
                    flag=true;
                }
                orderGoodsBOList.add(orderGoodsBO);
            }
            bo.setOrderGoods(orderGoodsBOList );
            bo.setReview(flag);
            orders.add(bo);

        }


        result.setState(200);
        result.setDatas(orders);
        result.setMessage("");
        return result;
    }


    @ApiOperation(value="提交订单",notes="提交订单")
    @RequestMapping(value="/orderSubmit",method= RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseData orderSubmit(
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="cart",value="cart")@RequestParam(value="cart",required=false)String cart,
            @ApiParam(required=true,name="cid",value="cid")@RequestParam(value="cid",required=false)Integer cid,
            @ApiParam(required=true,name="shippingId",value="shippingId")@RequestParam(value="shippingId",required=false)Integer shippingId,
            @ApiParam(required=true,name="shippingId",value="couponLogId")@RequestParam(value="couponLogId",required=false)Integer couponLogId,
            @ApiParam(required=true,name="integral",value="integral")@RequestParam(value="integral",required=false)Boolean integral,
            @ApiParam(required=true,name="note",value="备注")@RequestParam(value="note",required=false)String note


    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        BigDecimal cartWeight=new BigDecimal(0);
        Integer cartNum=0;
        //检查购物车信息
        String newJson = StringEscapeUtils.unescapeHtml4(cart);
        List<Cart> list=  JsonUtils.jsonToList(newJson,Cart.class);
        BigDecimal total=new BigDecimal(0.00);
        Integer allInteger=0;
        Integer allRebateIntegral=0;
        if(list!=null&&list.size()>0){
            for (Integer i=0;i<list.size();i++ ) {

                Goods goods=goodsService.findByPrimaryKey(list.get(i).getGoodsId().toString());
                cartWeight=cartWeight.add(goods.getGoodsWeight());
                cartNum+=list.get(i).getNum();
                BigDecimal price=goods.getNowPrice();
                if(list.get(i).getOpt()!=null&&list.get(i).getOpt().size()>0){
                    String ids="";
                    Collections.sort(list.get(i).getOpt());
                    for( com.ff.order.model.ex.Optional o:list.get(i).getOpt()){
                        ids+=o.getOptId()+":";
                    }
                    if(!ids.equals("")){
                        GoodsSpec goodsSpecMap=new GoodsSpec();
                        goodsSpecMap.setGoodsId(list.get(i).getGoodsId());
                        goodsSpecMap.setKey(ids.substring(0,ids.length()-1));
                        GoodsSpec goodsSpec=goodsSpecService.findOne(goodsSpecMap);
                        price=goodsSpec.getPrice();
                    }
                }
                if(goods.getIntegral()!=null){
                    allInteger+=goods.getIntegral()*list.get(i).getNum();
                }

                if(goods.getRebateIntegral()!=null){
                    allRebateIntegral+=goods.getRebateIntegral()*list.get(i).getNum();
                }

                list.get(i).setPrice(price);
                total=total.add(price).multiply(BigDecimal.valueOf(list.get(i).getNum()));
                list.get(i).setTitle(goods.getGoodsName());
                list.get(i).setImage(goods.getGoodsImage());

            }
        }
        else{
            result.setState(500);
            result.setMessage("购物车商品数据不正确");
            return result;
        }

        //检查收件人信息
        UsersConsignee usersConsigneeMap=new UsersConsignee();
        usersConsigneeMap.setUserId(model.getUserId());
        usersConsigneeMap.setId(cid);
        UsersConsignee usersConsignee= usersConsigneeService.findOne(usersConsigneeMap);
        if (usersConsignee == null) {
            result.setState(500);
            result.setMessage("无法获取收件人地址信息");
            return result;

        }
        //检查配送方式
       ShippingMethod shippingMethod= shippingMethodService.findByPrimaryKey(shippingId.toString());
        if(shippingMethod==null){
            result.setState(500);
            result.setMessage("配送方式不存在");
            return result;
        }
        ResponseData shippingData=  shippingMethodService.getFreight(cid,shippingId,cartWeight,cartNum);
        if(shippingData.getState()!=200){
            result.setState(500);
            result.setMessage("无法获取运费");
            return result;
        }

        //创建订单
        Order order=new Order();
        order.setOrderId(OrderUtils.getOrderSN());
        order.setMemos(note);
        order.setCreatedDate(System.currentTimeMillis()/1000 );
        order.setGoodsAmount(total);
        order.setOrderStatus(1);
        order.setRebateIntegral(allRebateIntegral);

        order.setIntegral(0);
        //使用积分处理
        if(integral){

            UsersAccount usersAccount= usersAccountService.findByPrimaryKey(model.getUserId().toString());

            if(usersAccount!=null&&usersAccount.getPoints()>=allInteger){


                UsersAccountLog log=new UsersAccountLog();
                log.setUserId(model.getUserId());
                log.setExp(0);
                log.setBalance(new BigDecimal(0));
                log.setPoints(-allInteger);
                log.setCause("您在购物中使用积分:"+allInteger);
                log.setAdminId("0");
                Long time=new Date().getTime()/1000;
                log.setDateline(Integer.parseInt(time.toString()));
                usersAccountService.updateAccount(log);
                order.setIntegral(allInteger);
                //积分是否可抵扣运费处理
                Setting setting= settingService.findOneByKey("system");
                SystemConfig cfg= JsonUtils.jsonToPojo(setting.getV(), SystemConfig.class);
                //此处理为客户要求，两种处理效果一样

                if(cfg.getIsIntegralToFreight()==1){
                    order.setOrderAmount(total.add((BigDecimal) shippingData.getDatas()).subtract(BigDecimal.valueOf(allInteger)));
                }
                else{
                    order.setOrderAmount(total.subtract(BigDecimal.valueOf(allInteger).add((BigDecimal) shippingData.getDatas())));
                }

            }
            else{
                result.setState(500);
                result.setMessage("您的积分不足");
                return result;
            }


        }
        else {
            order.setOrderAmount(total.add((BigDecimal) shippingData.getDatas()));
        }

        //使用积分处理
        if(couponLogId>0){
           CouponLogs couponLogs= couponLogsService.findByPrimaryKey(couponLogId.toString());
           if(couponLogs==null||couponLogs.getStatus()!=0|| couponLogs.getMinPrice().compareTo(total)>0){
               result.setState(500);
               result.setMessage("此优惠券不可用");
               return result;
           }
            order.setOrderAmount(order.getOrderAmount().subtract(couponLogs.getPrice()));
            order.setCouponMoney(couponLogs.getPrice());
            //修改状态
            couponLogs.setStatus(1);
            couponLogs.setUseTime(new Date());
            couponLogsService.updateByPrimaryKey(couponLogs);

        }
        int r=order.getOrderAmount().compareTo(BigDecimal.valueOf(0)); //和0，Zero比较
        if(r==-1){
            order.setOrderAmount(BigDecimal.valueOf(0));
        }
        order.setPaymentMethod(1);
        order.setShippingAmount((BigDecimal) shippingData.getDatas());
        order.setUserId(model.getUserId());
        order.setShippingMethod(shippingId);

        if(!StringUtils.isEmpty(orderService.createOrder(order,list,usersConsignee))){
            result.setState(200);
            result.setDatas(order.getOrderId());
            result.setMessage("");
        }
        else{
            result.setState(500);
            result.setMessage("订单创建失败");

        }
        return result;
    }
    @ApiOperation(value="获取订单",notes="获取订单")
    @RequestMapping(value="/getOrder",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData payment(
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="orderId",value="orderId")@RequestParam(value="orderId",required=false)String orderId



    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        Order order= orderService.findOrder(orderId,model.getUserId());
        if(order!=null){
            result.setDatas(order);
            result.setState(200);
            result.setMessage("");
            return result;
        }
        else{
            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }



    }

    @ApiOperation(value="支付",notes="支付")
    @RequestMapping(value="/payment",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData payment(
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="orderId",value="orderId")@RequestParam(value="orderId",required=false)String orderId,
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

        Order order= orderService.findOrder(orderId,model.getUserId());
        if(order!=null&&order.getOrderStatus()==1){

            order.setPaymentMethod(paymentId);
            orderService.updateByPrimaryKey(order);
            result.setState(200);
            result.setMessage("");
            return result;
        }
        else{
            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }

    }

    @ApiOperation(value="取消订单",notes="取消订单")
    @RequestMapping(value="/cancelOrder",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData cancelOrder(
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="orderId",value="orderId")@RequestParam(value="orderId",required=false)String orderId



    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        Order order= orderService.findOrder(orderId,model.getUserId());
        if(order!=null&&order.getOrderStatus()==1){
            if(orderService.cancelOrder(orderId)){
                result.setState(200);
                result.setMessage("");
            }
            else
            {
                result.setState(500);
                result.setMessage("确认订单失败");
            }
            return result;
        }
        else{
            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }


    }


    @ApiOperation(value="确认订单",notes="确认订单")
    @RequestMapping(value="/deliveredOrder",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData deliveredOrder(
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="orderId",value="orderId")@RequestParam(value="orderId",required=false)String orderId
    ){


        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        Order order= orderService.findOrder(orderId,model.getUserId());
        if(order!=null&&order.getOrderStatus()==3){
            if(orderService.deliveredOrder(orderId)){
                result.setState(200);
                result.setMessage("");
            }
            else
            {
                result.setState(500);
                result.setMessage("确认订单失败");
            }
            return result;
        }
        else{
            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }

    }

    @ApiOperation(value="获取评价商品列表",notes="获取评价商品列表")
    @RequestMapping(value="/getOrderGoods",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getOrderGoods(HttpServletRequest request,
                                     @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
                                     @ApiParam(required=true,name="orderId",value="订单ID")@RequestParam(value="orderId",required=false)String orderId
    ){

        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        Order order= orderService.findOrder(orderId,model.getUserId());
        if(order!=null&&order.getOrderStatus()==4){
            OrderGoods orderGoodsModel=new OrderGoods();
            orderGoodsModel.setOrderId(orderId);
            orderGoodsModel.setIsReviewed(0);
            List<OrderGoods> lists= orderGoodsService.selectByT(orderGoodsModel);
            List<OrderGoodsBO> orderGoodsBOList=new ArrayList<>();
            for(OrderGoods temp:lists){
                OrderGoodsOptional mapOpt=new OrderGoodsOptional();
                mapOpt.setMapId(temp.getId());
                List<OrderGoodsOptional> list=orderGoodsOptionalService.selectByT(mapOpt);
                OrderGoodsBO orderGoodsBO=new OrderGoodsBO();
                orderGoodsBO.setGoodsId(temp.getGoodsId());
                orderGoodsBO.setGoodsImage(temp.getGoodsImage());
                orderGoodsBO.setGoodsPrice(temp.getGoodsPrice());
                orderGoodsBO.setGoodsQty(temp.getGoodsQty());
                orderGoodsBO.setGoodsName(temp.getGoodsName());
                orderGoodsBO.setIsReviewed(temp.getIsReviewed());
                orderGoodsBO.setId(temp.getId());
                orderGoodsBO.setOrderId(temp.getOrderId());
                orderGoodsBO.setOrderGoodsOptionals(list);

                orderGoodsBOList.add(orderGoodsBO);
            }
            result.setState(200);
            result.setDatas(orderGoodsBOList);
        }
        else{
            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }

        return result;
    }


    @ApiOperation(value="评价",notes="评价")
    @RequestMapping(value="/goodsReView",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData goodsReView(HttpServletRequest request,
                                      @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
                                      @ApiParam(required=true,name="orderId",value="订单ID")@RequestParam(value="orderId",required=false)String orderId,
                                      @ApiParam(required=true,name="goodsReViews",value="商品评价")@RequestParam(value="goodsReViews",required=false)String goodsReViews
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }


        Order order= orderService.findOrder(orderId,model.getUserId());
        if(order!=null&&order.getOrderStatus()==4){

            String newJson = StringEscapeUtils.unescapeHtml4(goodsReViews);
            List<GoodsReview> list=  JsonUtils.jsonToList(newJson,GoodsReview.class);
            return goodsReviewService.GoodsReview(list,orderId,model.getUserId());
        }
        else {
            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }


    }

    @ApiOperation(value="申请售后",notes="申请售后")
    @RequestMapping(value="/aftersales",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData aftersales(HttpServletRequest request,
                                    @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
                                    @ApiParam(required=true,name="orderId",value="订单ID")@RequestParam(value="orderId",required=false)String orderId,
                                    @ApiParam(required=true,name="aftersales",value="售后")@RequestParam(value="aftersales",required=false)String aftersales
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }


        Order order= orderService.findOrder(orderId,model.getUserId());
        if(order!=null&&order.getOrderStatus()==4){

            String newJson = StringEscapeUtils.unescapeHtml4(aftersales);
            List<Aftersales> list=  JsonUtils.jsonToList(newJson,Aftersales.class);
            for(Aftersales a : list) {
                a.setStatus(1);
                a.setCreateTime(new Date());
                OrderGoods orderGoodsMap=new OrderGoods();
                orderGoodsMap.setGoodsId(a.getGoodsId());
                orderGoodsMap.setOrderId(a.getOrderId());
                OrderGoods orderGoods= orderGoodsService.findOne(orderGoodsMap);
                orderGoods.setIsReviewed(1);

                a.setUserId(model.getUserId());
                a.setGoodsImage(orderGoods.getGoodsImage());
                a.setGoodsName(orderGoods.getGoodsName());
                a.setGoodsPrice(orderGoods.getGoodsPrice());
                a.setGoodsQty(orderGoods.getGoodsQty());
                aftersalesService.insert(a);
                orderGoodsService.updateByPrimaryKey(orderGoods);



            }
            result.setState(200);
            result.setMessage("提交成功");
            return result;

        }
        else {
            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }


    }



    @ApiOperation(value="获取物流信息",notes="获取物流信息")
    @RequestMapping(value="/getKuaidi",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getKuaidi(
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="orderId",value="orderId")@RequestParam(value="orderId",required=false)String orderId



    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        Order order= orderService.findOrder(orderId,model.getUserId());
        if(order!=null&&order.getOrderStatus()==3){
            OrderShippingBO orderShippingBO= orderShippingService.findByOrderId(orderId);
            if(orderShippingBO!=null){
                Setting setting= settingService.findOneByKey("system");
                SystemConfig cfg= JsonUtils.jsonToPojo(setting.getV(), SystemConfig.class);
                Kuaidi100.key=cfg.getKuaidi100Key();
                String info=Kuaidi100.getExpressInfo(orderShippingBO.getCode(),orderShippingBO.getTrackingNo());
                Kuaidi100Data kuaidis= JsonUtils.jsonToPojo(info,Kuaidi100Data.class);
                result.setState(200);
                result.setMessage("");
                result.setDatas(kuaidis);
                return result;
            }
            else {
                result.setState(500);
                result.setMessage("没有物流信息");
                return result;
            }

        }
        else {
            result.setState(500);
            result.setMessage("非法操作");
            return result;
        }



    }

}
