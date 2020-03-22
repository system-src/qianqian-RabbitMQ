package com.qianxia.rabbitmq.simple;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 	简单模式
 * @author qianxia
 *
 */
public class Provider {
	private static final String QUEUE_NAME="test_simple_queue";
	
	public static void main(String[] args) throws Exception {
		//获取一个连接
		Connection connection = ConnectionUtils.getConnection();
		//从连接中获取一个通道
		Channel channel = connection.createChannel();
		//声明一个队列 
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		String msg = "hello simple !";
		
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		System.out.println("---send msg:"+msg);
		
		channel.close();
		connection.close();
	}
}
