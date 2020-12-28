package com.ff.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ff.common.util.MysqlUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ff.system.model.ScheduleJob;
import org.springframework.stereotype.Service;


/**
 * 定时任务工作类
 */
@DisallowConcurrentExecution
public class TaskA implements Job {
    //0 0 0 1,24 * ?
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        MysqlUtil.exportDataBase();

        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }
}
