package com.ff.api.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.ResponseData;
import com.ff.common.util.JsonUtils;
import com.ff.common.util.MD5Utils;
import com.ff.common.util.UtilPath;
import com.ff.order.model.ex.Cart;
import com.ff.order.service.OrderService;
import com.ff.shop.model.*;
import com.ff.shop.service.*;
import com.ff.system.service.CommonService;
import com.ff.users.model.*;
import com.ff.users.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Api(value="用户接口",description="用户接口")
@Controller
@RequestMapping(value="/api/users")
public class UsersApiController extends BaseController{

    @Reference
    private UsersService usersService;

    @Reference
    private CommonService commonService;

    @Reference
    private UsersAccountService usersAccountService;

    @Reference
    private UsersCouponService usersCouponService;

    @Reference
    private UsersAccountLogService usersAccountLogService;

    @Reference
    private ShopService shopService;

    @Reference
    private UsersConsigneeService usersConsigneeService;

    @Reference
    private ShippingMethodService shippingMethodService;

    @Reference
    private GoodsService goodsService;
    @Reference
    private ProvinceService provinceService;

    @Reference
    private CityService cityService;

    @Reference
    private AreaService areaService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private OrderService orderService;



    @Reference
    private  GoodsFavoritesService goodsFavoritesService;




    @ApiOperation(value="用户登录",notes="用户登录")
    @RequestMapping(value="/login",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData Login(
            @ApiParam(required=true,name="mobile",value="手机号")@RequestParam(value="mobile",required=false)String mobile,
            @ApiParam(required=true,name="password",value="密码")@RequestParam(value="password",required=false)String password
    ){
        ResponseData result = new ResponseData();
        Users user=new Users();
        user.setMobile(mobile);
        Users model=usersService.findOne(user);
        password=MD5Utils.getPwd(password);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在");
        }
        else if(model.getStatus()==0){

            result.setState(500);
            result.setMessage("用户已禁用");
        }
        else if(!model.getPassword().equals(password)){

            result.setState(500);
            result.setMessage("密码不正确");
        }
        else{

            //更新用户登录信息
            String token = MD5Utils.MD5(model.getMobile()+"+"+model.getPassword());
            model.setToken(token);
            model.setUpateTime(new Date());
            usersService.updateByPrimaryKey(model);
            //返回数据
            result.setState(200);
            result.setDatas(token);
            result.setMessage("");

        }
        return result;
    }

    @ApiOperation(value="根据手机号登录",notes="根据手机号登录")
    @RequestMapping(value="/loginByMobile",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData LoginByMobile(
            @ApiParam(required=true,name="mobile",value="手机号")@RequestParam(value="mobile",required=false)String mobile,
            @ApiParam(required=true,name="code",value="验证码")@RequestParam(value="code",required=false)String code
    ){
        ResponseData result = new ResponseData();
        String checkCode=redisTemplate.opsForValue().get("smsCode:"+mobile).toString();

        if(!checkCode.equals(code)){
            result.setState(500);
            result.setMessage("验证码不正确");
            return result;
        }
        Users user=new Users();
        user.setMobile(mobile);
        Users model=usersService.findOne(user);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在");
        }
        else if(model.getStatus()==0){

            result.setState(500);
            result.setMessage("用户已禁用");
        }
        else{

            //更新用户登录信息
            String token = MD5Utils.MD5(model.getMobile()+"+"+model.getPassword());
            model.setToken(token);
            model.setUpateTime(new Date());
            usersService.updateByPrimaryKey(model);
            //返回数据
            result.setState(200);
            result.setDatas(token);
            result.setMessage("");

        }
        return result;
    }

