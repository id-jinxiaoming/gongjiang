package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.shop.model.Shop;
import com.ff.shop.model.ShopCate;
import com.ff.shop.model.ShopOrder;
import com.ff.shop.model.bo.ShopOrderBO;
import com.ff.shop.service.ShopCateService;
import com.ff.shop.service.ShopOrderService;
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
 * @Description 分类属性
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/shopOrder")
public class ShopOrderController extends BaseController{
	@Reference
	private ShopOrderService shopOrderService;
	

	@RequiresPermissions(value = "shop:shopOrder:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {
		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);

		return new ModelAndView("shop/shopOrder/list",map);

	}


	@RequiresPermissions(value = "shop:shopOrder:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<ShopOrderBO> page = getPage(request);

		List<ShopOrderBO> data= shopOrderService.selectListByPage(page);
		page.setRecords(data);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",page);
		return resultMap;
	}



	

}
