package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.JsonTreeData;
import com.ff.shop.model.Goods;
import com.ff.shop.model.GoodsAlbum;
import com.ff.shop.model.GoodsCate;
import com.ff.shop.model.GoodsOptional;
import com.ff.shop.service.*;
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
import java.util.*;

/**
 * @Author yuzhongxin
 * @Description 商品选项
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/goodsOptional")
public class GoodsOptionController extends BaseController{
	@Reference
	private GoodsService goodsService;

	@Reference
	private GoodsOptionalService goodsOptionalService;

	@Reference
	private GoodsOptionalTypeService goodsOptionalTypeService;



	@RequiresPermissions(value = "shop:goodsOptional:list")
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("id")Integer id,ModelMap map) {
		map.put("goodsId",id);
		Goods goods= goodsService.findByPrimaryKey(id.toString());
		if(goods!=null)
		map.put("goodsName",goods.getGoodsName());
		map.put("typeList", goodsOptionalTypeService.selectAll());
		return new ModelAndView("shop/goodsOptional/list",map);

	}


	@RequiresPermissions(value = "shop:goodsOptional:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(Integer id,Integer typeId,HttpServletRequest request) {
		Page<GoodsOptional> page = getPage(request);
		EntityWrapper<GoodsOptional> ew=new EntityWrapper();
		ew.eq("goods_id",id);
		if(typeId!=null&&typeId>0)
		{
			ew.eq("type_id",typeId);
		}
		ew.orderBy("id",false);
		Page<GoodsOptional> data= goodsOptionalService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();

		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsOptional:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		goodsOptionalService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsOptional:add")
	@RequestMapping(value="/add/{id}",method=RequestMethod.GET)
	public ModelAndView add(@PathVariable("id")Integer id,ModelMap map){
		map.put("goodsId",id);
		map.put("typeList", goodsOptionalTypeService.selectAll());
		return new ModelAndView("shop/goodsOptional/add",map);
	}
	@RequiresPermissions("shop:goodsOptionalType:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(GoodsOptional model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(goodsOptionalService.insert(model)!=0){

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


	@RequiresPermissions("shop:goodsOptional:edit")
	@RequestMapping(value="/edit/{id}/{optionalId}",method=RequestMethod.GET)
	public ModelAndView add(@PathVariable("id")Integer id,@PathVariable("optionalId")Integer optionalId,ModelMap map){
		GoodsOptional optional= goodsOptionalService.findByPrimaryKey(optionalId.toString());
		map.put("optional",optional);
		map.put("goodsId",id);
		map.put("typeList", goodsOptionalTypeService.selectAll());
		return new ModelAndView("shop/goodsOptional/edit",map);
	}
	@RequiresPermissions("shop:goodsOptionalType:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(GoodsOptional model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(goodsOptionalService.updateByPrimaryKey(model)!=0){

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
