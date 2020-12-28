package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.common.model.JsonTreeData;
import com.ff.shop.model.Brand;
import com.ff.shop.model.GoodsCate;
import com.ff.shop.model.GoodsCateBrand;
import com.ff.shop.service.BrandService;
import com.ff.shop.service.GoodsCateBrandService;
import com.ff.shop.service.GoodsCateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 商品分类
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/goodsCate")
public class GoodsCateController extends BaseController{
	@Reference
	private GoodsCateService goodsCateService;
	@Reference
	private GoodsCateBrandService goodsCateBrandService;
	@Reference
	private BrandService brandService;

	@RequiresPermissions(value = "shop:goodsCate:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelMap map) {

		return new ModelAndView("shop/goodsCate/list",map);

	}


	@RequiresPermissions(value = "shop:goodsCate:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request) {
		List<JsonTreeData> data= goodsCateService.selectTree();
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsCate:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		goodsCateService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsCate:add")
	@RequestMapping(value="/add/{id}",method=RequestMethod.GET)
	public ModelAndView add(@PathVariable("id")Integer id, ModelMap map){
		List<Brand> list= brandService.selectAll();
		map.put("Brand", list);
		map.put("id", id);
		return new ModelAndView("/shop/goodsCate/add",map);
	}
	@RequiresPermissions("shop:goodsCate:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(GoodsCate model,@RequestParam("brandIds")Integer[] brandIds){
		Map<String, Object> resultMap = new LinkedHashMap();
		int id=goodsCateService.insert(model);
		if(id!=0){
			if(brandIds!=null&&brandIds.length>0)
			{
				for (Integer brandId:brandIds) {
					GoodsCateBrand brand=new GoodsCateBrand();
					brand.setBrandId(brandId);
					brand.setCateId(id);
					goodsCateBrandService.insert(brand);
				}
			}

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

	@RequiresPermissions("shop:goodsCate:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		List<Brand> list= brandService.selectAll();
		map.put("Brand", list);
		GoodsCateBrand brand=new GoodsCateBrand();
		brand.setCateId(Integer.parseInt(id));
		map.put("GoodsCateBrand", goodsCateBrandService.selectByT(brand));
		GoodsCate model = goodsCateService.findByPrimaryKey(id);
		map.put("item", model);
		return new ModelAndView("/shop/goodsCate/edit",map);

	}
	@RequiresPermissions("shop:goodsCate:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(GoodsCate t,@RequestParam("brandIds")Integer[] brandIds){

		Map<String, Object> resultMap = new LinkedHashMap();
		if(goodsCateService.updateByPrimaryKey(t)!=0){
			goodsCateBrandService.deleteByCateId(t.getCateId());
			if(brandIds!=null&&brandIds.length>0)
			{
				for (Integer brandId:brandIds) {
					GoodsCateBrand brand=new GoodsCateBrand();
					brand.setBrandId(brandId);
					brand.setCateId(t.getCateId());
					goodsCateBrandService.insert(brand);
				}
			}
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
