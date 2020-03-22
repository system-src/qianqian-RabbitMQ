package com.qianxia.rabbitmq.routing;

import java.io.IOException;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 *  路由模式
 * @author qianxia
 *
 */
public class Provider {
	
	private static final String EXCHANGE_NAME = "test_exchange_direct";
	
	public static void main(String[] args) throws IOException, Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		//exchange
		channel.exchangeDeclare(EXCHANGE_NAME,"direct");
		
		String msg = " hello direct !";
		
		String routingKey = "error";
		//String routingKey = "info";
		//String routingKey = "warning";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());;
		
		channel.close();
		connection.close();
		
		
	}
}
