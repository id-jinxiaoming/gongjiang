package com.ff.activiti.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.config.annotation.Reference;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ff.activiti.model.Leave;
import com.ff.activiti.service.ActivitiService;
import com.ff.activiti.service.LeaveService;
import com.ff.common.base.BaseController;
import com.ff.common.model.Page;
import com.ff.common.util.ByteToInputStream;
import com.ff.common.util.UserUtil;
import com.ff.system.model.User;

/**
* @Description 流程历史
* @Author yuzhongxin
* @Date 14:18 2017/6/4
* @Version V1.0
*/
@Controller
@RequestMapping("activiti/history")
public class HistoryController extends BaseController{

	@Reference
	private ActivitiService workflowService;
	@RequiresPermissions(value = "activiti:history:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "activiti/history/list";
	}


	@RequiresPermissions(value = "activiti:history:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		Page<Map<String, Object>> page = getBasePage(request);
		page= workflowService.findHistoryTaskListByUserId(page,((User)UserUtil.getUser()).getId().toString());
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",page);
		return resultMap;
	}


	/**
	 * @Description 显示 流程
	 * @Author yuzhongxin
	 * @Date 21:43 2017/6/2
	 * @Version V1.0
	 */
	@RequiresPermissions("activiti:history:show")
	@RequestMapping(value = "/show")
	public String toViewImage(String taskId,Model model) {
		ProcessDefinition pd = workflowService.findHistoryProcessDefinitionByTaskId(taskId);
		model.addAttribute("deploymentId", pd.getDeploymentId());
		model.addAttribute("diagramResourceName",pd.getDiagramResourceName());

		ProcessInstance  processInstance =workflowService.findProcessInstanceByTaskId(taskId);
		if(processInstance!=null){
			Map<String, Object> map = workflowService.findHistoryCoordingByTaskId(taskId);
			model.addAttribute("acs", map);
		}
		return "activiti/history/show";
	}


	@RequestMapping(value="/viewImage")
	public void viewImage(String deploymentId,String diagramResourceName,HttpServletResponse response) throws Exception{

		//获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream



		byte[] bt=	workflowService.findImageInputStream(deploymentId,diagramResourceName);
		InputStream in = ByteToInputStream.byte2Input(bt);
		//从response对象获取输出流
		OutputStream out = response.getOutputStream();
		//将输入流中的数据读取出来，写到输出流中
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();

	}




}
