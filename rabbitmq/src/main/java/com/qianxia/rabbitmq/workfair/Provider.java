package com.qianxia.rabbitmq.workfair;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 	公平分发 
 * 	fair dipatch
 * @author qianxia
 *
 */
public class Provider {
	
	private static final String QUEUE_NAME = "test_work_queue";
	
	public static void main(String[] args) throws Exception {
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//获取通道
		Channel channel = connection.createChannel();
		
		//声明一个队列,将第二个参数 改为 true 的时候 就开启了 消息的持久化 
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		/**
		 * 每个消费者送达确认消息之前,队列不会发送下一个到消费者,一次只处理一个消息
		 * 
		 * 限制发送同一个消费者不得超过一条
		 */
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		
		for (int i = 0; i < 50 ; i++) {
			String msg = "msg: hello "+i;
			System.out.println("[MQ]provider:"+msg);
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(i*5);
		}
		channel.close();
		connection.close();
	}
}
