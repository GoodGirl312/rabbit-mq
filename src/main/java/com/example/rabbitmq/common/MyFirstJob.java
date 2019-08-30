package com.example.rabbitmq.common;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ys
 * @Description
 * @Date 2019/8/30 14:59
 */
public class MyFirstJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时任务执行；" + jobExecutionContext.getJobDetail().getJobDataMap().getString("param"));
    }
}
