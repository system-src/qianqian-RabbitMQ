package com.qianxia.rabbitmq.topic;

import java.io.IOException;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 
 * @author qianxia
 *
 */
public class Consumer_A {
	
	private static final String EXCHANGE_NAME = "test_exchange_topic";
	private static final String QUEUE_NAME = "test_queue_topic_a";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicQos(1);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "godds.update");
		
		DefaultConsumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("[A] consumer msg:"+msg);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("[A] done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}			
		};
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);	
	}
}
