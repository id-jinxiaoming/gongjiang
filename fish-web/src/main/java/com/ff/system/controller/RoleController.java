package com.ff.system.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.system.model.Resources;
import com.ff.system.model.Role;
import com.ff.system.service.ResourcesService;
import com.ff.system.service.RoleService;
import com.ff.system.vo.TreeVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/system/role")
public class RoleController  extends BaseController{
	@Reference
	private RoleService roleService;

	@Reference
	private ResourcesService resourcesService;

	@RequiresPermissions(value = "system:role:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {

		return "system/role/list";

	}


	@RequiresPermissions(value = "system:role:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Role> page = getPage(request);
		EntityWrapper<Role> ew=new EntityWrapper();
		ew.like("name","%"+key+"%");
		Page<Role> data= roleService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("system:role:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		roleService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("system:role:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "system/role/add";
	}
	@RequiresPermissions("system:role:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(Role model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(roleService.insert(model)!=0){

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

	@RequiresPermissions("system:role:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		Role model = roleService.findByPrimaryKey(id);
		map.put("item", model);
		return new ModelAndView("system/role/edit",map);

	}
	@RequiresPermissions("system:role:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(Role model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(roleService.updateByPrimaryKey(model)!=0){

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

	@RequiresPermissions("system:role:editResources")
	@RequestMapping(value="/editResources",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> editResources(String id){
		Map<String, Object> resultMap = new LinkedHashMap();
		List<Resources> list= resourcesService.selectAll();

		List<Resources> currentPermissionList=resourcesService.selectListByRoleId(id);

		List<TreeVO> treeList=new ArrayList<>();

		for (Resources item : list) {
			TreeVO t=new TreeVO();
			for (Resources currentItem : currentPermissionList) {
				if(item.getId().equals(currentItem.getId()))
				{

					t.setChecked(1);
				}
			}
			t.setId(item.getId());
			t.setName(item.getName());
			t.setpId(item.getPid());
			treeList.add(t);
		}
		resultMap.put("status", 200);
		resultMap.put("data", treeList);
		resultMap.put("message", "");
		return resultMap;
	}
	@RequiresPermissions("system:role:editResources")
	@RequestMapping(value="/editResources",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEditResources(String id,String rule){
		Map<String, Object> resultMap = new LinkedHashMap();
		String[] roles=rule.split(",");
		roleService.addResourcesByRoleId(id,roles);
		resultMap.put("status", 200);
		resultMap.put("message", "");
		return resultMap;
	}




}
