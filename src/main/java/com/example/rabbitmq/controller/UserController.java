package com.example.rabbitmq.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.rabbitmq.common.MyFirstJob;
import com.example.rabbitmq.common.TreeUtil;
import com.example.rabbitmq.config.MqConstants;
import com.example.rabbitmq.entity.Cat;
import com.example.rabbitmq.mapper.CatMapper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author ys
 * @Description
 * @Date 2019/8/28 16:09
 */
@RestController
public class UserController {
   private static int mobile = 0;
    @Autowired
    RabbitTemplate template;

    @Autowired
    TreeUtil util;

    @Autowired
    CatMapper catMapper;
    @Autowired
    private static SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 发送消息到对列(普通单一队列测试)
     */
    @RequestMapping(value = "/sendMessage",method = RequestMethod.GET)
    public void sendMessage(){
        template.convertAndSend(MqConstants.ordinaryQueneMessage,"测试第一条数据");
    }

    /**
     * 主题式消息 队列测试
     */
    @RequestMapping(value = "/sendTopicMessage",method = RequestMethod.GET)
    public void sendTopicMessage(){
        template.convertAndSend("topExchange","topic","测试主题消息");
    }
    /**
     * 广播式消息 队列测试
     */
    @RequestMapping(value = "/sendFanoutMessage",method = RequestMethod.GET)
    public void sendFanoutMessage(){
        template.convertAndSend("fanoutExchange","","测试广播消息");
    }

    /**
     *
     */
    @RequestMapping(value = "/robbingMobile",method = RequestMethod.GET)
    public void robbingMobile(){

        try{
            CountDownLatch countDownLatch = new CountDownLatch(1);
            for (int i = 0; i < 1000; i++) {
                new Thread(new RunThead(countDownLatch)).start();
            }
            countDownLatch.countDown();
        }catch (Exception e){
            e.getMessage();
        }
    }


    private class RunThead implements Runnable{

        private final CountDownLatch startLatch;
        public RunThead(CountDownLatch countDownLatch) {
            this.startLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                startLatch.await();
                mobile += 1;
               template.convertAndSend("topExchange","topic",String.valueOf(mobile));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试定时任务
     */
    @RequestMapping("/testTime")
    public void testTime(){

        try{
            Scheduler scheduler = new StdSchedulerFactory("quartz.properties").getScheduler();
            //Scheduler scheduler = schedulerFactoryBean.getScheduler();
            /*HolidayCalendar calendar = new HolidayCalendar();
            calendar.addExcludedDate(new Date(2019,5,5));
            scheduler.addCalendar("myCalander",calendar,false,false);*/
            JobDetail jobDetail = JobBuilder.newJob(MyFirstJob.class).withIdentity("first", "first").build();
            jobDetail.getJobDataMap().put("param","aaa");
            /*CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule("0/5 * * * * ?");
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("first", "first").withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);*/

            /*SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("first","first").
                    startAt(new Date())
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(3))
                    .forJob(jobDetail).build();
            scheduler.scheduleJob(jobDetail, simpleTrigger);*/

            CronTrigger cronTrigger = TriggerBuilder.newTrigger().
                    withIdentity("first","first")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                    .build();

            scheduler.scheduleJob(jobDetail, cronTrigger);

            scheduler.start();

            System.out.println("==============");
            Thread.sleep(60000);
            scheduler.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @RequestMapping(value = "/getCats",method = RequestMethod.GET)
    public List<Cat> getCats(Integer id){
        return util.getTree(id);
    }

    @RequestMapping(value = "/getCats2",method = RequestMethod.GET)
    public List<Cat> getCats2(Integer id){
        List<Cat> cats = catMapper.selectList(new EntityWrapper<>());
        return util.getTreeCat(cats,id);
    }

}
