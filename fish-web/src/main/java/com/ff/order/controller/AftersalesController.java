package com.ff.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.DateUtils;
import com.ff.order.model.*;
import com.ff.order.model.bo.AftersalesBO;
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

/**
 * @Author yuzhongxin
 * @Description 订单管理
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/order/aftersales")
public class AftersalesController extends BaseController{
	@Reference
	private AftersalesService aftersalesService;
	@Reference
	private AftersalesMessageService aftersalesMessageService;


	@RequiresPermissions(value = "order:aftersales:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {

		return new ModelAndView("order/aftersales/list",map);

	}


	@RequiresPermissions(value = "order:aftersales:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<AftersalesBO> page = getPage(request);
		Page<AftersalesBO> data= aftersalesService.selectByPage(page);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}



	@RequiresPermissions(value = "order:aftersales:logs")
	@RequestMapping(value = "/logs/{id}", method = RequestMethod.GET)
	public ModelAndView logs(@PathVariable("id")Integer id,ModelMap map) {
		map.put("id", id);
		return new ModelAndView("order/aftersales/logs",map);

	}


	@RequiresPermissions(value = "order:aftersales:logs")
	@RequestMapping(value = "/doLogs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogs(HttpServletRequest request, Integer id) {

		Page<AftersalesMessage> page = getPage(request);
		EntityWrapper<AftersalesMessage> ew=new EntityWrapper<>();
		ew.eq("as_id",id);
		Page<AftersalesMessage> data= aftersalesMessageService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions(value = "order:aftersales:check")
	@RequestMapping(value = "/check/{id}", method = RequestMethod.GET)
	public ModelAndView check(@PathVariable("id")Integer id,ModelMap map) {
		map.put("id", id);
		return new ModelAndView("order/aftersales/check",map);

	}

	@RequiresPermissions(value = "order:aftersales:check")
	@RequestMapping(value = "/doCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doCheck(HttpServletRequest request, Integer id,Integer status,String content) {
		Aftersales aftersales= aftersalesService.findByPrimaryKey(id.toString());
		Map<String, Object> resultMap = new LinkedHashMap();
		if(aftersales!=null){
			aftersales.setStatus(status);
			aftersalesService.updateByPrimaryKey(aftersales);
			AftersalesMessage aftersalesMessage=new AftersalesMessage();
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			aftersalesMessage.setAccount(user.getAccount());
			aftersalesMessage.setContent(content);
			aftersalesMessage.setCreateTime(new Date());
			aftersalesMessage.setAsId(id);
			aftersalesMessageService.insert(aftersalesMessage);
			resultMap.put("msg","操作成功");
			resultMap.put("status",200);
			return resultMap;
		}
		resultMap.put("msg","操作失败");
		resultMap.put("status",200);
		return resultMap;
	}



}
