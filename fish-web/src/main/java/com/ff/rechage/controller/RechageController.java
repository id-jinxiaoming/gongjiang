package com.ff.rechage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.rechage.model.Rechage;
import com.ff.rechage.service.RechageService;
import com.ff.shop.model.Brand;
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
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 充值套餐
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/rechage/rechage")
public class RechageController extends BaseController{
	@Reference
	private RechageService rechageService;



	@RequiresPermissions(value = "rechage:rechage:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {


		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("rechage/rechage/list",map);

	}


	@RequiresPermissions(value = "rechage:rechage:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Rechage> page = getPage(request);
		EntityWrapper<Rechage> ew=new EntityWrapper();
		ew.like("title","%"+key+"%");
		Page<Rechage> data= rechageService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("rechage:rechage:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		rechageService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("rechage:rechage:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/rechage/rechage/add";
	}
	@RequiresPermissions("rechage:rechage:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(Rechage model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(rechageService.insert(model)!=0){

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

	@RequiresPermissions("rechage:rechage:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		Rechage model = rechageService.findByPrimaryKey(id);
		map.put("item", model);
		return new ModelAndView("/rechage/rechage/edit",map);

	}
	@RequiresPermissions("rechage:rechage:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(Rechage brand){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(rechageService.updateByPrimaryKey(brand)!=0){

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
