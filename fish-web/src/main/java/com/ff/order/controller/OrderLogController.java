package com.ff.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.StringUtils;
import com.ff.order.model.OrderLog;
import com.ff.order.model.bo.OrderShippingBO;
import com.ff.order.service.OrderLogService;
import com.ff.order.service.OrderShippingService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order/orderLog")
public class OrderLogController extends BaseController{

	@Reference
	private OrderLogService orderLogService;

	@RequiresPermissions(value = "order:orderLog:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {



		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("order/orderLog/list",map);

	}


	@RequiresPermissions(value = "order:orderLog:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<OrderLog> page = getPage(request);
		EntityWrapper ew=new EntityWrapper();
		if(!StringUtils.isEmpty(key)){
			ew.like("order_id","%"+key);
		}
		ew.orderBy("id",false);
		Page<OrderLog> data= orderLogService.selectPage(page, ew);
		Map<String, Object> resultMap = new LinkedHashMap();

		resultMap.put("data",data);
		return resultMap;
	}



}
