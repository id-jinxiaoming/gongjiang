package com.ff.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.DateUtils;
import com.ff.order.model.*;
import com.ff.order.model.bo.OrderShippingBO;
import com.ff.order.service.*;
import com.ff.shop.model.ShippingCarrier;
import com.ff.shop.service.ShippinpCarrierService;
import com.ff.system.model.Dict;
import com.ff.system.model.User;
import com.ff.system.service.DictService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order/orderShipping")
public class OrderShippingController extends BaseController{

	@Reference
	private OrderShippingService orderShippingService;

	@RequiresPermissions(value = "order:orderShipping:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {



		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("order/orderShipping/list",map);

	}


	@RequiresPermissions(value = "order:orderShipping:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<OrderShippingBO> page = getPage(request);
		List<OrderShippingBO> data= orderShippingService.selectByPage(page, key);
		Map<String, Object> resultMap = new LinkedHashMap();
		page.setRecords(data);
		resultMap.put("data",page);
		return resultMap;
	}



}
