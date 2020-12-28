package com.ff.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ff.system.model.ScheduleJob;
import com.ff.system.service.ScheduleJobService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 定时任务 service
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService{

	@Autowired
	private Scheduler scheduler;

	/**
	 * 添加定时任务
	 * @param scheduleJob
	 */
	public void add(ScheduleJob scheduleJob){
		@SuppressWarnings("rawtypes")
		Class job = null;
		try {
			job = Class.forName(scheduleJob.getClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(scheduleJob.getName(), scheduleJob.getGroup()).build();
		//表达式调度构建器（可判断创建SimpleScheduleBuilder）
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);
		//按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getName(), scheduleJob.getGroup()).withSchedule(scheduleBuilder).build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取所有计划中的任务
	 * @return 结果集合
	 */
	public List<ScheduleJob> getAllScheduleJob(){
		List<ScheduleJob> scheduleJobList=new ArrayList<ScheduleJob>();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		try {
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					ScheduleJob scheduleJob = new ScheduleJob();
					scheduleJob.setName(jobKey.getName());
					scheduleJob.setGroup(jobKey.getGroup());
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					scheduleJob.setStatus(triggerState.name());
					//获取要执行的定时任务类名
					JobDetail jobDetail=scheduler.getJobDetail(jobKey);
					scheduleJob.setClassName(jobDetail.getJobClass().getName());

					if (trigger instanceof CronTrigger) {
						CronTrigger cron = (CronTrigger) trigger;
						scheduleJob.setCronExpression(cron.getCronExpression());
					}
					scheduleJobList.add(scheduleJob);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleJobList;
	}




	/**
	 * 恢复任务
	 * @param name 任务名
	 * @param group 任务组
	 */
	public void restartJob(String[] name,String[] group){
		for(int i=0;i<name.length;i++){
			JobKey key = new JobKey(name[i], group[i]);
			try {
				scheduler.resumeJob(key);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 立马执行一次任务
	 * @param name 任务名
	 * @param group 任务组
	 */
	public void startNowJob(String[] name,String[] group){
		for(int i=0;i<name.length;i++){
			JobKey jobKey = JobKey.jobKey(name[i], group[i]);
			try {
				scheduler.triggerJob(jobKey);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除任务
	 * @param name 任务名
	 * @param group 任务组
	 */
	public void delJob(String[] name,String[] group){
		for(int i=0;i<name.length;i++){
			JobKey key = new JobKey(name[i], group[i]);
			try {
				scheduler.deleteJob(key);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 修改触发器时间
	 * @param scheduleJob 任务名
	 */
	public void modifyTrigger(ScheduleJob scheduleJob){
		try {
			TriggerKey key = TriggerKey.triggerKey(scheduleJob.getName(), scheduleJob.getGroup());
			//Trigger trigger = scheduler.getTrigger(key);

			CronTrigger newTrigger = (CronTrigger) TriggerBuilder.newTrigger()
					.withIdentity(key)
					.withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()))
					.build();
			scheduler.rescheduleJob(key, newTrigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 暂停任务
	 * @param name 任务名
	 * @param group 任务组
	 */
	public void stopJob(String[] name,String[] group){
		for(int i=0;i<name.length;i++){
			JobKey key = new JobKey(name[i], group[i]);
			try {
				scheduler.pauseJob(key);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ScheduleJob getScheduleJob(String name, String group) {
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		try {
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					if(jobKey.getName().equals(name)&&jobKey.getGroup().equals(group))
					{
						ScheduleJob scheduleJob = new ScheduleJob();
						scheduleJob.setName(jobKey.getName());
						scheduleJob.setGroup(jobKey.getGroup());
						Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
						scheduleJob.setStatus(triggerState.name());
						//获取要执行的定时任务类名
						JobDetail jobDetail=scheduler.getJobDetail(jobKey);
						scheduleJob.setClassName(jobDetail.getJobClass().getName());

						if (trigger instanceof CronTrigger) {
							CronTrigger cron = (CronTrigger) trigger;
							scheduleJob.setCronExpression(cron.getCronExpression());
						}
						return scheduleJob;
					}


				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
