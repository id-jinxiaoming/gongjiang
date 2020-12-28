package com.ff.job;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ff.order.dao.OrderLogMapper;
import com.ff.order.dao.OrderMapper;
import com.ff.order.model.Order;
import com.ff.system.model.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Yzx on 2017/5/19.
 */
@DisallowConcurrentExecution
public class TaskB implements Job {
    @Autowired
    protected OrderMapper mapper;

    //0 0 0 1,24 * ?
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //定时对未支付的订单进行取消 每天执行一次
        EntityWrapper ew=new EntityWrapper();
        ew.setEntity(new Order());

        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -7); //年份减1
        Date lastTime = ca.getTime(); //结果
        long ts = lastTime.getTime()/1000;
        ew.eq("order_status",'1');
        ew.lt("created_date",ts);
        List<Order> list= mapper.selectList(ew);
        if(list!=null){
            for(Order order:list){
                order.setOrderStatus(0);
                mapper.updateById(order);
            }
        }
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("任务名称 = [" + scheduleJob.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行");
    }
}
