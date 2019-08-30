package com.example.rabbitmq.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @Description
 * @Date 2019/8/29 10:04
 */
@Component
public class SqsDealMessage {

    /*@RabbitListener(queues = "ordinary_message")
    public void dealSqsMessage(String message) throws Exception{

        System.out.println("消费信息：" + message);
    }
*/
    /**
     * 监听主题主题
     * @param message
     * @throws Exception
     */
    /*@RabbitListener(queues = "topic_message")
    public void dealTopicMessage(String message) throws Exception{

        System.out.println("消费主题交换机信息：" + message);
    }*/

    /**
     * 监听广播消息
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = "fanout_message")
    public void dealFanoutMessage(String message) throws Exception{

        System.out.println("消费广播式交换机信息：" + message);
    }

}
