package com.ff.business.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.JsonTreeData;
import com.ff.coupon.model.Coupon;
import com.ff.coupon.service.CouponService;
import com.ff.shop.model.*;
import com.ff.shop.service.BrandService;
import com.ff.shop.service.GoodsCateBrandService;
import com.ff.shop.service.GoodsCateService;
import com.ff.users.model.Users;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 商品分类
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/coupon/coupon")
public class CouponController extends BaseController{
	@Reference
	private CouponService couponService;

	@RequiresPermissions(value = "coupon:coupon:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {

		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("coupon/coupon/list",map);

	}


	@RequiresPermissions(value = "coupon:coupon:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Coupon> page = getPage(request);
		EntityWrapper<Coupon> ew=new EntityWrapper();
		ew.like("title","%"+key+"%");
		ew.orderBy("coupon_id",false);
		Page<Coupon> data= couponService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("coupon:coupon:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		couponService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("coupon:coupon:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView add(ModelMap map){

		return new ModelAndView("/coupon/coupon/add",map);
	}
	@RequiresPermissions("coupon:coupon:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(Coupon model,String username,String[] photo){
		//
		Map<String, Object> resultMap = new LinkedHashMap();


		Integer id=couponService.insert(model);
		if(id!=0){



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

	@RequiresPermissions("coupon:coupon:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		Coupon model = couponService.findByPrimaryKey(id);
		map.put("item", model);

		return new ModelAndView("/coupon/coupon/edit",map);

	}
	@RequiresPermissions("coupon:coupon:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(Coupon model,String username,String[] photo){
		Map<String, Object> resultMap = new LinkedHashMap();


		if(couponService.updateByPrimaryKey(model)!=0){

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
