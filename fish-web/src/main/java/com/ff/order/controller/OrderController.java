package com.ff.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.DateUtils;
import com.ff.order.model.*;
import com.ff.order.model.bo.OrderGoodsBO;
import com.ff.order.model.bo.OrderShippingBO;
import com.ff.order.service.*;
import com.ff.shop.model.ShippingCarrier;
import com.ff.shop.model.ShippingMethod;
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
import java.text.SimpleDateFormat;
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
@RequestMapping("/order/order")
public class OrderController extends BaseController{
	@Reference
	private OrderService orderService;
	@Reference
	private OrderConsigneeService orderConsigneeService;
	@Reference
	private DictService dictService;
	@Reference
	private OrderGoodsService orderGoodsService;

	@Reference
	private OrderGoodsOptionalService orderGoodsOptionalService;

	@Reference
	private OrderLogService orderLogService;

	@Reference
	private OrderShippingService orderShippingService;

	@Reference
	private ShippinpCarrierService shippinpCarrierService;


	@RequiresPermissions(value = "order:order:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {
		List<Dict> dict= dictService.selectByCode("order_status");
		map.put("orderStatus",dict);

		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("order/order/list",map);

	}


	@RequiresPermissions(value = "order:order:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Map<String,Object>> page = getPage(request);
		Page<Map<String,Object>> data= orderService.selectPageByOrderId(page, key);
		Map<String, Object> resultMap = new LinkedHashMap();

		resultMap.put("data",data);
		return resultMap;
	}




	@RequiresPermissions("order:order:show")
	@RequestMapping(value="/show/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		Map<String, Object> model = orderService.findByOrderId(id);
		Long createdDate=Long.parseLong(model.get("created_date").toString())*1000;
		model.put("created_date",DateUtils.stampToDate(createdDate.toString()));
		Long paymentDate=Long.parseLong(model.get("payment_date").toString())*1000;
		model.put("payment_date",DateUtils.stampToDate(paymentDate.toString()));
		List<Dict> dicts= dictService.selectByCode("order_status");
		for (Dict d:dicts) {
			if(d.getValue().equals(model.get("order_status").toString())){
				model.put("order_status_name",d.getName());
			}
		}
		map.put("item", model);
		//获取订单商品列表
		OrderGoods orderGoodsMap=new OrderGoods();
		orderGoodsMap.setOrderId(model.get("order_id").toString());
		List<OrderGoods> orderGoodsList=orderGoodsService.selectByT(orderGoodsMap);

		for (OrderGoods orderGoods:orderGoodsList){
			OrderGoodsOptional optional=new OrderGoodsOptional();
			optional.setMapId(orderGoods.getId());
			List<OrderGoodsOptional> optionals=orderGoodsOptionalService.selectByT(optional);
			for (OrderGoodsOptional o:optionals){
				String name="["+o.getOptType()+":"+o.getOptText()+"]";
				orderGoods.setGoodsName(orderGoods.getGoodsName()+" "+name);
			}

		}
		map.put("goods", orderGoodsList);
		//获取订单日志
		OrderLog logMap=new OrderLog();
		logMap.setOrderId(id);
		List<OrderLog> logs= orderLogService.selectByT(logMap);
		map.put("logs", logs);
		//获取物流
		List<OrderShippingBO> shipps=orderShippingService.selectByOrdreId(id);
		map.put("shipping", shipps);
		return new ModelAndView("/order/order/show",map);

	}



	@RequiresPermissions(value = "order:order:setAddress")
	@RequestMapping(value="/setAddress/{id}",method=RequestMethod.GET)
	public ModelAndView setAddress(@PathVariable("id")String id, ModelMap map) {
			OrderConsignee orderConsigneeMap=new OrderConsignee();
		orderConsigneeMap.setOrderId(id);
		OrderConsignee orderConsignee=orderConsigneeService.findOne(orderConsigneeMap);
		map.put("item", orderConsignee);
		map.put("orderId", id);
		return new ModelAndView("order/order/setAddress",map);

	}


