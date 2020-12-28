package com.ff.config.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.AliPayConfig;
import com.ff.common.model.JsonTreeData;
import com.ff.common.model.WeixinPayConfig;
import com.ff.common.util.JsonUtils;
import com.ff.shop.model.*;
import com.ff.shop.service.BrandService;
import com.ff.shop.service.PaymentMethodService;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 支付方式
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/config/payment")
public class PaymentController extends BaseController{
	@Reference
	private PaymentMethodService paymentMethodService;



	@RequiresPermissions(value = "config:payment:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {


		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("config/payment/list",map);

	}


	@RequiresPermissions(value = "config:payment:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<PaymentMethod> page = getPage(request);
		EntityWrapper<PaymentMethod> ew=new EntityWrapper();
		Page<PaymentMethod> data= paymentMethodService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}



	@RequiresPermissions("config:payment:editWeixin")
	@RequestMapping(value="/editWeixin/{id}",method=RequestMethod.GET)
	public ModelAndView editWeixin(@PathVariable("id")Integer id, ModelMap map){
		PaymentMethod paymentMethod=paymentMethodService.findByPrimaryKey(id.toString());
		WeixinPayConfig cfg= JsonUtils.jsonToPojo(paymentMethod.getParams(),WeixinPayConfig.class);
		map.put("item",paymentMethod);
		map.put("cfg",cfg);
		return new ModelAndView("/config/payment/editWeixin",map);
	}
	@RequiresPermissions("config:payment:editWeixin")
	@RequestMapping(value="/editWeixin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEditWeixin(PaymentMethod model, WeixinPayConfig weixinPayConfig, HttpServletRequest request){
		Map<String, Object> resultMap = new LinkedHashMap();

		String cfg= JsonUtils.objectToJson(weixinPayConfig);
		model.setParams(cfg);
		Integer id=paymentMethodService.updateByPrimaryKey(model);
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




	@RequiresPermissions("config:payment:editAlipay")
	@RequestMapping(value="/editAlipay/{id}",method=RequestMethod.GET)
	public ModelAndView editAlipay(@PathVariable("id")Integer id, ModelMap map){
		PaymentMethod paymentMethod=paymentMethodService.findByPrimaryKey(id.toString());
		AliPayConfig cfg= JsonUtils.jsonToPojo(paymentMethod.getParams(),AliPayConfig.class);
		map.put("item",paymentMethod);
		map.put("cfg",cfg);
		return new ModelAndView("/config/payment/editAlipay",map);
	}
	@RequiresPermissions("config:payment:editAlipay")
	@RequestMapping(value="/editAlipay",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEditAlipay(PaymentMethod model, AliPayConfig aliPayConfig, HttpServletRequest request){
		Map<String, Object> resultMap = new LinkedHashMap();

		String cfg= JsonUtils.objectToJson(aliPayConfig);
		model.setParams(cfg);
		Integer id=paymentMethodService.updateByPrimaryKey(model);
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





}
