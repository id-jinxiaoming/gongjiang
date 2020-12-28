package com.ff.system.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.system.model.ScheduleJob;
import com.ff.system.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
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
 * 定时任务 controller
 */
@Controller
@RequestMapping("system/scheduleJob")
public class ScheduleJobController{



	@Reference
	private ScheduleJobService scheduleJobService;

	@RequiresPermissions(value = "system:scheduleJob:list")
	@RequestMapping(value="/list")
	public String list() {
		return "system/scheduleJob/list";
	}

	@RequiresPermissions(value = "system:scheduleJob:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {


		List<ScheduleJob> data= scheduleJobService.getAllScheduleJob();
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("records",data);
		return resultMap;
	}


	@RequiresPermissions("system:scheduleJob:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/system/scheduleJob/add";
	}
	@RequiresPermissions("system:scheduleJob:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(ScheduleJob model){
		Map<String, Object> resultMap = new LinkedHashMap();
		scheduleJobService.add(model);
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");

		return resultMap;
	}

	/**
	 * 删除任务
	 */
	@RequiresPermissions("system:scheduleJob:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(String[] name,String[] group) {
		Map<String, Object> resultMap = new LinkedHashMap();
		scheduleJobService.delJob(name,group);
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");
		return resultMap;

	}

	/**
	 * 暂停任务
	 */
	@RequiresPermissions("system:scheduleJob:stop")
	@RequestMapping("/stop")
	@ResponseBody
	public Map<String,Object> stop(String[] name,String[] group) {
		Map<String, Object> resultMap = new LinkedHashMap();
		scheduleJobService.stopJob(name,group);
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");

		return resultMap;
	}

	/**
	 * 立即运行一次
	 */
	@RequiresPermissions("system:scheduleJob:startNow")
	@RequestMapping("/startNow")
	@ResponseBody
	public Map<String,Object> stratNow(String[] name,String[] group) {

		Map<String, Object> resultMap = new LinkedHashMap();
		scheduleJobService.startNowJob(name,group);
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");

		return resultMap;

	}

	/**
	 * 恢复
	 */
	@RequiresPermissions("system:scheduleJob:resume")
	@RequestMapping("/resume")
	@ResponseBody
	public Map<String,Object> resume(String[] name,String[] group) {
		scheduleJobService.restartJob(name,group);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");

		return resultMap;
	}



	@RequiresPermissions("system:scheduleJob:edit")
	@RequestMapping(value="/edit/{name}/{group}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("name")String name, @PathVariable("group")String group, ModelMap map){
		ScheduleJob scheduleJob = scheduleJobService.getScheduleJob(name,group);
		map.put("item", scheduleJob);
		return new ModelAndView("/system/scheduleJob/edit",map);

	}
	@RequiresPermissions("system:scheduleJob:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(ScheduleJob scheduleJob){
		Map<String, Object> resultMap = new LinkedHashMap();
		//验证cron表达式
		if(CronExpression.isValidExpression(scheduleJob.getCronExpression())){
			scheduleJobService.modifyTrigger(scheduleJob);
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");

		}else{
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败");
		}


		return resultMap;
	}

	@RequestMapping(value="/cron",method=RequestMethod.GET)
	public String cron(){

		return "/system/scheduleJob/cron";
	}











	
}
