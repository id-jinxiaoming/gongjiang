package com.ff.system.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.system.dao.SettingMapper;
import com.ff.system.model.Setting;
import com.ff.system.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class SettingServiceImpl implements SettingService{

	@Autowired
    protected SettingMapper mapper;


	@Override
	public List<Setting> selectAll() {
		return null;
	}

	@Override
	public Setting findByPrimaryKey(String id) {
		return null;
	}

	@Override
	public int insert(Setting setting) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Setting setting) {
		EntityWrapper<Setting> ew = new EntityWrapper<Setting>();
		ew.eq("k",setting.getK());
		return mapper.update(setting,ew);
	}

	@Override
	public int deleteByPrimaryKey(String[] id) {
		return 0;
	}

	@Override
	public Page<Setting> selectPage(Page<Setting> page, EntityWrapper<Setting> ew) {
		return null;
	}

	@Override
	public List<Setting> selectByT(Setting setting) {
		EntityWrapper<Setting> ew = new EntityWrapper<Setting>();
		ew.eq("k",setting.getK());
		return mapper.selectList(ew);



	}

	@Override
	public Setting findOne(Setting setting) {
		return null;
	}

	@Override
	public Setting findOneByKey(String key) {
		EntityWrapper<Setting> ew = new EntityWrapper<Setting>();
		ew.eq("k",key);
		List<Setting> settings= mapper.selectList(ew);
		if(settings!=null&&settings.size()>0)
		{
			return settings.get(0);
		}
		return null;

	}
}
