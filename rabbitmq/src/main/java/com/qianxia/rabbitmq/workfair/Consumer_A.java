package com.qianxia.rabbitmq.workfair;

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
	
	private static final String QUEUE_NAME = "test_work_queue";
	
	public static void main(String[] args) throws Exception {
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//获取channel
		//final Channel channel = connection.createChannel();
		Channel channel = connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		channel.basicQos(1);//保证一次只分发一个
		
		//定义一个消费者
		DefaultConsumer consumer = new DefaultConsumer(channel) {

			//消息到达,就会触发此方法
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body, "utf-8");
				System.out.println("[A] consumer msg:"+msg);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("[A] done ");
					
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		//boolean autoAck = true;// 值为  true 的时候,是自动应答 
		boolean autoAck = false;// 值为  false 的时候,是手动应答 
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}
}
