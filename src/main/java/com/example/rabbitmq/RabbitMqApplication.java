package com.example.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 18070016
 */
@EnableRabbit
@SpringBootApplication
/*@ComponentScan(basePackages = {"com.example.rabbitmq"})*/
@MapperScan(basePackages = {"com.example.rabbitmq.mapper"})
public class RabbitMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqApplication.class, args);
	}

}
