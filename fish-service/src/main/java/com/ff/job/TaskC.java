package com.ff.job;

import com.ff.system.model.ScheduleJob;
import com.ff.user.dao.AuthenticationMapper;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@DisallowConcurrentExecution
public class TaskC implements Job {
    @Autowired
    protected AuthenticationMapper mapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        mapper.removalAllData();

        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }
}
