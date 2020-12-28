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


@Api(value="商城接口",description="商城接口")
@Controller
@RequestMapping(value="/api/shop")
public class ShopApiController extends BaseController {
    @Reference
    private GoodsCateService goodsCateService;
    @Reference
    private GoodsService goodsService;
    @Reference
    private GoodsAlbumService goodsAlbumService;

    @Reference
    private GoodsReviewService goodsReviewService;

    @Reference
    private GoodsOptionalService goodsOptionalService;

    @Reference
    private GoodsOptionalTypeService goodsOptionalTypeService;

    @Reference
    private GoodsAttrService goodsAttrService;

    @Reference
    private ShopService shopService;

    @Reference
    private ShopImageService shopImageService;

    @Reference
    private ShopCateService shopCateService;

    @Reference
    private UsersService usersService;

    @Reference
    private UsersAccountService usersAccountService;

    @Reference
    private ShopFavoritesService shopFavoritesService;

    @Reference
    private ShopOrderService shopOrderService;
    @Reference
    private GoodsFavoritesService goodsFavoritesService;

    @Reference
    private GoodsSpecService goodsSpecService;

    @Reference
    private ShopCouponService shopCouponService;

    @ApiOperation(value="获取商品分类",notes="获取商品分类")
    @RequestMapping(value="/getGoodsCateList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getGoodsCateList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
         @ApiParam(required=true,name="pid",value="pid")@RequestParam(value="pid",required=false)Integer pid
         ){

        ResponseData result = new ResponseData();
        result.setState(200);
        GoodsCate cate=new GoodsCate();
        cate.setParentId(pid);
        List<GoodsCate> cates= goodsCateService.selectByT(cate);
        Collections.sort(cates);
        result.setDatas(cates);
        result.setMessage("");
        return result;
    }

    @ApiOperation(value="获取商品",notes="获取商品")
    @RequestMapping(value="/getGoodsList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getGoodsList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                         @ApiParam(required=true,name="cid",value="cid")@RequestParam(value="cid",required=false)Integer cid,
                                     @ApiParam(required=true,name="keyword",value="keyword")@RequestParam(value="keyword",required=false)String keyword
    ){

        Page<Goods> page = getPage(request);
        EntityWrapper<Goods> ew=new EntityWrapper();
        if(cid!=0)
        {
            ew.eq("cate_id",cid);
        }
        if(StringUtils.isNotEmpty(keyword))
        {
            ew.like("goods_name","%"+keyword+"%");
        }
        ew.orderBy("goods_id",false);
        ew.eq("status",1);
        Map<String,Object> data=new HashMap<>();

        ResponseData result = new ResponseData();
        result.setState(200);
        Page<Goods> list=goodsService.selectPage(page,ew);
        result.setDatas(list.getRecords());
        result.setMessage("");
        return result;
    }
    @ApiOperation(value="获取商品详情",notes="获取商品详情")
    @RequestMapping(value="/getGoodsDetail",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getGoodsDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                     @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id
    ){


        ResponseData result = new ResponseData();
        result.setState(200);
        Goods goods=goodsService.findByPrimaryKey(id.toString());
        GoodsAlbum album=new GoodsAlbum();
        album.setGoodsId(id);
        GoodsReview review =new GoodsReview();
        review.setGoodsId(id);
        review.setStatus(1);
        Map<String,Object> data=new HashMap<>();
        data.put("goods",goods);
        data.put("goodsAlbum", goodsAlbumService.selectByT(album));
        //选项类型
        GoodsOptional goodsOptional=new GoodsOptional();
        goodsOptional.setGoodsId(id);
        List<GoodsOptional> list=goodsOptionalService.selectByT(goodsOptional);
        ArrayList ids=new ArrayList();
        if(list!=null){
             for(GoodsOptional o:list){
                 if(ids!=null){
                     Boolean flag=false;
                     for(Object item:ids){
                         if(item==o.getTypeId()){
                             flag=true;
                         }
                     }
                     if(!flag){
                         ids.add(o.getTypeId());
                     }
                 }
             }
        }
        List<GoodsOptionalType> goodsOptionalTypeList=new ArrayList<>();
        for (Object goodsOptionalTypeId:ids){
            GoodsOptionalType goodsOptionalType=goodsOptionalTypeService.findByPrimaryKey(goodsOptionalTypeId.toString());
            goodsOptionalTypeList.add(goodsOptionalType);
        }
        //选项类型
        data.put("goodsOptional",  goodsOptionalService.selectByT(goodsOptional));
        data.put("goodsReview", goodsReviewService.selectByT(review));
        data.put("GoodsOptionalType", goodsOptionalTypeList);
        GoodsSpec goodsSpecMap=new GoodsSpec();
        goodsSpecMap.setGoodsId(id);
        data.put("goodsSpec", goodsSpecService.selectByT(goodsSpecMap));
        result.setDatas(data);
        result.setMessage("");
        return result;
    }

