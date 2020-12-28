package com.ff.activiti.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ff.activiti.dao.LeaveMapper;
import com.ff.activiti.model.Leave;
import com.ff.activiti.service.LeaveService;
import com.ff.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;






@Service("leaveService")
@Transactional(readOnly=true)
public class LeaveServiceImpl  implements LeaveService{
	@Autowired
	private LeaveMapper leaveMapper;
	
	@Autowired
	private ActivitiServiceImpl workflowService;

	@Override
	public List<Leave> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Leave findByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return leaveMapper.selectById(id);
	}

	@Override
	public int insert(Leave t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateByPrimaryKey(Leave t) {
		// TODO Auto-generated method stub

		return leaveMapper.updateById(t);
	}

	@Override
	public int deleteByPrimaryKey(String[] id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public com.baomidou.mybatisplus.plugins.Page<Leave> selectPage(com.baomidou.mybatisplus.plugins.Page<Leave> page,
			EntityWrapper<Leave> ew) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Leave> selectByT(Leave t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Leave findOne(Leave t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Leave leave, User user) {
		leave.setId(UUID.randomUUID().toString());
		leaveMapper.insert(leave);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("inputUser2", user.getId().toString());
		workflowService.startProcessInstanceByKey(leave.getClass().getSimpleName(), leave.getId(),variables);

		
	}
	
	
}
