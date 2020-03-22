package com.qianxia.rabbitmq.simple;

import java.io.IOException;

import javax.management.Query;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 *  	消息获取者 
 * @author qianxia
 *
 */
@SuppressWarnings("deprecation")
public class Consumer {
	private static final String QUEUE_NAME="test_simple_queue";

	public static void main(String[] args) throws Exception {
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//创建通道
		Channel channel = connection.createChannel();
		
		//----------- 新版本 ----------------
		//队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			//创建DefaultConsumer内部类, 重写handleDelivery 方法,一旦监听到队列列有消息,就会触发此方法执行
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, 
					byte[] body) throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("new api consumer:"+msg);	
			}
		};
		//监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

	private static void orlVersion() throws IOException, Exception, InterruptedException {
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//创建通道
		Channel channel = connection.createChannel();

		//------------ 老版本的写法 ---------------
		//定义消费者队列
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
		while (true) {
			Delivery delivery = consumer.nextDelivery();
			String msgString = new String(delivery.getBody());
			System.out.println("consumer :"+msgString);
		}
	}
}
