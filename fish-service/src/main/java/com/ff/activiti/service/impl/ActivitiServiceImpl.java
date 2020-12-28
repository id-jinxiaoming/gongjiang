package com.ff.activiti.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ff.activiti.service.ActivitiService;
import com.ff.common.model.Page;
import com.ff.common.util.ByteToInputStream;
import com.ff.common.util.DateUtils;
import com.ff.system.model.User;
import com.ff.system.service.UserService;



@Service("activitiService")
@Transactional(readOnly=true)
public class ActivitiServiceImpl implements ActivitiService{
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private UserService userService;

	
	public ProcessDefinition findProcessDefinitionByprocessDefinitionId(String processDefinitionId) {
		return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
	}

	public Task findTaskByTaskId(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();

	}


	public Task findTaskBybusinessKey(String businessKey) {
		return taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
	}


	public ProcessInstance findProcessInstanceByProcessInstanceId(String processInstanceId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}

	public HistoricTaskInstance findHistoricTaskInstanceByTaskId(String taskId) {
		return historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
	}

	public HistoricProcessInstance findHistoricProcessInstanceByProcessInstanceId(String processInstanceId) {
		return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}

	public ProcessInstance findProcessInstanceByTaskId(String taskId) {
		HistoricTaskInstance historicTaskInstance = findHistoricTaskInstanceByTaskId(taskId);
		return findProcessInstanceByProcessInstanceId(historicTaskInstance.getProcessInstanceId());
	}

	public HistoricProcessInstance findHistoricProcessInstanceByBusinessKey(String businessKey) {
		return historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
	}

