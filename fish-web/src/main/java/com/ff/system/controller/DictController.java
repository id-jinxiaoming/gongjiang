package com.ff.system.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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


import com.ff.common.base.BaseController;
import com.ff.system.model.Dict;
import com.ff.system.service.DictService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/system/dict")
public class DictController extends BaseController{
	@Reference
	private DictService dictService;
	

	@RequiresPermissions(value = "system:dict:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {
		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("system/dict/list",map);

	}


	@RequiresPermissions(value = "system:dict:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Dict> page = getPage(request);
		EntityWrapper<Dict> ew=new EntityWrapper();
		ew.like("code","%"+key+"%");
		Page<Dict> data= dictService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("system:dict:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		dictService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("system:dict:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/system/dict/add";
	}
	@RequiresPermissions("system:dict:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(Dict dict){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(dictService.insert(dict)!=0){

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

	@RequiresPermissions("system:dict:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		Dict dict = dictService.findByPrimaryKey(id);
		map.put("item", dict);
		return new ModelAndView("/system/dict/edit",map);

	}
	@RequiresPermissions("system:dict:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(Dict dict){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(dictService.updateByPrimaryKey(dict)!=0){

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