	@RequiresPermissions("order:order:setAddress")
	@RequestMapping(value="/setAddress",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doSetAddress(OrderConsignee orderConsignee,String reason){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(orderConsigneeService.updateByPrimaryKey(orderConsignee)>0){
			resultMap.put("message","操作成功");
			resultMap.put("status",200);
			//写操作日志
			OrderLog log=new OrderLog();
			log.setOrderId(orderConsignee.getOrderId());
			log.setCause(reason);
			log.setOperate("修改收货人");
			log.setCreateTime(new Date());
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			log.setAccount(user.getAccount());
			orderLogService.insert(log);
		}
		else{
			resultMap.put("message","操作失败");
			resultMap.put("status",500);
		}
		return resultMap;
	}

	@RequiresPermissions(value = "order:order:setMoney")
	@RequestMapping(value="/setMoney/{id}",method=RequestMethod.GET)
	public ModelAndView setMoney(@PathVariable("id")String id, ModelMap map) {
		Order orderMap=new Order();
		orderMap.setOrderId(id);
		Order order=orderService.findOne(orderMap);
		map.put("item", order);
		return new ModelAndView("order/order/setMoney",map);

	}


	@RequiresPermissions("order:order:setMoney")
	@RequestMapping(value="/setMoney",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doSetMoney(Order order,String reason){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(orderService.updateByPrimaryKey(order)>0){
			resultMap.put("message","操作成功");
			resultMap.put("status",200);
			//写操作日志
			OrderLog log=new OrderLog();
			log.setOrderId(order.getOrderId());
			log.setCause(reason);
			log.setOperate("修改订单总金额为:"+order.getOrderAmount());
			log.setCreateTime(new Date());
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			log.setAccount(user.getAccount());
			orderLogService.insert(log);
		}
		else{
			resultMap.put("message","操作失败");
			resultMap.put("status",500);
		}
		return resultMap;
	}

	@RequiresPermissions(value = "order:order:setCancel")
	@RequestMapping(value="/setCancel/{id}",method=RequestMethod.GET)
	public ModelAndView setCancel(@PathVariable("id")String id, ModelMap map) {
		Order orderMap=new Order();
		orderMap.setOrderId(id);
		Order order=orderService.findOne(orderMap);
		map.put("item", order);
		return new ModelAndView("order/order/setCancel",map);

	}


	@RequiresPermissions("order:order:setCancel")
	@RequestMapping(value="/setCancel",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doSetCancel(Order order,String reason){
		Map<String, Object> resultMap = new LinkedHashMap();
		order.setOrderStatus(0);
		if(orderService.updateByPrimaryKey(order)>0){
			resultMap.put("message","操作成功");
			resultMap.put("status",200);
			//写操作日志
			OrderLog log=new OrderLog();
			log.setOrderId(order.getOrderId());
			log.setCause(reason);
			log.setOperate("取消了该笔订单交易");
			log.setCreateTime(new Date());
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			log.setAccount(user.getAccount());
			orderLogService.insert(log);
		}
		else{
			resultMap.put("message","操作失败");
			resultMap.put("status",500);
		}
		return resultMap;
	}

	@RequiresPermissions(value = "order:order:setRecover")
	@RequestMapping(value="/setRecover/{id}",method=RequestMethod.GET)
	public ModelAndView setRecover(@PathVariable("id")String id, ModelMap map) {
		Order orderMap=new Order();
		orderMap.setOrderId(id);
		Order order=orderService.findOne(orderMap);
		map.put("item", order);
		return new ModelAndView("order/order/setRecover",map);

	}

	@RequiresPermissions("order:order:setRecover")
	@RequestMapping(value="/setRecover",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doSetRecover(Order order,String reason){
		order.setOrderStatus(1);
		Map<String, Object> resultMap = new LinkedHashMap();
		if(orderService.updateByPrimaryKey(order)>0){
			resultMap.put("message","操作成功");
			resultMap.put("status",200);
			//写操作日志
			OrderLog log=new OrderLog();
			log.setOrderId(order.getOrderId());
			log.setCause(reason);
			log.setOperate("恢复了该笔订单交易");
			log.setCreateTime(new Date());
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			log.setAccount(user.getAccount());
			orderLogService.insert(log);
		}
		else{
			resultMap.put("message","操作失败");
			resultMap.put("status",500);
		}
		return resultMap;
	}



	@RequiresPermissions(value = "order:order:setShipping")
	@RequestMapping(value="/setShipping/{id}",method=RequestMethod.GET)
	public ModelAndView setShipping(@PathVariable("id")String id, ModelMap map) {
		ShippingCarrier maps=new ShippingCarrier();
		maps.setClosed(0);
		List<ShippingCarrier> list= shippinpCarrierService.selectByT(maps);

		map.put("shippingCarrier", list);
		map.put("orderId", id);
		return new ModelAndView("order/order/setShipping",map);

	}


	@RequiresPermissions("order:order:setShipping")
	@RequestMapping(value="/setShipping",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doSetShipping(OrderShipping orderShipping){
		orderShipping.setCreateTime(new Date());
		Map<String, Object> resultMap = new LinkedHashMap();

		orderShippingService.insert(orderShipping);

		Order orderMap=new Order();
		orderMap.setOrderId(orderShipping.getOrderId());
		Order order=orderService.findOne(orderMap);
		order.setOrderStatus(3);

		if(orderService.updateByPrimaryKey(order)>0){
			resultMap.put("message","操作成功");
			resultMap.put("status",200);

		}
		else{
			resultMap.put("message","操作失败");
			resultMap.put("status",500);
		}
		return resultMap;
	}
}