	/**
	 * 创建 部署
	 * @param file
	 * @param filename
	 */
	@Transactional(readOnly = false)
	public void saveNewDeploye(byte[] file, String filename) {
		try {
			repositoryService.createDeployment()
					.name(filename)
					.addZipInputStream(  new ZipInputStream(new ByteArrayInputStream(file)))//
					.deploy();// 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Page<Map<String, Object>> findDeploymentList(Page<Map<String, Object>> page) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();

		page.setTotalCount(deploymentQuery.count());
		List<Deployment> list = deploymentQuery.orderByDeploymenTime().asc()
				.listPage(page.getFirst(), page.getPageSize());

		for (int i = 0; i < list.size(); i++) {
			Deployment d = list.get(i);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", d.getId());
			m.put("name", d.getName());
			m.put("deploymentTime", d.getDeploymentTime());
			m.put("category", d.getCategory());
			m.put("tenantId", d.getTenantId());
			mapList.add(m);
		}
		page.setResult(mapList);
		return page;
	}

	/**
	 * 删除部署
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteProcessDefinitionByDeploymentId(String id) {
		repositoryService.deleteDeployment(id, true);
	}


	/**
	 * 流程实例列表
	 * @param page
	 * @return
	 */
	public Page<Map<String, Object>> findProcessDefinitionList(Page<Map<String, Object>> page) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		page.setTotalCount(processDefinitionQuery.count());
		List<ProcessDefinition> list = processDefinitionQuery.orderByProcessDefinitionVersion().asc()//
				.listPage(page.getFirst(), page.getPageSize());
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			ProcessDefinition p = list.get(i);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", p.getId());
			m.put("name", p.getName());
			m.put("key", p.getKey());
			m.put("category", p.getCategory());
			m.put("deploymentId", p.getDeploymentId());
			m.put("description", p.getDescription());
			m.put("diagramResourceName", p.getDiagramResourceName());
			m.put("resourceName", p.getResourceName());
			m.put("tenantId", p.getTenantId());
			m.put("version", p.getVersion());
			mapList.add(m);
		}
		page.setResult(mapList);
		return page;
	}

	
	public byte[]  findImageInputStream(String deploymentId, String imageName) throws IOException {
		return ByteToInputStream.input2byte(repositoryService.getResourceAsStream(deploymentId, imageName));
	}

	
	@Transactional(readOnly = false)
	public void startProcessInstanceByKey(String name, String id,Map<String,Object>variables) {
		String businessKey = name + "." + id;
		runtimeService.startProcessInstanceByKey(name,businessKey , variables);
	}

	@Transactional(readOnly = false)
	public void complete(String name, String id, Map<String, Object> variables, String message,User user) {
		String businessKey = name + "." + id;
		
		Task task = findTaskBybusinessKey(businessKey);
		
		String processInstanceId = task.getProcessInstanceId();
		if(message!=null){
			Authentication.setAuthenticatedUserId(user.getId().toString());
			taskService.addComment(task.getId(), processInstanceId, message);
		}
		taskService.complete(findTaskBybusinessKey(businessKey).getId(), variables);

	}
	

	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		
		String url = formData.getFormKey();
		return url;
	}

	
	public Page<Map<String, Object>> findTaskListByUserId(final Page<Map<String, Object>> page, String userId) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(userId);
		page.setTotalCount(taskQuery.count());
		List<Task> list = taskQuery.orderByTaskCreateTime().asc().listPage(page.getFirst(), page.getPageSize());

		for (int i = 0; i < list.size(); i++) {
			Task d = list.get(i);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("assignee", d.getAssignee());
			m.put("category", d.getCategory());
			m.put("createTime", DateUtils.formatDateTime(d.getCreateTime()));
			m.put("delegationState", d.getDelegationState());
			m.put("description", d.getDescription());
			m.put("dueDate", d.getDueDate());
			m.put("executionId", d.getExecutionId());
			m.put("formKey", d.getFormKey());
			m.put("id", d.getId());
			m.put("name", d.getName());
			m.put("owner", d.getOwner());
			m.put("parentTaskId", d.getParentTaskId());
			m.put("priority", d.getPriority());
			m.put("processDefinitionId", d.getProcessDefinitionId());
			m.put("processInstanceId", d.getProcessInstanceId());
			m.put("processVariables", d.getProcessVariables());
			m.put("taskDefinitionKey", d.getTaskDefinitionKey());
			m.put("taskLocalVariables", d.getTaskLocalVariables());
			m.put("tenantId", d.getTenantId());
			ProcessDefinition processDefinition = findProcessDefinitionByprocessDefinitionId(
					d.getProcessDefinitionId());
			m.put("processDefinitionName", processDefinition.getName());
			mapList.add(m);
		}

		page.setResult(mapList);
		return page;
	}

	public Page<Map<String, Object>> findHistoryTaskListByUserId(Page<Map<String, Object>> page,
			String userId) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId)
				.finished();

		page.setTotalCount(histTaskQuery.count());
		List<HistoricTaskInstance> histList = histTaskQuery.includeProcessVariables()
				.orderByHistoricTaskInstanceEndTime().desc().listPage(page.getFirst(), page.getPageSize());
		for (int i = 0; i < histList.size(); i++) {
			HistoricTaskInstance historicTaskInstance = histList.get(i);
			Map<String, Object> map = new HashMap<>();
			map.put("assignee", historicTaskInstance.getAssignee());
			map.put("category", historicTaskInstance.getCategory());
			map.put("claimTime", historicTaskInstance.getClaimTime());
			map.put("createTime", DateUtils.formatDateTime(historicTaskInstance.getCreateTime()));
			map.put("deleteReason", historicTaskInstance.getDeleteReason());
			map.put("description", historicTaskInstance.getDescription());
			map.put("dueDate", historicTaskInstance.getDueDate());
			map.put("durationInMillis", historicTaskInstance.getDurationInMillis());
			map.put("endTime", historicTaskInstance.getEndTime());
			map.put("executionId", historicTaskInstance.getExecutionId());
			map.put("formKey", historicTaskInstance.getFormKey());
			map.put("id", historicTaskInstance.getId());
			map.put("name", historicTaskInstance.getName());
			map.put("owner", historicTaskInstance.getOwner());
			map.put("priority", historicTaskInstance.getParentTaskId());
			map.put("priority", historicTaskInstance.getPriority());
			map.put("processDefinitionId", historicTaskInstance.getProcessDefinitionId());
			map.put("processInstanceId", historicTaskInstance.getProcessInstanceId());
			map.put("processVariables", historicTaskInstance.getProcessVariables());
			map.put("startTime", historicTaskInstance.getStartTime());
			map.put("taskDefinitionKey", historicTaskInstance.getTaskDefinitionKey());
			map.put("taskLocalVariables", historicTaskInstance.getTaskLocalVariables());
			map.put("tenantId", historicTaskInstance.getTenantId());
			map.put("time", historicTaskInstance.getTime());
			map.put("workTimeInMillis", historicTaskInstance.getWorkTimeInMillis());
			ProcessDefinition processDefinition = findProcessDefinitionByprocessDefinitionId(
					historicTaskInstance.getProcessDefinitionId());
			map.put("processDefinitionName", processDefinition.getName());
			mapList.add(map);

		}

		page.setResult(mapList);
		return page;
	}

	
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		
		Task task = findTaskByTaskId(taskId);
		
		String processDefinitionId = task.getProcessDefinitionId();
		return findProcessDefinitionByprocessDefinitionId(processDefinitionId);
	}

	
	public ProcessDefinition findHistoryProcessDefinitionByTaskId(String taskId) {
	
		HistoricTaskInstance task = findHistoricTaskInstanceByTaskId(taskId);
		
		String processDefinitionId = task.getProcessDefinitionId();
		return findProcessDefinitionByprocessDefinitionId(processDefinitionId);
	}

	
	public String findIdByTaskId(String taskId) {
		
		String processInstanceId = findTaskByTaskId(taskId).getProcessInstanceId();
		
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		
		String buniness_key = pi.getBusinessKey();
		String id = null;
		if (buniness_key != null) {
		
			id = buniness_key.split("\\.")[1];
		}
		return id;
	}

	public String findHistoryIdByTaskId(String processInstanceId) {
	
		HistoricProcessInstance historicProcessInstance = findHistoricProcessInstanceByProcessInstanceId(
				processInstanceId);

		
		String buniness_key = historicProcessInstance.getBusinessKey();
		String id = null;
		if (buniness_key != null) {
			
			id = buniness_key.split("\\.")[1];
		}
		return id;
	}

	
	public Map<String, Object> findCoordingByTaskId(String taskId) {
		
		
		Task task = findTaskByTaskId(taskId);
		
		String processDefinitionId = task.getProcessDefinitionId();
		String processInstanceId = task.getProcessInstanceId();
		return findcoordingByProcessDefinitionIdProcessInstanceId(processDefinitionId,processInstanceId);
	}

	public Map<String, Object> findHistoryCoordingByTaskId(String taskId) {
		
		HistoricTaskInstance historicTaskInstance = findHistoricTaskInstanceByTaskId(taskId);
		
		String processDefinitionId = historicTaskInstance.getProcessDefinitionId();
		String processInstanceId = historicTaskInstance.getProcessInstanceId();
		return findcoordingByProcessDefinitionIdProcessInstanceId(processDefinitionId,processInstanceId);
	}
	
	
	public Map<String , Object> findcoordingByProcessDefinitionIdProcessInstanceId(String processDefinitionId,String processInstanceId){
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		
		ProcessInstance pi = findProcessInstanceByProcessInstanceId(processInstanceId);
		
		String activityId = pi.getActivityId();
		
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}

	
	public List<Map<String, Object>> findCommentByBusinessKey(String businessKey) {

		
		Task task = findTaskBybusinessKey(businessKey);
		
		String processInstanceId = task.getProcessInstanceId();
		return findHistoryCommentByProcessInstanceId(processInstanceId);
	}

	
	public List<Map<String, Object>> findHistoryCommentByBusinessKey(String businessKey) {

		
		HistoricProcessInstance hpi = findHistoricProcessInstanceByBusinessKey(businessKey);
		
		String processInstanceId = hpi.getId();
		
		return findHistoryCommentByProcessInstanceId(processInstanceId);
	}
	public  List<Map<String, Object>> findHistoryCommentByProcessInstanceId(String processInstanceId){
		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Comment comment = list.get(i);
			Map<String, Object> map = new HashMap<>();
			map.put("fullMessage", comment.getFullMessage());
			map.put("id", comment.getId());
			map.put("processInstanceId", comment.getProcessInstanceId());
			map.put("taskId", comment.getTaskId());
			map.put("time", DateUtils.formatDateTime(comment.getTime()));
			map.put("type", comment.getType());
			map.put("userId", comment.getUserId());
			User user=new User();
			user.setId(comment.getUserId());
			map.put("userName", userService.findOne(user).getAccount());
			mapList.add(map);
		}
		return mapList;
		
	}

}
