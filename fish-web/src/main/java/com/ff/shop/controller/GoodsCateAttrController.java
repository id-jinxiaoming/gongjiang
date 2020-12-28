package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.JsonTreeData;
import com.ff.common.util.JsonUtils;
import com.ff.shop.model.GoodsCate;
import com.ff.shop.model.GoodsCateAttr;
import com.ff.shop.service.GoodsCateAttrService;
import com.ff.shop.service.GoodsCateService;
import org.apache.commons.lang3.StringUtils;
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
 * @Description 分类属性
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/goodsCateAttr")
public class GoodsCateAttrController extends BaseController{
	@Reference
	private GoodsCateAttrService goodsCateAttrService;
	

	@RequiresPermissions(value = "shop:goodsCateAttr:list")
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("id")String id,ModelMap map) {
		map.put("id",id);
		return new ModelAndView("shop/goodsCateAttr/list",map);

	}


	@RequiresPermissions(value = "shop:goodsCateAttr:list")
	@RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request ,@PathVariable("id")String id) {
		Page<GoodsCateAttr> page = getPage(request);
		EntityWrapper<GoodsCateAttr> ew=new EntityWrapper();
		ew.like("cate_id","%"+id+"%");
		Page<GoodsCateAttr> data= goodsCateAttrService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();

		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsCateAttr:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		goodsCateAttrService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsCateAttr:add")
	@RequestMapping(value="/add/{id}",method=RequestMethod.GET)
	public ModelAndView add(@PathVariable("id")Integer id, ModelMap map){
		map.put("id", id);
		return new ModelAndView("/shop/goodsCateAttr/add",map);
	}
	@RequiresPermissions("shop:goodsCateAttr:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(GoodsCateAttr model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(!model.getOpts().equals(""))
		{
			String[] opts=model.getOpts().split(",");

			model.setOpts(JsonUtils.objectToJson(opts));
		}
		if(goodsCateAttrService.insert(model)!=0){

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

	@RequiresPermissions("shop:goodsCateAttr:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		GoodsCateAttr model = goodsCateAttrService.findByPrimaryKey(id);

		if(!model.getOpts().equals(""))
		{
			List<String> opts=JsonUtils.jsonToList(model.getOpts(),String.class);
			model.setOpts(StringUtils.join(opts.toArray(),','));

		}

		map.put("item", model);
		return new ModelAndView("/shop/goodsCateAttr/edit",map);

	}
	@RequiresPermissions("shop:goodsCateAttr:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(GoodsCateAttr t){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(!t.getOpts().equals(""))
		{
			String[] opts=t.getOpts().split(",");

			t.setOpts(JsonUtils.objectToJson(opts));
		}
		if(goodsCateAttrService.updateByPrimaryKey(t)!=0){

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
