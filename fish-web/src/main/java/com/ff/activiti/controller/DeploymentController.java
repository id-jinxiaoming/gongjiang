package com.ff.activiti.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.activiti.service.ActivitiService;
import com.ff.common.base.BaseController;
import com.ff.common.model.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 流程部署
 */
@Controller
@RequestMapping("activiti/deployment")
public class DeploymentController extends BaseController{

	@Reference
	private ActivitiService workflowService;

	/**
	 * 部署列表
	 * @return
	 */
	@RequiresPermissions(value = "activiti:deployment:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "activiti/deployment/list";
	}



	@RequiresPermissions(value = "activiti:deployment:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {

		Page<Map<String,Object>> page = getBasePage(request);
		page = workflowService.findDeploymentList(page);

		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",page);
		return resultMap;
	}

	/**
	 * 删除部署
	 * @param id
	 * @return
	 */
	@RequiresPermissions("activiti:deployment:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		if(id.length>0)
		{
			for (String dep:id) {
				workflowService.deleteProcessDefinitionByDeploymentId(dep);
			}

		}


		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("activiti:deployment:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/activiti/deployment/add";
	}
	@RequiresPermissions("activiti:deployment:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(MultipartFile file,String name) throws  IOException{
		Map<String, Object> resultMap = new LinkedHashMap();
		workflowService.saveNewDeploye(file.getBytes()  ,name);
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");
		return resultMap;
	}

}