    @ApiOperation(value="获取商品评价",notes="获取商品评价")
    @RequestMapping(value="/getGoodsReview",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getGoodsReview(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                       @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id
    ){


        ResponseData result = new ResponseData();
        result.setState(200);
        Map<String,Object> data=new HashMap<>();


        Page<Goods> page = getPage(request);

        List<GoodsReviewUserBO> list=goodsReviewService.selectListByGoodsId(id,page);
        data.put("goodsReview",list);

        result.setDatas(data);
        result.setMessage("");
        return result;
    }
    @ApiOperation(value="获取商品规格",notes="获取商品规格")
    @RequestMapping(value="/getGoodsAttr",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getGoodsAttr(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                       @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id
    ){


        ResponseData result = new ResponseData();
        result.setState(200);
        Map<String,Object> data=new HashMap<>();


        List<GoodsAttrBO> goodsAttrBOS=goodsAttrService.selectAttr(id);
        data.put("goodsAttr",goodsAttrBOS);

        result.setDatas(data);
        result.setMessage("");
        return result;
    }



    @ApiOperation(value="获取购物车",notes="获取购物车")
    @RequestMapping(value="/getCart",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData getCart(HttpServletRequest request,
                                @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                @ApiParam(required=true,name="cart",value="cart")@RequestParam(value="cart",required=false)  String cart
                                ){
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
        BigDecimal total=new BigDecimal(0.00);
        Integer integer=0;
        if(list!=null&&list.size()>0){
            for (Integer i=0;i<list.size();i++ ) {

                Goods goods=goodsService.findByPrimaryKey(list.get(i).getGoodsId().toString());

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
                integer=integer+goods.getIntegral()*list.get(i).getNum();

                list.get(i).setPrice(price);
                total=total.add(price).multiply(BigDecimal.valueOf(list.get(i).getNum()));
                list.get(i).setTitle(goods.getGoodsName());
                list.get(i).setImage(goods.getGoodsImage());

            }
        }
        Map<String,Object> data=new HashMap<>();
        data.put("cart",list);
        data.put("total",total);

        UsersAccount usersAccount= usersAccountService.findByPrimaryKey(model.getUserId().toString());

        data.put("integral",integer);
        data.put("userIntegral",usersAccount.getPoints());
        result.setState(200);
        result.setDatas(data);

        return result;
    }


    @ApiOperation(value="检测购物车",notes="检测购物车")
    @RequestMapping(value="/checkCart",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData checkCart(HttpServletRequest request,
                                @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                @ApiParam(required=true,name="cart",value="cart")@RequestParam(value="cart",required=false)  String cart
    ){
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
        BigDecimal total=new BigDecimal(0.00);
        if(list!=null&&list.size()>0){
            for (Integer i=0;i<list.size();i++ ) {

                Goods goods=goodsService.findByPrimaryKey(list.get(i).getGoodsId().toString());

                //如果商品小于
                if((goods.getStockQty()-list.get(i).getNum())<0){
                    result.setState(500);
                    result.setMessage("商品:"+goods.getGoodsName()+",库存不足");
                    return result;
                }
            }
            result.setState(200);
        }
        else{
            result.setState(500);
            result.setMessage("购物车为空");
        }
        return result;
    }



    @ApiOperation(value="获取附近推荐商家",notes="获取附近推荐商家")
    @RequestMapping(value="/getShopTuiList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getShopTuiList(HttpServletRequest request,String lng,String lat){
        Page<Map<String,ShopBO>> page = getPage(request);
        Shop model=new Shop();
        model.setLng(lng);
        model.setLat(lat);
        model.setIsTui(1);
        model.setCateId(0);
        ResponseData result = new ResponseData();
        result.setDatas(shopService.selectByLngLat(page,model));
        result.setMessage("");
        result.setState(200);
        return result;
    }


    @ApiOperation(value="获取商家",notes="获取商家")
    @RequestMapping(value="/getShopList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getShopList(HttpServletRequest request,String lng,String lat,Integer cateId,
                                     @ApiParam(required=true,name="keyword",value="keyword")@RequestParam(value="keyword",required=false)String keyword


    ){
        Page<Map<String,ShopBO>> page = getPage(request);
        Shop model=new Shop();
        model.setCateId(cateId);
        if(StringUtils.isNotEmpty(keyword))
        {
            model.setTitle(keyword);
        }
        model.setLng(lng);
        model.setLat(lat);
        ResponseData result = new ResponseData();
        result.setDatas(shopService.selectByLngLat(page,model));
        result.setMessage("");
        result.setState(200);
        return result;
    }


    @ApiOperation(value="获取商家分类",notes="获取商家分类")
    @RequestMapping(value="/getShopCate",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getShopCate(HttpServletRequest request){
        ResponseData result = new ResponseData();
        result.setDatas( shopCateService.selectAll());
        result.setMessage("");
        result.setState(200);
        return result;
    }

    @ApiOperation(value="获取商家详情",notes="获取商家详情")
    @RequestMapping(value="/getShopDetail",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getShopDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                      @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id
    ){

        ResponseData result = new ResponseData();
        result.setState(200);
        Shop shop=new Shop();
        shop.setShopId(id);
        shop.setClosed(0);
        shop.setStatus(1);

        Shop shopDetail= shopService.findOne(shop);
        ShopImage image=new ShopImage();
        image.setShopId(shopDetail.getShopId());
        List<ShopImage> images= shopImageService.selectByT(image);
        Map<String,Object> map=new HashMap<>();

        ShopCoupon shopCoupon=new ShopCoupon();
        shopCoupon.setShopId(shopDetail.getShopId());
        shopCoupon.setStatus(1);
        List<ShopCoupon> list=shopCouponService.selectByT(shopCoupon);
        map.put("shop",shopDetail);
        map.put("shopImages",images);
        map.put("coupon",list);
        result.setDatas(map);
        result.setMessage("");
        return result;
    }

    @ApiOperation(value="创建快捷支付订单",notes="创建快捷支付订单")
    @RequestMapping(value="/createOrder",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData createOrder(HttpServletRequest request,
                                    @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                    @ApiParam(required=true,name="shopId",value="shopId")@RequestParam(value="shopId",required=false)Integer shopId,
                                    @ApiParam(required=true,name="支付金额",value="money")@RequestParam(value="money",required=false)BigDecimal money,
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
        Shop shop=shopService.findByPrimaryKey(shopId.toString());
        if(shop==null){
            result.setState(500);
            result.setMessage("商家不存在");
        }
        else if(shop.getStatus()==0){
            result.setState(500);
            result.setMessage("此商家已关闭");
        }
        ShopOrder order=new ShopOrder();
        order.setOrderSn(OrderUtils.getOrderSN());
        order.setClosed(0);
        order.setCreateIp(IpAddress.getIp(request));
        order.setCreateTime(new Date());
        order.setIsPay(0);
        order.setMoney(money);
        order.setAllmoney(money);
        order.setPayType(payType);
        if(payType==1){
            UsersAccount usersAccount= usersAccountService.findByPrimaryKey(model.getUserId().toString());
            int integral= money.multiply(BigDecimal.valueOf(shop.getIntegral())).divide(BigDecimal.valueOf(100)).intValue();

            if(usersAccount!=null&&usersAccount.getPoints()>=integral){


                UsersAccountLog log=new UsersAccountLog();
                log.setUserId(model.getUserId());
                log.setExp(0);
                log.setBalance(new BigDecimal(0));
                log.setPoints(-integral);
                log.setCause("您在"+shop.getTitle()+"消费"+order.getMoney()+"元，使用积分:"+integral);
                log.setAdminId("0");
                Long time=new Date().getTime()/1000;
                log.setDateline(Integer.parseInt(time.toString()));
                usersAccountService.updateAccount(log);
                order.setIntegral(integral);
                order.setMoney(order.getMoney().subtract(BigDecimal.valueOf(integral)));
            }
        }
        else if(payType==2){//满减

            if(money.compareTo(shop.getMinMoney())>=0){
                order.setMoney(money.subtract(shop.getJianMoney()));
            }
        }
        order.setShopId(shopId);
        order.setUserId(model.getUserId());
        if(shopOrderService.insert(order)>0){
            result.setState(200);
            result.setDatas(order.getOrderSn());
            result.setMessage("订单创建成功");
        }
        else{
            result.setState(500);
            result.setMessage("此商家已关闭");
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

        ShopOrder map=new ShopOrder();
        map.setOrderSn(orderSN);
        map.setUserId(model.getUserId());
        ShopOrder order= shopOrderService.findOne(map);
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

    @ApiOperation(value="取消订单",notes="取消订单")
    @RequestMapping(value="/cancelShopOrder",method= RequestMethod.POST)
    @ResponseBody
    public ResponseData cancelOrder(
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

        ShopOrder map=new ShopOrder();
        map.setOrderSn(orderSN);
        map.setUserId(model.getUserId());
        ShopOrder order= shopOrderService.findOne(map);
        if(order==null){
            result.setState(500);
            result.setMessage("订单不存在");
        }
        else{
            order.setIsPay(-1);
            shopOrderService.updateByPrimaryKey(order);
            if(order.getIntegral()>0){
                UsersAccountLog log=new UsersAccountLog();
                log.setUserId(model.getUserId());
                log.setExp(0);
                log.setBalance(new BigDecimal(0));
                log.setPoints(order.getIntegral());
                log.setCause("您取消认单，返还积分:"+order.getIntegral());
                log.setAdminId("0");
                Long time=new Date().getTime()/1000;
                log.setDateline(Integer.parseInt(time.toString()));
                usersAccountService.updateAccount(log);
            }
            result.setState(200);
            result.setMessage("操作成功");
        }
        return  result;

    }


    @ApiOperation(value="获取快捷支付订单列表",notes="获取快捷支付订单列表")
    @RequestMapping(value="/getShopOrderList",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getShopOrderList(HttpServletRequest request,
                                         @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                         @ApiParam(required=true,name="isPay",value="isPay")@RequestParam(value="isPay",required=false)Integer isPay
                                         ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        Page<ShopOrderBO> page = getPage(request);

        List<ShopOrderBO> list=shopOrderService.selectListByPage(model.getUserId(),isPay,page);
        result.setDatas(list);
        result.setMessage("");
        result.setState(200);
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
        ShopOrder map=new ShopOrder();
        map.setOrderSn(orderSN);
        map.setUserId(model.getUserId());
        ShopOrder order= shopOrderService.findOne(map);

        if(order==null){
            result.setState(500);
            result.setMessage("订单不存在");
        }
        else{

            order.setPaymentMethod(paymentId);
            shopOrderService.updateByPrimaryKey(order);
            result.setState(200);
            result.setMessage("");
        }
        return result;
    }



    @ApiOperation(value="关注商家",notes="关注商家")
    @RequestMapping(value="/setFavorite",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData setFavorite(HttpServletRequest request,
                                    @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                    @ApiParam(required=true,name="shopId",value="shopId")@RequestParam(value="shopId",required=false)Integer shopId


    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        ShopFavorites fa= new ShopFavorites();
        fa.setShopId(shopId);
        fa.setUserId(model.getUserId());
        ShopFavorites fa_model=shopFavoritesService.findOne(fa);
        if(fa_model!=null)//取消关注
        {

            String[] id={fa_model.getFavoritesId().toString()};

            shopFavoritesService.deleteByPrimaryKey(id);
            result.setState(200);
            result.setMessage("取消关注");


        }
        else{//关注
            fa.setCreateTime(new Date());
            shopFavoritesService.insert(fa);
            result.setState(200);
            result.setMessage("已关注");

        }
        return result;
    }

    @ApiOperation(value="获取关注商家",notes="获取关注商家")
    @RequestMapping(value="/getShopFavorite",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getFavorite(HttpServletRequest request,
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

        Page<Map<String,ShopFavoritesBO>> page = getPage(request);
        List<ShopFavoritesBO> list=shopFavoritesService.selectByUserId(page,model.getUserId());
        result.setState(200);
        result.setDatas(list);
        result.setMessage("");
        return result;
    }
    @ApiOperation(value="获取关注商品",notes="获取关注商品")
    @RequestMapping(value="/getGoodsFavorite",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getGoodsFavorite(HttpServletRequest request,
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

        Page<Map<String,GoodsFavoritesBO>> page = getPage(request);
        List<GoodsFavoritesBO> list=goodsFavoritesService.selectByUserId(page,model.getUserId());
        result.setState(200);
        result.setDatas(list);
        result.setMessage("");
        return result;
    }


    @ApiOperation(value="获取本店详情",notes="获取本店详情")
    @RequestMapping(value="/getMyShopDetail",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getMyShopDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                        @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                        @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id
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
        Shop shop=new Shop();
        shop.setShopId(id);
        shop.setClosed(0);
        shop.setStatus(1);
        Shop shopDetail= shopService.findOne(shop);
        if(model.getUserId()!=shopDetail.getUserId())
        {
            result.setState(500);
            result.setMessage("非法用户");
            return result;
        }
        ShopImage image=new ShopImage();
        image.setShopId(shopDetail.getShopId());
        List<ShopImage> images= shopImageService.selectByT(image);
        Map<String,Object> map=new HashMap<>();
        map.put("shop",shopDetail);
        map.put("shopImages",images);
        //获取今日收入及订单总数
        map.put("money", shopOrderService.selectMoneySum(shopDetail.getShopId()));
        map.put("count", shopOrderService.selectOrderCount(shopDetail.getShopId()));
        result.setDatas(map);
        result.setMessage("");
        return result;
    }




    @ApiOperation(value="获取商家快捷支付订单列表",notes="获取商家快捷支付订单列表")
    @RequestMapping(value="/getShopOrders",method= RequestMethod.GET)
    @ResponseBody
    public ResponseData getShopOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                         @ApiParam(required=true,name="token",value="token")@RequestParam(value="token",required=false)String token,
                                         @ApiParam(required=true,name="id",value="id")@RequestParam(value="id",required=false)Integer id
    ){
        ResponseData result = new ResponseData();
        Users model=usersService.findUserByToken(token);
        if(model==null)
        {
            result.setState(500);
            result.setMessage("用户不存在或已禁用");
            return result;
        }
        Shop shop=new Shop();
        shop.setShopId(id);
        shop.setClosed(0);
        shop.setStatus(1);
        Shop shopDetail= shopService.findOne(shop);
        if(model.getUserId()!=shopDetail.getUserId())
        {
            result.setState(500);
            result.setMessage("非法用户");
            return result;
        }
        Page<ShopOrderBO> page = getPage(request);

        List<ShopOrderBO> list=shopOrderService.selectListByShopId(shop.getShopId(),page);
        result.setDatas(list);
        result.setMessage("");
        result.setState(200);
        return result;
    }





}
