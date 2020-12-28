package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.JsonUtils;
import com.ff.shop.model.GoodsCateAttr;
import com.ff.shop.model.ShopCoupon;
import com.ff.shop.model.ShopCouponLogs;
import com.ff.shop.service.GoodsCateAttrService;
import com.ff.shop.service.ShopCouponService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/shopCoupon")
public class ShopCouponController extends BaseController{
	@Reference
	private ShopCouponService shopCouponService;
	

	@RequiresPermissions(value = "shop:shopCoupon:list")
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("id")String id,ModelMap map) {
		map.put("id",id);
		return new ModelAndView("shop/shopCoupon/list",map);

	}


	@RequiresPermissions(value = "shop:shopCoupon:list")
	@RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request ,@PathVariable("id")String id) {
		Page<ShopCoupon> page = getPage(request);
		EntityWrapper<ShopCoupon> ew=new EntityWrapper();
		ew.like("shop_id","%"+id+"%");
		Page<ShopCoupon> data= shopCouponService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();

		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:shopCoupon:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		shopCouponService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("shop:shopCoupon:add")
	@RequestMapping(value="/add/{id}",method=RequestMethod.GET)
	public ModelAndView add(@PathVariable("id")Integer id, ModelMap map){
		map.put("id", id);
		return new ModelAndView("/shop/shopCoupon/add",map);
	}
	@RequiresPermissions("shop:shopCoupon:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(ShopCoupon model){
		Map<String, Object> resultMap = new LinkedHashMap();

		if(shopCouponService.insert(model)!=0){

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

	@RequiresPermissions("shop:shopCoupon:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		ShopCoupon model = shopCouponService.findByPrimaryKey(id);



		map.put("item", model);
		return new ModelAndView("/shop/shopCoupon/edit",map);

	}
	@RequiresPermissions("shop:shopCoupon:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(ShopCoupon t){
		Map<String, Object> resultMap = new LinkedHashMap();

		if(shopCouponService.updateByPrimaryKey(t)!=0){

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
