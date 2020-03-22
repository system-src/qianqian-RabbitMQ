package com.qianxia.rabbitmq.topic;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 主题模式 topic
 * @author qianxia
 *
 */
public class Provider {
	
	private static final String EXCHANGE_NAME = "test_exchange_topic";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		String msg = "商品.......";
		
		channel.basicPublish(EXCHANGE_NAME, "goods.delete", null, msg.getBytes());
		System.out.println("---send:"+msg);
		
		channel.close();
		connection.close();
	}
}
