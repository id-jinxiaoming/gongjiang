package com.ff.config.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.JsonUtils;
import com.ff.shop.model.Province;
import com.ff.shop.model.ShippingCarrier;
import com.ff.shop.model.ShippingMethod;
import com.ff.shop.model.ex.ShipingParams;
import com.ff.shop.service.ProvinceService;
import com.ff.shop.service.ShippingMethodService;
import com.ff.shop.service.ShippinpCarrierService;
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
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 物流承运商方式
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/config/shippingMethod")
public class ShippingMethodController extends BaseController{
	@Reference
	private ShippingMethodService shippingMethodService;

	@Reference
	private ProvinceService provinceService;



	@RequiresPermissions(value = "config:shippingMethod:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {


		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("config/shippingMethod/list",map);

	}


	@RequiresPermissions(value = "config:shippingMethod:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<ShippingMethod> page = getPage(request);
		EntityWrapper<ShippingMethod> ew=new EntityWrapper();
		ew.orderBy("id",false);
		Page<ShippingMethod> data= shippingMethodService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}


	@RequiresPermissions("config:shippingMethod:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		shippingMethodService.deleteByPrimaryKey(id);
		ShippingCarrier map=new ShippingCarrier();

		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;

	}

	@RequiresPermissions("config:shippingMethod:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/config/shippingMethod/add";
	}
	@RequiresPermissions("config:shippingMethod:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(ShippingMethod model){
		model.setParams( StringEscapeUtils.unescapeHtml(model.getParams()));
		Map<String, Object> resultMap = new LinkedHashMap();
		if(shippingMethodService.insert(model)!=0){

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

	@RequiresPermissions("config:shippingMethod:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){


		ShippingMethod model = shippingMethodService.findByPrimaryKey(id);
		List<ShipingParams> params= JsonUtils.jsonToList(model.getParams(), ShipingParams.class);
		List<Province> provinces= provinceService.selectAll();
		map.put("provinces", provinces);
		map.put("params", params);
		map.put("item", model);
		return new ModelAndView("/config/shippingMethod/edit",map);

	}
	@RequiresPermissions("config:shippingMethod:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(ShippingMethod model){
		model.setParams( StringEscapeUtils.unescapeHtml(model.getParams()));
		Map<String, Object> resultMap = new LinkedHashMap();
		if(shippingMethodService.updateByPrimaryKey(model)!=0){

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


	@RequestMapping(value="/params",method=RequestMethod.GET)
	public ModelAndView params(ModelMap map){
		List<Province> list= provinceService.selectAll();
		map.put("province",list);
		return new ModelAndView("/config/shippingMethod/params",map);
	}










}
