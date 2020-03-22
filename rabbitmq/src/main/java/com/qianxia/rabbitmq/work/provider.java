package com.qianxia.rabbitmq.work;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 	轮询分发
 * @author qianxia
 *
 */
public class provider {
	
	private static final String QUEUE_NAME = "test_work_queue";
	
	public static void main(String[] args) throws Exception {
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//获取通道
		Channel channel = connection.createChannel();
		
		//声明一个队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		for (int i = 0; i < 50 ; i++) {
			String msg = "hello "+i;
			System.out.println("[MQ]provider:"+msg);
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(i*20);
		}
		channel.close();
		connection.close();
	}
}
