package com.example.rabbitmq.controller;

import com.example.rabbitmq.config.MqConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}