    @ApiOperation(value="根据令牌获取用户信息",notes="根据令牌获取用户信息")
    @RequestMapping(value="/getUserInfo",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getUserInfo(
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
        //获取账户信息

        Map<String,Object> data=new HashMap<>();
        data.put("user",model);
        data.put("account",usersAccountService.findByPrimaryKey(model.getUserId().toString()));
        UsersCoupon map=new UsersCoupon();
        map.setUserId(model.getUserId());

        data.put("coupon",usersCouponService.selectCount(map));
        Shop shopMap=new Shop();
        shopMap.setUserId(model.getUserId());
        Shop shopModel=shopService.findOne(shopMap);

        data.put("shop",shopModel);
        //获取订单COUNT信息
        Map<String,String> orderCount=new HashMap<>();
        orderCount.put("orderStatus1",String.valueOf(orderService.selectCount(1, model.getUserId())));
        orderCount.put("orderStatus2",String.valueOf(orderService.selectCount(2, model.getUserId() )));
        orderCount.put("orderStatus3",String.valueOf(orderService.selectCount(3, model.getUserId() )));
        orderCount.put("orderStatus4",String.valueOf(orderService.selectCount(4, model.getUserId() )));
        data.put("orderStatus",orderCount);
        result.setState(200);
        result.setDatas(data);
        result.setMessage("");
        return result;
    }


    /**
     * 发送短信验证码（登录）
     * @param mobile
     * @return
     */
    @ApiOperation(value="发送短信验证码",notes="发送短信验证码", httpMethod = "POST")
    @RequestMapping(value="/sendIdentifyingCode",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData sendIdentifyingCode(
                                            @ApiParam(required=true,name="sms_code",value="验证码类型")@RequestParam(value="sms_code",required=false)String sms_code,
                                            @ApiParam(required=true,name="mobile",value="手机号")@RequestParam(value="mobile",required=false)String mobile){
        int code = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;// 产生1000-9999的随机数


        String json="{\"code\":\""+code+"\"}";
        ResponseData result = new ResponseData();
        if(!UtilPath.isMobile(mobile)){//验证手机号码格式是否正确
            result.setState(500);
            result.setMessage("手机号码格式不正确！");
            return result;
        }
        commonService.sendSMS("sms_code", mobile, json);//调运发送短信的方法
        //创建session，并将手机号于验证码存入session
        redisTemplate.opsForValue().set("smsCode:"+mobile,code, 180000, TimeUnit.MILLISECONDS);

//        HttpSession session = request.getSession();
//        session.setAttribute("code", mobile+"#"+code);
//        session.setMaxInactiveInterval(300);//设置session的时间(300s=5min)
        result.setState(200);
        result.setMessage("短信发送成功！");
        return result;
    }

    /**
     * 手机号注册（账号为手机号）
     */
    @ApiOperation(value="会员注册",notes="会员注册", httpMethod = "POST")
    @RequestMapping(value="/register",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData register(
                                   @ApiParam(required=true,name="username",value="用户名(手机号)")@RequestParam(value="username",required=false)String username,
                                   @ApiParam(required=true,name="password",value="密码")@RequestParam(value="password",required=false)String password,
                                   @ApiParam(required=true,name="code",value="验证码")@RequestParam(value="code",required=false)String code){
        ResponseData result = new ResponseData();
        //验证手机号码格式是否正确
        if(!UtilPath.isMobile(username)){
            result.setState(500);
            result.setMessage("手机号码格式不正确！");
            return result;
        }
        String checkCode=redisTemplate.opsForValue().get("smsCode:"+username).toString();
        if(!checkCode.equals(code)){
            result.setState(500);
            result.setMessage("验证码不正确");
            return result;
        }
        if(password.equals("")){
            result.setState(500);
            result.setMessage("密码不能为空");
            return result;
        }
        Users user=new Users();
        user.setUsername(username);
        Users model=usersService.findOne(user);
        if(model!=null)
        {
            result.setState(500);
            result.setMessage("此手机号已存在");
            return result;
        }
        user.setPassword(MD5Utils.MD5(password).toLowerCase());
        user.setStatus(1);
        user.setMobile(username);
        user.setCreateTime(new Date());
        user.setMobileStatus(1);
        Integer id=usersService.insert(user);
        UsersAccount account=new UsersAccount();
        account.setPoints(0);
        account.setBalance(new BigDecimal(0));
        account.setUserId(id);
        account.setExp(0);
        usersAccountService.insert(account);
        result.setState(200);
        result.setMessage("注册成功！");
        return result;
    }


    /**
     * 手机号注册（账号为手机号）
     */
    @ApiOperation(value="忘记密码",notes="忘记密码", httpMethod = "POST")
    @RequestMapping(value="/resetPwd",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData resetPwd(
                                 @ApiParam(required=true,name="username",value="用户名(手机号)")@RequestParam(value="username",required=false)String username,
                                 @ApiParam(required=true,name="password",value="密码")@RequestParam(value="password",required=false)String password,
                                 @ApiParam(required=true,name="code",value="验证码")@RequestParam(value="code",required=false)String code){
        ResponseData result = new ResponseData();
        //验证手机号码格式是否正确
        if(!UtilPath.isMobile(username)){
            result.setState(500);
            result.setMessage("手机号码格式不正确！");
            return result;
        }
        String checkCode=redisTemplate.opsForValue().get("smsCode:"+username).toString();

        if(!checkCode.equals(code)){
            result.setState(500);
            result.setMessage("验证码不正确");
            return result;
        }
        if(password.equals("")){
            result.setState(500);
            result.setMessage("密码不能为空");
            return result;
        }
        Users user=new Users();
        user.setUsername(username);
        Users model=usersService.findOne(user);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("此手机号不存在");
            return result;
        }

        model.setPassword(MD5Utils.MD5(password).toLowerCase());

        if(usersService.updateByPrimaryKey(model)>0){

            result.setState(200);
            result.setMessage("注册成功！");
            return result;
        }
        else
        {
            result.setState(500);
            result.setMessage("操作失败");
            return result;
        }


    }

    /**
     * 设置jpush
     */
    @ApiOperation(value="设置极光Id",notes="设置jpush", httpMethod = "POST")
    @RequestMapping(value="/setJPushId",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData setJPushId(HttpServletRequest request,
                                   @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                 @ApiParam(required=true,name="jpushId",value="jpushId")@RequestParam(value="jpushId",required=false)String jpushId){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        model.setJpushId(jpushId);
        if(usersService.updateByPrimaryKey(model)>0){
            result.setState(200);
            result.setMessage("操作成功");

        }else{

            result.setState(500);
            result.setMessage("操作失败");
        }
        return result;


    }

    /**
     * 设置头像
     */
    @ApiOperation(value="设置头像",notes="设置头像", httpMethod = "POST")
    @RequestMapping(value="/setHeadImg",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData setHeadImg(HttpServletRequest request,
                                   @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                   @ApiParam(required=true,name="headImg",value="headImg")@RequestParam(value="headImg",required=false)String headImg){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        model.setAvatar(headImg);
        if(usersService.updateByPrimaryKey(model)>0){
            result.setState(200);
            result.setMessage("操作成功");

        }else{

            result.setState(500);
            result.setMessage("操作失败");
        }
        return result;


    }

    /**
     * 设置昵称
     */
    @ApiOperation(value="设置昵称",notes="设置昵称", httpMethod = "POST")
    @RequestMapping(value="/setNickname",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData setNickname(HttpServletRequest request,
                                   @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                   @ApiParam(required=true,name="nickname",value="nickname")@RequestParam(value="nickname",required=false)String nickname){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        model.setNickname(nickname);
        if(usersService.updateByPrimaryKey(model)>0){
            result.setState(200);
            result.setMessage("操作成功");

        }else{

            result.setState(500);
            result.setMessage("操作失败");
        }
        return result;


    }


    /**
     * 设置手机号
     */
    @ApiOperation(value="设置手机号",notes="设置手机号", httpMethod = "POST")
    @RequestMapping(value="/setPhone",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData setNickname(
                                    @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                    @ApiParam(required=true,name="mobile",value="mobile")@RequestParam(value="mobile",required=false)String mobile,
                                    @ApiParam(required=true,name="mobile1",value="mobile1")@RequestParam(value="mobile1",required=false)String mobile1,
                                    @ApiParam(required=true,name="code",value="code")@RequestParam(value="code",required=false)String code
                                    ){
        ResponseData result = new ResponseData();

        //验证手机号码格式是否正确
        if(!UtilPath.isMobile(mobile)||!UtilPath.isMobile(mobile1)){
            result.setState(500);
            result.setMessage("手机号码格式不正确！");
            return result;
        }
        String checkCode=redisTemplate.opsForValue().get("smsCode:"+mobile).toString();

        if(!checkCode.equals(code)){
            result.setState(500);
            result.setMessage("验证码不正确");
            return result;
        }
        Users mobileUser=new Users();
        mobileUser.setUsername(mobile1);
        Users mobileModel=usersService.findOne(mobileUser);
        if(mobileModel!=null){
            result.setState(500);
            result.setMessage("此手机号已存在");
            return result;
        }
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        if(!model.getMobile().equals(mobile)){

            result.setState(500);
            result.setMessage("原手机号不正确");
        }
        else{
            model.setMobile(mobile1);
            model.setUsername(mobile1);
            if(usersService.updateByPrimaryKey(model)>0){
                result.setState(200);
                result.setMessage("操作成功");
            }else{
                result.setState(500);
                result.setMessage("操作失败");
            }
        }
        return result;


    }

    /**
     * 修改密码
     */
    @ApiOperation(value="修改密码",notes="修改密码", httpMethod = "POST")
    @RequestMapping(value="/setPwd",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData setPwd(
                                 @ApiParam(required=true,name="token",value="令牌")@RequestParam(value="token",required=false)String token,
                                 @ApiParam(required=true,name="username",value="用户名(手机号)")@RequestParam(value="username",required=false)String username,
                                 @ApiParam(required=true,name="password",value="密码")@RequestParam(value="password",required=false)String password,
                                 @ApiParam(required=true,name="code",value="验证码")@RequestParam(value="code",required=false)String code){
        ResponseData result = new ResponseData();
        //验证手机号码格式是否正确
        if(!UtilPath.isMobile(username)){
            result.setState(500);
            result.setMessage("手机号码格式不正确！");
            return result;
        }
        String checkCode=redisTemplate.opsForValue().get("smsCode:"+username).toString();

        if(!checkCode.equals(code)){
            result.setState(500);
            result.setMessage("验证码不正确");
            return result;
        }
        if(password.equals("")){
            result.setState(500);
            result.setMessage("密码不能为空");
            return result;
        }
        Users user=new Users();
        user.setToken(token);
        Users model=usersService.findOne(user);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("此用户不存在");
            return result;
        }
        if(!model.getMobile().equals(username))
        {
            result.setState(500);
            result.setMessage("此手机号不正确");
            return result;
        }

        model.setPassword(MD5Utils.MD5(password).toLowerCase());

        if(usersService.updateByPrimaryKey(model)>0){

            result.setState(200);
            result.setMessage("修改成功！");
            return result;
        }
        else
        {
            result.setState(500);
            result.setMessage("操作失败");
            return result;
        }


    }

    @ApiOperation(value="根据令牌获取消费记录",notes="根据令牌获取消费记录")
    @RequestMapping(value="/getBalances",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getBalances(HttpServletRequest request,
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="status",value="0消费,1充值")@RequestParam(value="status",required=false)Integer status
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        //获取账户信息
        Page<UsersAccountLog> page = getPage(request);
        EntityWrapper<UsersAccountLog> ew=new EntityWrapper();
        if(status==0)
        {
            ew.lt("balance",0);
        }
        else if(status==1){
            ew.gt("balance",0);
        }
        ew.orderBy("id",false);
        ew.eq("user_id",model.getUserId());
        Map<String,Object> data=new HashMap<>();
        Page<UsersAccountLog> list=usersAccountLogService.selectPage(page,ew);
        data.put("accountLog",list.getRecords());
        result.setState(200);
        result.setDatas(data);
        result.setMessage("");


        return result;
    }




    @ApiOperation(value="根据令牌获取积分记录",notes="根据令牌获取积分记录")
    @RequestMapping(value="/getIntegral",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getIntegral(HttpServletRequest request,
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
        //获取账户信息
        Page<UsersAccountLog> page = getPage(request);
        EntityWrapper<UsersAccountLog> ew=new EntityWrapper();
        ew.ne("points",0);
        ew.orderBy("id",false);
        ew.eq("user_id",model.getUserId());
        Map<String,Object> data=new HashMap<>();
        Page<UsersAccountLog> list=usersAccountLogService.selectPage(page,ew);
        data.put("accountLog",list.getRecords());
        result.setState(200);
        result.setDatas(data);
        result.setMessage("");


        return result;
    }

    @ApiOperation(value="根据令牌获取优惠券",notes="根据令牌获取优惠券")
    @RequestMapping(value="/getCoupon",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getCoupon(
            HttpServletRequest request,
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
        Page<UsersCoupon> page = getPage(request);
        EntityWrapper<UsersCoupon> ew=new EntityWrapper();
        ew.orderBy("create_time",false);

        Map<String,Object> data=new HashMap<>();
        UsersCoupon map=new UsersCoupon();
        map.setUserId(model.getUserId());
        ew.setEntity(map);
        data.put("coupon",usersCouponService.selectPage(page,ew));
         result.setState(200);
        result.setDatas(data);
        result.setMessage("");
        return result;
    }
    @ApiOperation(value="获取用户所有地址",notes="获取用户所有地址")
    @RequestMapping(value="/getConsignees",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getConsignees(HttpServletRequest request, HttpServletResponse response,
                                     @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token){


        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        UsersConsignee map=new UsersConsignee();
        map.setUserId(model.getUserId());

        List<UsersConsignee> list= usersConsigneeService.selectByT(map);
        for(UsersConsignee usersConsignee:list){
            Province province= provinceService.findByProvince(usersConsignee.getProvince().toString());
            City city= cityService.selectByCity(usersConsignee.getCity().toString());
            Area area= areaService.selectByArea(usersConsignee.getArea().toString());


            usersConsignee.setAddress( province.getProvince()+" "+city.getCity()+" "+area.getArea()+" "+usersConsignee.getAddress());

        }

        result.setDatas(list);
        result.setState(200);

        return result;
    }



    @ApiOperation(value="获取用户地址",notes="获取用户地址")
    @RequestMapping(value="/getConsignee",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getConsignee(HttpServletRequest request, HttpServletResponse response,
                                     @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token){


        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        UsersConsignee map=new UsersConsignee();
        map.setUserId(model.getUserId());
        map.setIsDefault(1);

        UsersConsignee uc= usersConsigneeService.findOne(map);
        if(uc!=null){
            Province province= provinceService.findByProvince(uc.getProvince().toString());
            City city= cityService.selectByCity(uc.getCity().toString());
            Area area= areaService.selectByArea(uc.getArea().toString());
            uc.setAddress(province.getProvince()+" "+city.getCity()+" "+area.getArea()+" "+uc.getAddress());
            result.setDatas(uc);

        }



       result.setState(200);

        return result;
    }

    @ApiOperation(value="添加用户地址",notes="添加用户地址")
    @RequestMapping(value="/addConsignee",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData addConsignee(HttpServletRequest request, HttpServletResponse response,
                                     @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                     @ApiParam(required=true,name="usersConsignee",value="usersConsignee")UsersConsignee usersConsignee){


        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        usersConsignee.setUserId(model.getUserId());
        usersConsignee.setIsDefault(0);
        if(usersConsigneeService.insert(usersConsignee)>0){
            result.setState(200);
            result.setMessage("操作成功");

        }

        return result;
    }

    @ApiOperation(value="编辑用户地址",notes="编辑用户地址")
    @RequestMapping(value="/editConsignee",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData editConsignee(HttpServletRequest request, HttpServletResponse response,
                                     @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                     @ApiParam(required=true,name="usersConsignee",value="usersConsignee")@RequestParam(value="usersConsignee",required=false)UsersConsignee usersConsignee){


        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        if(usersConsigneeService.updateByPrimaryKey(usersConsignee)>0){
            result.setState(200);
            result.setMessage("操作成功");

        }

        return result;
    }
    @ApiOperation(value="删除用户地址",notes="删除用户地址")
    @RequestMapping(value="/delConsignee",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData delConsignee(HttpServletRequest request, HttpServletResponse response,
                                     @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                     @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id){


        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        UsersConsignee uc=new UsersConsignee();
        uc.setUserId(model.getUserId());
        String[] ids={id.toString()};

        if(usersConsigneeService.deleteByPrimaryKey(ids)>0){
            result.setState(200);
            result.setMessage("操作成功");

        }

        return result;
    }

    @ApiOperation(value="设置默认地址",notes="设置默认地址")
    @RequestMapping(value="/setDefault",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData setDefault(HttpServletRequest request, HttpServletResponse response,
                                     @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                     @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id){


        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        if(usersConsigneeService.setDefault(id)>0){
            result.setState(200);
            result.setMessage("操作成功");

        }

        return result;
    }



    @ApiOperation(value="获取配送方式",notes="获取配送方式")
    @RequestMapping(value="/getShippingMethod",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getShippingMethod(HttpServletRequest request, HttpServletResponse response){


        ResponseData result = new ResponseData();

        ShippingMethod map=new ShippingMethod();
        map.setEnable(1);
        result.setDatas( shippingMethodService.selectByT(map));
        result.setState(200);

        return result;
    }

    @ApiOperation(value="获取配送费",notes="获取配送费")
    @RequestMapping(value="/getShippingMoney",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getShippingMoney(HttpServletRequest request, HttpServletResponse response,
                                         @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                         @ApiParam(required=true,name="cId",value="cId")@RequestParam(value="cId",required=false)Integer cId,
                                          @ApiParam(required=true,name="shippingId",value="shippingId")@RequestParam(value="shippingId",required=false)Integer shippingId,
                                          @ApiParam(required=true,name="cart",value="cart")@RequestParam(value="cart",required=false)  String cart){

        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }

        String newJson = StringEscapeUtils.unescapeHtml4(cart);
        List<Cart> list=  JsonUtils.jsonToList(newJson,Cart.class);
        BigDecimal cartWeight=new BigDecimal(0);
        Integer cartNum=0;
        if(list!=null&&list.size()>0){
            for (Integer i=0;i<list.size();i++ ) {
                cartNum+=list.get(i).getNum();
                Goods goods=goodsService.findByPrimaryKey(list.get(i).getGoodsId().toString());
                cartWeight=cartWeight.add(goods.getGoodsWeight());
            }
        }
        result=shippingMethodService.getFreight(cId,shippingId,cartWeight,cartNum);


        return result;
    }






    /**
     * 收藏商品
     */
    @ApiOperation(value="收藏商品",notes="收藏商品", httpMethod = "GET")
    @RequestMapping(value="/favoriteGoods",method=RequestMethod.GET)
    @ResponseBody
    public ResponseData favoriteGoods(
            @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
            @ApiParam(required=true,name="goodsId",value="goodsId")@RequestParam(value="goodsId",required=false)Integer goodsId
    ){
        ResponseData result = new ResponseData();


        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        GoodsFavorites goodsFavoritesParams=new GoodsFavorites();
        goodsFavoritesParams.setGoodsId(goodsId);
        goodsFavoritesParams.setUserId(model.getUserId());
        GoodsFavorites goodsFavorites=goodsFavoritesService.findOne(goodsFavoritesParams);
        if(goodsFavorites==null){
            goodsFavoritesParams.setCreateTime(new Date());
            goodsFavoritesService.insert(goodsFavoritesParams);
            result.setState(200);
            result.setMessage("已收藏");
        }
        else{
            goodsFavoritesService.deleteByPrimaryKey(new String[]{goodsFavorites.getFavoritesId().toString()});
            result.setState(200);
            result.setMessage("取消收藏");
        }

        return result;


    }







}
