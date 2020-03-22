package com.qianxia.rabbitmq.subscribe;

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
public class Consumer_B {
	
	private static final String EXCHANGE_NAME = "test_exchange_fanout";
	
	private static final String QUEUE_NAME = "test_queue_fanout_email_b";
	
	public static void main(String[] args) throws Exception {
		
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		//声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		//绑定队列到交换机 转发器
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		
		channel.basicQos(1);
		
		DefaultConsumer consumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("[B] consumer msg:"+msg);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("[B] done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
			
		};
		boolean autoAck = false; //手动模式 false
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
		
	}
}
