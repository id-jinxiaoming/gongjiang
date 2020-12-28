package com.ff.system.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.system.dao.SmsMapper;
import com.ff.system.model.Sms;
import com.ff.system.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
    protected SmsMapper mapper;


	@Override
	public List<Sms> selectAll(){return mapper.selectList(null);
	}

	@Override
	public Sms findByPrimaryKey(String id){return mapper.selectById(id);
	}

	@Override
	public int insert(Sms t){
		String id = UUID.randomUUID().toString();
		Field field;
		try {
			field = t.getClass().getDeclaredField("smsId");
			field.setAccessible(true);
			field.set(t, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return 1;
		return mapper.insert(t);

	}

	@Override
	public int updateByPrimaryKey(Sms t) {
		// TODO Auto-generated method stub
		return mapper.updateById(t);
	}

	@Override
	public int deleteByPrimaryKey(String[] id) {
		List<String> ids =new ArrayList<String>();
		for (String string : id) {
			ids.add(string);
		}
		return mapper.deleteBatchIds(ids);
	}

	@Override
	public Page<Sms> selectPage(Page<Sms> page, EntityWrapper<Sms> ew) {
		if (null != ew) {
			ew.orderBy(page.getOrderByField(), page.isAsc());
		}
		page.setRecords(mapper.selectPage(page, ew));
		return page;
	}

	@Override
	public List<Sms> selectByT(Sms t) {

		EntityWrapper< Sms> ew = new EntityWrapper<Sms>();
		return mapper.selectList(ew);

	}

	@Override
	public Sms findOne(Sms t) {

		return mapper.selectOne(t);

	}

	@Override
	public Sms findBysmsTemplateId(String code) {
		EntityWrapper<Sms> ew = new EntityWrapper<Sms>();
		ew.eq("sms_key",code);
		List<Sms> list= mapper.selectList(ew);
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
}
