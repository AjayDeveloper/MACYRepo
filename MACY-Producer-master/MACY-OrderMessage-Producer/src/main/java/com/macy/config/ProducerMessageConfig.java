package com.macy.config;

import static util.Constant.EXCHANGE;
import static util.Constant.QUEUE_JSON;
import static util.Constant.QUEUE_XML;
import static util.Constant.ROUTING_JSON_KEY;
import static util.Constant.ROUTING_XML_KEY;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;

@Configuration
public class ProducerMessageConfig {

	@Bean
	public Queue queueJson() {

		return new Queue(QUEUE_JSON, true);
	}

	@Bean
	public TopicExchange exchange() {

		return new TopicExchange(EXCHANGE);
	}

	@Bean
	public Binding bindingJson(TopicExchange exchange) {

		return BindingBuilder.bind(queueJson()).to(exchange).with(ROUTING_JSON_KEY);
	}

	@Bean
	public MessageConverter converterJson() {

		return new Jackson2JsonMessageConverter();
	}

	
	@Bean
	public MessageConverter converterXml() {

		return new Jackson2XmlMessageConverter();
	}

	
	@Bean
	public AmqpTemplate templatXml(ConnectionFactory connectionFactory) {

		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converterXml());
		rabbitTemplate.setRoutingKey(ROUTING_XML_KEY);
		rabbitTemplate.setDefaultReceiveQueue(QUEUE_XML);	
		return rabbitTemplate;
	}
	
	@Bean
	public AmqpTemplate templateJson(ConnectionFactory connectionFactory) {

		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setRoutingKey(ROUTING_JSON_KEY);
		rabbitTemplate.setDefaultReceiveQueue(QUEUE_JSON);	
		rabbitTemplate.setMessageConverter(converterJson());
		
		return rabbitTemplate;
	}

	@Bean
	public Binding bindingXml(TopicExchange exchange) {

		return BindingBuilder.bind(queueXml()).to(exchange).with(ROUTING_XML_KEY);
	}

	@Bean
	public Queue queueXml() {

		return new Queue(QUEUE_XML, true);
	}

}
