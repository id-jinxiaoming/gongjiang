package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.shop.model.Brand;
import com.ff.shop.model.GoodsCateBrand;
import com.ff.shop.service.BrandService;
import com.ff.shop.service.GoodsCateBrandService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description 商品品牌
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/brand")
public class BrandController extends BaseController{
	@Reference
	private BrandService brandService;



	@RequiresPermissions(value = "shop:brand:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {


		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("shop/brand/list",map);

	}


	@RequiresPermissions(value = "shop:brand:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Brand> page = getPage(request);
		EntityWrapper<Brand> ew=new EntityWrapper();
		ew.like("brand_name","%"+key+"%");
		Page<Brand> data= brandService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:brand:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		brandService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("shop:brand:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/shop/brand/add";
	}
	@RequiresPermissions("shop:brand:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(Brand model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(brandService.insert(model)!=0){

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

	@RequiresPermissions("shop:brand:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		Brand model = brandService.findByPrimaryKey(id);
		map.put("item", model);
		return new ModelAndView("/shop/brand/edit",map);

	}
	@RequiresPermissions("shop:brand:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(Brand brand){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(brandService.updateByPrimaryKey(brand)!=0){

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
