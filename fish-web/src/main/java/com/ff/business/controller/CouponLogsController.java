package com.ff.business.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.JsonTreeData;
import com.ff.coupon.model.Coupon;
import com.ff.coupon.model.CouponLogs;
import com.ff.coupon.service.CouponLogsService;
import com.ff.coupon.service.CouponService;
import com.ff.shop.model.Brand;
import com.ff.shop.model.GoodsCate;
import com.ff.shop.model.GoodsCateBrand;
import com.ff.shop.service.BrandService;
import com.ff.shop.service.GoodsCateBrandService;
import com.ff.shop.service.GoodsCateService;
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
 * @Description
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/coupon/couponLogs")
public class CouponLogsController extends BaseController{
	@Reference
	private CouponLogsService couponLogsService;

	@RequiresPermissions(value = "coupon:couponLogs:list")
	@RequestMapping(value = "/list/{couponId}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("couponId")String couponId,ModelMap map) {
		map.put("couponId", couponId);
		return new ModelAndView("coupon/couponLogs/list",map);

	}


	@RequiresPermissions(value = "coupon:couponLogs:list")
	@RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, @PathVariable("id")String id) {

		Page<CouponLogs> page = getPage(request);
		EntityWrapper<CouponLogs> ew=new EntityWrapper();
		ew.eq("coupon_id",id);
		ew.orderBy("log_id",false);
		Page<CouponLogs> data= couponLogsService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}



}
