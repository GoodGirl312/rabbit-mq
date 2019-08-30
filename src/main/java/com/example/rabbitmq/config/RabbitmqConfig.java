package com.example.rabbitmq.config;

import com.example.rabbitmq.controller.MqMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ys
 * @Description
 * @Date 2019/8/28 17:39
 */
@Configuration
public class RabbitmqConfig {
    private static final Logger log= LoggerFactory.getLogger(RabbitmqConfig.class);

    final static String ordinaryQueneMessage = "ordinary_message";
    final static String topicQueneMessage = "topic_message";

    final static String fanoutQueneMessage = "fanout_message";

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MqMessageListener mqMessageListener;
    @Bean
    public Queue ordinaryQuene(){

        return new Queue(RabbitmqConfig.ordinaryQueneMessage,true);
    }

    @Bean
    public Queue topicQuene(){
        return new Queue(RabbitmqConfig.topicQueneMessage,true);
    }

    @Bean
    public Queue fanoutQuene(){
        return new Queue(RabbitmqConfig.fanoutQueneMessage,true);
    }

    /**
     * 主题交换机
     */
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange("topExchange");
    }

    /**
     * 绑定队列到交换机
     */
    @Bean
    Binding bindingExchangeWithQuene(){
        return BindingBuilder.bind(topicQuene()).to(topicExchange()).with("topic");
    }

    /**
     * 广播式消息测试
     */
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingFanoutExchangeA(Queue ordinaryQuene,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(ordinaryQuene).to(fanoutExchange);
    }
    @Bean
    Binding bindingFanoutExchangeB(Queue topicQuene,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(topicQuene).to(fanoutExchange);
    }
    @Bean
    Binding bindingFanoutExchangeC(Queue fanoutQuene,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQuene).to(fanoutExchange);
    }

    /*@Bean
    public SimpleMessageListenerContainer listenerContainer(Queue  ordinaryQuene){
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setMessageConverter(new Jackson2JsonMessageConverter());

        // 并发配置
        listenerContainer.setConcurrentConsumers(10);
        listenerContainer.setMaxConcurrentConsumers(20);
        listenerContainer.setPrefetchCount(5);

        // 消息确认机制
        listenerContainer.setQueues(ordinaryQuene);
        listenerContainer.setMessageListener(mqMessageListener);
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return listenerContainer;
    }*/

}
