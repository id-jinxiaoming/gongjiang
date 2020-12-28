package com.ff.activiti.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.activiti.service.ActivitiService;
import com.ff.common.base.BaseController;
import com.ff.common.model.Page;
import com.ff.common.util.ByteToInputStream;
import com.ff.common.util.UserUtil;
import com.ff.system.model.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: 流程管理
* @Author yuzhongxin
* @Date 14:53 2017/6/2
* @Version V1.0
*/
@Controller
@RequestMapping("activiti/bpm")
public class BPMController extends BaseController{

	@Reference
	private ActivitiService workflowService;


	@RequiresPermissions(value = "activiti:bpm:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "activiti/bpm/list";
	}


	@RequiresPermissions(value = "activiti:bpm:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {

		Page<Map<String, Object>> page = getBasePage(request);
		page= workflowService.findTaskListByUserId(page,((User) UserUtil.getUser()).getId().toString());

		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",page);
		return resultMap;
	}




	@RequestMapping(value="/commentList")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,String businessKey) {
		Page<Map<String, Object>> page = getBasePage(request);
		List<Map<String, Object>> commentList =workflowService.findCommentByBusinessKey(businessKey);
		page.setResult(commentList); 
		page.setTotalCount(commentList.size());
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",page);
		return resultMap;
	}
	
	/**
	* @Description 操作 处理任务  FORM KEY
	* @Author yuzhongxin
	* @Date 16:54 2017/6/2
	* @param  * @param null
	* @Version V1.0
	*/
	@RequiresPermissions("activiti:bpm:edit")
	@RequestMapping(value = "/edit")
	public String updateForm(String id, Model model) {
		String url = workflowService.findTaskFormKeyByTaskId(id);
		String sid = workflowService.findIdByTaskId(id);
		return "redirect:"+url+"?id="+sid;
	}
	/**
	* @Description 显示 流程
	* @Author yuzhongxin
	* @Date 21:43 2017/6/2
	* @param  * @param null
	* @Version V1.0
	*/
	@RequiresPermissions("activiti:bpm:show")
	@RequestMapping(value = "/show")
	public String toViewImage(String taskId,Model model) {
		ProcessDefinition pd = workflowService.findProcessDefinitionByTaskId(taskId);
		model.addAttribute("deploymentId", pd.getDeploymentId());
		model.addAttribute("diagramResourceName",pd.getDiagramResourceName());
		Map<String, Object> map = workflowService.findCoordingByTaskId(taskId);
		model.addAttribute("acs", map);
		return "activiti/bpm/show";
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
