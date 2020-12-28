package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.shop.model.*;
import com.ff.shop.service.*;
import com.ff.users.model.Users;
import com.ff.users.service.UsersService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author yuzhongxin
 * @Description 商家列表
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/shop")
public class ShopController extends BaseController{



    @Reference
    private ShopService shopService;
    @Reference
    private ShopCateService shopCateService;
    @Reference
    private UsersService usersService;
    @Reference
    private ShopImageService shopImageService;

    @RequiresPermissions(value = "shop:shop:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(String key,ModelMap map) {


        key= StringEscapeUtils.unescapeHtml(key);
        try {
            key = new String(key.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("key", key);
        return new ModelAndView("shop/shop/list",map);

    }


    @RequiresPermissions(value = "shop:shop:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doList(HttpServletRequest request, String key) {

        Page<Shop> page = getPage(request);
        EntityWrapper<Shop> ew=new EntityWrapper();
        ew.like("title","%"+key+"%");
        ew.orderBy("shop_id",false);
        Page<Shop> data= shopService.selectPage(page,ew);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("data",data);
        return resultMap;
    }

    @RequiresPermissions("shop:shop:delete")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String[] id) {
        shopService.deleteByPrimaryKey(id);
        Map<String, Object> resultMap = new LinkedHashMap();
        resultMap.put("msg","删除成功");
        resultMap.put("status",200);
        return resultMap;
    }

    @RequiresPermissions("shop:shop:add")
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public ModelAndView add(ModelMap map){

        map.put("cate",  shopCateService.selectAll());
        return new ModelAndView("/shop/shop/add",map);
    }
    @RequiresPermissions("shop:shop:add")
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doAdd(Shop model,String username,String[] photo){
       //
        Map<String, Object> resultMap = new LinkedHashMap();
        Users map=new Users();
        map.setUsername(username);
        map.setStatus(1);
        Users user=usersService.findOne(map);
        if(user==null){
            resultMap.put("status", 500);
            resultMap.put("message", "此用户不存在");
            return resultMap;

        }
        Shop mapShop=new Shop();
        mapShop.setUserId(user.getUserId());
        Shop shop=shopService.findOne(mapShop);
        if(shop!=null){
            resultMap.put("status", 500);
            resultMap.put("message", "此用户已开通商家，不能重复开通");
            return resultMap;
        }
        model.setUserId(user.getUserId());
        model.setUsername(user.getUsername());
        model.setClosed(0);
        model.setDescription(StringEscapeUtils.unescapeHtml(model.getDescription()));
        model.setNote(StringEscapeUtils.unescapeHtml(model.getNote()));

        Integer id=shopService.insert(model);
        if(id!=0){

            if(photo!=null)
            {
                for (String item:photo) {
                    ShopImage img=new ShopImage();
                    img.setImgUrl(item);
                    img.setShopId(id);

                    shopImageService.insert(img);
                }
            }

            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        }
        else
        {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }

    @RequiresPermissions("shop:shop:edit")
    @RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
        Shop model = shopService.findByPrimaryKey(id);
        map.put("item", model);
        try{
            Users user=usersService.findByPrimaryKey(model.getUserId().toString());
            map.put("user",  user.getUsername());
        }catch (Exception e){

        }

        map.put("cate",  shopCateService.selectAll());
        ShopImage mapImage=new ShopImage();
        mapImage.setShopId(model.getShopId());
        List<ShopImage> list=shopImageService.selectByT(mapImage);
        map.put("img",  list);
        return new ModelAndView("/shop/shop/edit",map);

    }
    @RequiresPermissions("shop:shop:edit")
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doEdit(Shop model,String username,String[] photo){
        Map<String, Object> resultMap = new LinkedHashMap();
        Users map=new Users();
        map.setUsername(username);
        map.setStatus(1);
        Users user=usersService.findOne(map);
        if(user==null){
            resultMap.put("status", 500);
            resultMap.put("message", "此用户不存在");
            return resultMap;

        }
        Shop mapShop=new Shop();
        mapShop.setUserId(user.getUserId());
        mapShop.setUsername(user.getUsername());
        Shop shop=shopService.findOne(mapShop);
        if(shop!=null&&shop.getUserId()!=user.getUserId()){
            resultMap.put("status", 500);
            resultMap.put("message", "此用户已开通商家，不能重复开通");
            return resultMap;

        }
        model.setUserId(user.getUserId());
        model.setUsername(user.getUsername());
        model.setDescription(StringEscapeUtils.unescapeHtml(model.getDescription()));
        model.setNote(StringEscapeUtils.unescapeHtml(model.getNote()));

        if(shopService.updateByPrimaryKey(model)!=0){

            if(photo!=null)
            {
                //删除之前数据
                shopImageService.deleteByShopId(model.getShopId());
                for (String item:photo) {
                    ShopImage img=new ShopImage();
                    img.setImgUrl(item);
                    img.setShopId(model.getShopId());
                    shopImageService.insert(img);
                }
            }

            resultMap.put("status", 200);
            resultMap.put("message", "操作成功");
        }
        else
        {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败");
        }
        return resultMap;
    }






}
