package com.ff.activiti.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.activiti.service.ActivitiService;
import com.ff.common.base.BaseController;
import com.ff.common.model.Page;
import com.ff.common.util.ByteToInputStream;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;


@Controller
@RequestMapping("activiti/processDefinition")
public class ProcessDefinitionController extends BaseController{

	@Reference
	private ActivitiService processDefinitionService;
	

	@RequiresPermissions(value = "activiti:processDefinition:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "activiti/processDefinition/list";
	}


	@RequiresPermissions(value = "activiti:processDefinition:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		Page<Map<String,Object>> page = getBasePage(request);

		page= processDefinitionService.findProcessDefinitionList(page);


		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",page);
		return resultMap;
	}

	/**
	 * 查看流程图
	 * @throws Exception 
	 */
	@RequiresPermissions(value = "activiti:processDefinition:show")
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public void viewImage(String deploymentId,String diagramResourceName, HttpServletResponse response) throws Exception{
	
		//获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
		byte[] bt = processDefinitionService.findImageInputStream(deploymentId,diagramResourceName);
		InputStream in =ByteToInputStream.byte2Input(bt);
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
