package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.JsonUtils;
import com.ff.shop.model.GoodsCateAttr;
import com.ff.shop.model.ShopCate;
import com.ff.shop.service.GoodsCateAttrService;
import com.ff.shop.service.ShopCateService;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/shop/shopCate")
public class ShopCateController extends BaseController{
	@Reference
	private ShopCateService shopCateService;
	

	@RequiresPermissions(value = "shop:shopCate:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelMap map) {

		return new ModelAndView("shop/shopCate/list",map);

	}


	@RequiresPermissions(value = "shop:shopCate:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request ) {
		Page<ShopCate> page = getPage(request);
		EntityWrapper<ShopCate> ew=new EntityWrapper();
		Page<ShopCate> data= shopCateService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();

		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:shopCate:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		shopCateService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("shop:shopCate:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView add( ModelMap map){

		return new ModelAndView("/shop/shopCate/add",map);
	}
	@RequiresPermissions("shop:shopCate:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(ShopCate model){
		Map<String, Object> resultMap = new LinkedHashMap();

		if(shopCateService.insert(model)!=0){

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

	@RequiresPermissions("shop:shopCate:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		ShopCate model = shopCateService.findByPrimaryKey(id);
		map.put("item", model);
		return new ModelAndView("/shop/shopCate/edit",map);

	}
	@RequiresPermissions("shop:shopCate:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(ShopCate t){
		Map<String, Object> resultMap = new LinkedHashMap();

		if(shopCateService.updateByPrimaryKey(t)!=0){

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
