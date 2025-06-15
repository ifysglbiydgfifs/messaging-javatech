package com.example.messagingrabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class MessagingRabbitmqApplication {

	static final String fanoutExchangeName = "spring-boot-fanout-exchange";

	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange(fanoutExchangeName);
	}

	@Bean
	Queue anonymousQueue() {
		return new AnonymousQueue();
	}

	@Bean
	Binding binding(Queue anonymousQueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(anonymousQueue).to(fanoutExchange);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter, Queue anonymousQueue) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(anonymousQueue);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static void main(String[] args) {
		SpringApplication.run(MessagingRabbitmqApplication.class, args);
	}
}
