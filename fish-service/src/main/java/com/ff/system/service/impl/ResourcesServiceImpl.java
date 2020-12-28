package com.ff.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.model.Attributes;
import com.ff.common.model.JsonTreeData;
import com.ff.common.model.ResourcesTree;
import com.ff.common.util.ResourcesUtil;
import com.ff.common.util.TreeNodeUtil;
import com.ff.system.dao.ResourcesMapper;
import com.ff.system.dao.RoleResourcesMapper;
import com.ff.system.model.Resources;
import com.ff.system.model.RoleResources;
import com.ff.system.model.User;
import com.ff.system.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;





@Service
public class ResourcesServiceImpl  implements ResourcesService {
	@Autowired
	private ResourcesMapper resourcesMapper;
	@Autowired
	private RoleResourcesMapper roleResourcesMapper;
	
	@Override
	public int delete(String[] id) {
		int j=0;
		for(int i=0;i<id.length;i++){
			if(!"1".equals(id[i])){
				j++;
			}
		}
		RoleResources[] r=new RoleResources[j];
		for(int i=0;i<id.length;i++){
			if(!"1".equals(id[i])){
				RoleResources roleResources=new RoleResources();
				roleResources.setResourcesId(id[i]);
				r[i]=roleResources;
			}
		}
		if(j!=0){
			EntityWrapper<RoleResources> ew=new EntityWrapper<RoleResources>();
			roleResourcesMapper.delete(ew);
			resourcesMapper.deleteById(id);
		}
		return 0;
	}
	
	




	@Override
	public List<Resources> selectAll() {
		// TODO Auto-generated method stub
		return resourcesMapper.selectList(null);
	}

	@Override
	public Resources findByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return resourcesMapper.selectById(id);
	}

	@Override
	public int insert(Resources t) {
		String id = UUID.randomUUID().toString();
		Field field;
		try {
			field = t.getClass().getDeclaredField("id");
			field.setAccessible(true);
			field.set(t, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return resourcesMapper.insert(t);
	}

	@Override
	public int updateByPrimaryKey(Resources t) {
		// TODO Auto-generated method stub
		return resourcesMapper.updateById(t);
	}

	@Override
	public int deleteByPrimaryKey(String[] id) {
		// TODO Auto-generated method stub
		List<String> ids =new ArrayList<String>();
		for (String string : id) {
			ids.add(string);
		}
		return resourcesMapper.deleteBatchIds(ids);
	}
	
	

	@Override
	public List<Resources> selectByT(Resources t) {
		// TODO Auto-generated method stub
		
		EntityWrapper< Resources> ew = new EntityWrapper<Resources>();
		ew.setEntity(t);
	
		return resourcesMapper.selectList(ew);
	}

	@Override
	public Resources findOne(Resources t) {
		return resourcesMapper.selectOne(t);
	}

	@Override
	public Page<Resources> selectPage(Page<Resources> page, EntityWrapper<Resources> ew) {
		// TODO Auto-generated method stub
	  if (null != ew) {
	      ew.orderBy(page.getOrderByField(), page.isAsc());
	  }
	  page.setRecords(resourcesMapper.selectPage(page, ew));
	  return page;
	}



	@Override
	public List<Resources> selectListByUserId(String userId, String type) {
		// TODO Auto-generated method stub
		return resourcesMapper.selectListByUserId(userId, type);
	}

	@Override
	public List<Resources> selectListByRoleId(String roleId) {
		return resourcesMapper.selectListByRoleId(roleId);
	}

	@Override
	public List<ResourcesTree> selectResourcesTree() {
		List<Resources> resourcesList =resourcesMapper.selectList(null);
		List<ResourcesTree> treeDataList = new ArrayList<ResourcesTree>();

		for (Resources res : resourcesList) {
			ResourcesTree treeData = new ResourcesTree();
			treeData.setId(res.getId());
			treeData.setPid(res.getPid());
			treeData.setName(res.getName());
			treeData.setIcon(res.getIcon());
			treeData.setPermission(res.getPermission());
			treeData.setPid(res.getPid());
			treeData.setSort(res.getSort());
			treeData.setUrl(res.getUrl());
			treeData.setType(res.getType());

			treeDataList.add(treeData);
		}

		List<ResourcesTree> newTreeDataList = ResourcesUtil.getfather(treeDataList);


		return newTreeDataList;
	}



	@Override
	public List<JsonTreeData> selectResources() {
		Resources resources=new Resources();
		resources.setType("1");
		EntityWrapper<Resources> ew=new EntityWrapper<Resources>();
		ew.orderBy("sort", true);
		ew.setEntity(resources);
		List<Resources> resourcesList =resourcesMapper.selectList(ew);
	    List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();
      
       for (Resources res : resourcesList) {
           JsonTreeData treeData = new JsonTreeData();
           treeData.setId(res.getId());
           treeData.setPid(res.getPid());
           treeData.setText(res.getName());
           Attributes attributes=new Attributes();
           attributes.setUrl(res.getUrl()); 
           attributes.setIcon(res.getIcon());
           treeData.setAttributes(attributes);
           treeDataList.add(treeData);
       }
       
       List<JsonTreeData> newTreeDataList = TreeNodeUtil.getfatherNode(treeDataList);
		return newTreeDataList;
	}
	
	@Override
	public List<JsonTreeData> selectResourcesByUser(User user) {

		List<Resources> resourcesList =resourcesMapper.selectListByUserId(user.getId(), "1");
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();
        for (Resources res : resourcesList) {
            JsonTreeData treeData = new JsonTreeData();
            treeData.setId(res.getId());
            treeData.setPid(res.getPid());
            treeData.setText(res.getName());
            Attributes attributes=new Attributes();
            attributes.setUrl(res.getUrl()); 
            attributes.setIcon(res.getIcon());
            treeData.setAttributes(attributes);
            treeDataList.add(treeData);
        }
    
        List<JsonTreeData> newTreeDataList = TreeNodeUtil.getfatherNode(treeDataList);
		return newTreeDataList;
	}


	
	
}
