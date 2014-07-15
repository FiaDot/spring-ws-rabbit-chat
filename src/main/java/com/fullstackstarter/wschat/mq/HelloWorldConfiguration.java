package com.fullstackstarter.wschat.mq;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {

	protected final String helloWorldQueueName = "hello.world.queue";

	private static final Logger logger = LoggerFactory.getLogger(HelloWorldConfiguration.class);

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		return connectionFactory;
	}
	
	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		//The routing key is set to the name of the queue by the broker for the default exchange.
		
		
		// TODO : set exchange opt!!!!
		// template.setExchange(exchange);
		
		template.setRoutingKey(this.helloWorldQueueName);
		//Where we will synchronously receive messages from
		template.setQueue(this.helloWorldQueueName);
				
		return template;
	}

	@Bean
	// Every queue is bound to the default direct exchange
	public Queue helloWorldQueue() {
		return new Queue(this.helloWorldQueueName);
	}

	
	/*
	 * Register Listener 
	 */
	
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(this.helloWorldQueueName);
		// container.setQueueName(this.helloWorldQueueName);
		container.setMessageListener(exampleListener());
		return container;
	}

	@Bean
	public MessageListener exampleListener() {
		return new MessageListener() {
			@Override
			public void onMessage(Message arg0) {
				
				logger.info("rabbitmq listener received: " + arg0);
				// System.out.println("received: " + arg0);				
			}
		};
	}
	
}
