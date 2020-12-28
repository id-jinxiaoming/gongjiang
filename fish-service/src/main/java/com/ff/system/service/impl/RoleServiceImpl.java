package com.ff.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.system.dao.RoleMapper;
import com.ff.system.dao.RoleResourcesMapper;
import com.ff.system.dao.UserRoleMapper;
import com.ff.system.model.Role;
import com.ff.system.model.RoleResources;
import com.ff.system.model.UserRole;
import com.ff.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.*;




@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleResourcesMapper roleResourcesMapper;


	
	
	

	@Override
	public int insert(Role role, String[] resourcesId) {
		String id = UUID.randomUUID().toString();
		role.setId(id);
		if(resourcesId!=null){
			for(int i=0;i<resourcesId.length;i++){
				RoleResources roleResources=new RoleResources();
				roleResources.setId(UUID.randomUUID().toString());
				roleResources.setResourcesId(resourcesId[i]);
				roleResources.setRoleId(id);
				roleResourcesMapper.insert(roleResources);
			}
		}
		
		return roleMapper.insert(role);
	}

	@Override
	public int update(Role role,String[] resourcesId) {
		RoleResources roleResources2=new RoleResources();
		roleResources2.setRoleId(role.getId());
		
		List<String> ids=new ArrayList<String>();
		for (String string : resourcesId) {
			ids.add(string);
		}
		roleResourcesMapper.deleteBatchIds(ids);
		if(resourcesId!=null){
			for(int i=0;i<resourcesId.length;i++){
				RoleResources roleResources=new RoleResources();
				roleResources.setId(UUID.randomUUID().toString());
				roleResources.setRoleId(role.getId());
				roleResources.setResourcesId(resourcesId[i]);
				roleResourcesMapper.insert(roleResources);
			}
		}
		return roleMapper.updateById(role);
	}

	@Override
	public int delete(String[] id) {
		RoleResources[] r=new RoleResources[id.length];
		UserRole[] u=new UserRole[id.length];
		for(int i=0;i<id.length;i++){
			RoleResources roleResources=new RoleResources();
			UserRole userRole=new UserRole();
			roleResources.setRoleId(id[i]);
			userRole.setRoleId(id[i]);
			r[i]=roleResources;
			u[i]=userRole;
		}
		EntityWrapper<UserRole> ew=new EntityWrapper<UserRole>();
		userRoleMapper.delete(ew);
		EntityWrapper<RoleResources> ew1=new EntityWrapper<RoleResources>();
		roleResourcesMapper.delete(ew1);
		return roleMapper.deleteById(id);
	}



	@Override
	public List<RoleResources> findRoleResourcesByRole(Role role) {
		RoleResources roleResources=new RoleResources();
		roleResources.setRoleId(role.getId());
		EntityWrapper<RoleResources> ew=new EntityWrapper<RoleResources>();
		ew.setEntity(roleResources);
		return roleResourcesMapper.selectList(ew);
	}

	@Override
	public List<Role> selectAll() {
		// TODO Auto-generated method stub
		return roleMapper.selectList(null);
	}

	@Override
	public Role findByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return roleMapper.selectById(id);
	}

	@Override
	public int insert(Role t) {
		String id = UUID.randomUUID().toString();
		Field field;
		try {
			field = t.getClass().getDeclaredField("id");
			field.setAccessible(true);
			field.set(t, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return roleMapper.insert(t);
	}

	@Override
	public int updateByPrimaryKey(Role t) {
		// TODO Auto-generated method stub
		return roleMapper.updateById(t);
	}

	@Override
	public int deleteByPrimaryKey(String[] id) {
		// TODO Auto-generated method stub
		List<String> ids =new ArrayList<String>();
		for (String string : id) {
			ids.add(string);
		}
		return roleMapper.deleteBatchIds(ids);
	}
	
	

	@Override
	public List<Role> selectByT(Role t) {
		// TODO Auto-generated method stub
		EntityWrapper< Role> ew = new EntityWrapper<Role>();
		return roleMapper.selectList(ew);
	}

	@Override
	public Role findOne(Role t) {
		return roleMapper.selectOne(t);
	}

	@Override
	public Page<Role> selectPage(Page<Role> page, EntityWrapper<Role> ew) {
		// TODO Auto-generated method stub
	  if (null != ew) {
	      ew.orderBy(page.getOrderByField(), page.isAsc());
	  }
	  page.setRecords(roleMapper.selectPage(page, ew));
	  return page;
	}

	@Override
	public List<Role> findListByUserId(String userId) {
		// TODO Auto-generated method stub
		return roleMapper.findTByUserId(userId);
	}

	@Override
	public boolean updateByUserId(String userId, String[] roleId) {
		//删除用户之前角色
		Map<String,Object> map=new HashMap();
		map.put("user_id",userId);
		userRoleMapper.deleteByMap(map);
		//创建新角色
		for (String r: roleId) {
			String id = UUID.randomUUID().toString();
			UserRole role=new UserRole();
			role.setId(id);
			role.setUserId(userId);
			role.setRoleId(r);

			userRoleMapper.insert(role);
		}



		return true;
	}

	@Override
	public boolean addResourcesByRoleId(String roleId, String[] resourcesId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("role_id",roleId);
		roleResourcesMapper.deleteByMap(map);
		for (String id:resourcesId) {
			String rId = UUID.randomUUID().toString();

			RoleResources model=new RoleResources();
			model.setId(rId);
			model.setRoleId(roleId);
			model.setResourcesId(id);
			roleResourcesMapper.insert(model);
		}
		return true;
	}


}
