package com.ff.system.service;

import java.util.List;

import com.ff.common.base.BaseService;
import com.ff.system.model.ScheduleJob;



public interface ScheduleJobService {
	
	
	public void add(ScheduleJob scheduleJob);
	public List<ScheduleJob> getAllScheduleJob();	
	public void modifyTrigger(ScheduleJob scheduleJob);
	
	public void restartJob(String[] name, String[] group);
	public void startNowJob(String[] name, String[] group);
	public void delJob(String[] name, String[] group);
	public void stopJob(String[] name, String[] group);

	public ScheduleJob getScheduleJob(String name, String group);
}
