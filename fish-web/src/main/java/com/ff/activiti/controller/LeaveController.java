package com.ff.activiti.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.ff.activiti.model.Leave;
import com.ff.activiti.service.ActivitiService;
import com.ff.activiti.service.LeaveService;
import com.ff.common.base.BaseController;
import com.ff.common.util.UserUtil;
import com.ff.system.model.User;
import org.springframework.web.servlet.ModelAndView;

/**
* @Description 请假流程
* @Author yuzhongxin
* @Date 15:17 2017/6/2
* @param  * @param null
* @Version V1.0
*/
@Controller
@RequestMapping("/activiti/leave")
public class LeaveController extends BaseController{

	@Reference
	private LeaveService leaveService;
	@Reference
	private ActivitiService workflowService;

	@RequiresPermissions(value = "activiti:leave:add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createForm(Model model) {		
		return "activiti/leave/add";
	}

	@RequiresPermissions(value = "activiti:leave:add")
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String,Object> doAdd(Leave leave){
		leaveService.save(leave,(User)UserUtil.getUser());

		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");

		return resultMap;
	}





	@RequestMapping(value = "/examine1",method=RequestMethod.GET)
	public ModelAndView examine1(String id,  ModelMap map) {
		map.put("item", leaveService.findByPrimaryKey(id));

		return new ModelAndView("/activiti/leave/examine1",map);


	}

	@RequestMapping(value="/examine1",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doExamine1(Leave leave,String message,String status){

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("message", status);
		variables.put("inputUser", ((User)UserUtil.getUser()).getId().toString());
		workflowService.complete(leave.getClass().getSimpleName(), leave.getId(),variables,message,(User)UserUtil.getUser());


		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");

		return resultMap;
	}

	@RequestMapping(value = "/examine2",method=RequestMethod.GET)
	public ModelAndView examine2(String id,  ModelMap map) {
		map.put("item", leaveService.findByPrimaryKey(id));

		return new ModelAndView("/activiti/leave/examine2",map);


	}

	@RequestMapping(value="/examine2",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doExamine2(Leave leave,String message,String status){
		leaveService.updateByPrimaryKey(leave);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("message", status);
		variables.put("inputUser", ((User)UserUtil.getUser()).getId().toString());
		workflowService.complete(leave.getClass().getSimpleName(), leave.getId(),variables,message,(User)UserUtil.getUser());


		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");

		return resultMap;
	}




}
