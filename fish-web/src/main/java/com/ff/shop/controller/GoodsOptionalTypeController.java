package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.shop.model.GoodsOptionalType;
import com.ff.shop.service.GoodsOptionalTypeService;
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
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 选项类型
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/goodsOptionalType")
public class GoodsOptionalTypeController extends BaseController{
	@Reference
	private GoodsOptionalTypeService goodsOptionalTypeService;
	

	@RequiresPermissions(value = "shop:goodsOptionalType:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {
		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("shop/goodsOptionalType/list",map);

	}


	@RequiresPermissions(value = "shop:brand:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<GoodsOptionalType> page = getPage(request);
		EntityWrapper<GoodsOptionalType> ew=new EntityWrapper();
		ew.like("name","%"+key+"%");
		Page<GoodsOptionalType> data= goodsOptionalTypeService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsOptionalType:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		goodsOptionalTypeService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsOptionalType:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/shop/goodsOptionalType/add";
	}
	@RequiresPermissions("shop:goodsOptionalType:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(GoodsOptionalType model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(goodsOptionalTypeService.insert(model)!=0){

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

	@RequiresPermissions("shop:goodsOptionalType:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		GoodsOptionalType model = goodsOptionalTypeService.findByPrimaryKey(id);
		map.put("item", model);
		return new ModelAndView("/shop/goodsOptionalType/edit",map);

	}
	@RequiresPermissions("shop:goodsOptionalType:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(GoodsOptionalType t){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(goodsOptionalTypeService.updateByPrimaryKey(t)!=0){

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
