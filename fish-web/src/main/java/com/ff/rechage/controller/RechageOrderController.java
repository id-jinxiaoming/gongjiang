package com.ff.rechage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.rechage.model.Rechage;
import com.ff.rechage.model.RechageOrder;
import com.ff.rechage.model.bo.RechageOrderBO;
import com.ff.rechage.service.RechageOrderService;
import com.ff.rechage.service.RechageService;
import com.ff.shop.model.Shop;
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
 * @Description 充值记录
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/rechage/order")
public class RechageOrderController extends BaseController{
	@Reference
	private RechageOrderService rechageOrderService;



	@RequiresPermissions(value = "rechage:order:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {


		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("rechage/order/list",map);

	}


	@RequiresPermissions(value = "rechage:order:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<RechageOrderBO> page = getPage(request);
		RechageOrder model=new RechageOrder();
		model.setOrderSn(key);
		List<RechageOrderBO> list= rechageOrderService.selectList(page,model);
		page.setRecords(list);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",page);
		return resultMap;
	}



	

}
