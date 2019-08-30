package com.example.rabbitmq.controller;

import com.example.rabbitmq.service.IProductService;
import com.example.rabbitmq.service.IUserService;
import com.example.rabbitmq.service.impl.ProductServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @Description
 * @Date 2019/8/29 15:25
 */
@Log4j2
@Component
public class MqMessageListener implements ChannelAwareMessageListener {

    @Autowired
    private IProductService productService ;

    @Override
    @RabbitListener(queues = "topic_message")
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try{
            byte[] body = message.getBody();
            String mobile = new String(body,"utf-8");
            log.info("监听到抢单手机号：" + mobile);
            productService.dealUserRobbing(mobile);
            channel.basicAck(tag,true);
        }catch (Exception e){
            log.error("抢单失败：" + e.getMessage());
            channel.basicAck(tag,false);
        }

    }
}
