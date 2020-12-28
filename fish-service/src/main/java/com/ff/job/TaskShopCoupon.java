package com.ff.job;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ff.order.dao.OrderMapper;
import com.ff.order.model.Order;
import com.ff.shop.model.ShopCouponLogs;
import com.ff.shop.service.ShopCouponLogsService;
import com.ff.shop.service.ShopCouponService;
import com.ff.system.model.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Yzx on 2017/5/19.
 */
@DisallowConcurrentExecution
public class TaskShopCoupon implements Job {
    @Autowired
    protected OrderMapper mapper;
    @Autowired
    protected ShopCouponLogsService shopCouponLogsService;
    @Autowired
    protected ShopCouponService shopCouponService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        ShopCouponLogs logs=new ShopCouponLogs();
        logs.setStatus(0);

    }
}
