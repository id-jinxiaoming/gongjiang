package com.ff.config.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.WeixinPayConfig;
import com.ff.common.util.JsonUtils;
import com.ff.shop.model.PaymentMethod;
import com.ff.shop.model.ShippingCarrier;
import com.ff.shop.service.PaymentMethodService;
import com.ff.shop.service.ShippinpCarrierService;
import com.ff.system.model.Dict;
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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 物流承运商方式
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/config/shippingCarrier")
public class ShippingCarrierController extends BaseController{
	@Reference
	private ShippinpCarrierService shippinpCarrierService;



	@RequiresPermissions(value = "config:shippingCarrier:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {


		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("config/shippingCarrier/list",map);

	}


	@RequiresPermissions(value = "config:shippingCarrier:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<ShippingCarrier> page = getPage(request);
		EntityWrapper<ShippingCarrier> ew=new EntityWrapper();
		ew.eq("closed",0);
		Page<ShippingCarrier> data= shippinpCarrierService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}


	@RequiresPermissions("config:shippingCarrier:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		ShippingCarrier map=new ShippingCarrier();
		for (String i:id) {
			map.setId(Integer.parseInt(i));
			map.setClosed(1);
			shippinpCarrierService.updateByPrimaryKey(map);

		}
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;

	}

	@RequiresPermissions("config:shippingCarrier:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/config/shippingCarrier/add";
	}
	@RequiresPermissions("config:shippingCarrier:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(ShippingCarrier model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(shippinpCarrierService.insert(model)!=0){

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

	@RequiresPermissions("config:shippingCarrier:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		ShippingCarrier shippingCarrier = shippinpCarrierService.findByPrimaryKey(id);
		map.put("item", shippingCarrier);
		return new ModelAndView("/config/shippingCarrier/edit",map);

	}
	@RequiresPermissions("config:shippingCarrier:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(ShippingCarrier model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(shippinpCarrierService.updateByPrimaryKey(model)!=0){

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
