package com.qianxia.rabbitmq.subscribe;

import java.io.IOException;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 	发布订阅模式
 * 	publish/subscribe
 * @author qianxia
 *
 */
public class Provider {
	
	//分发
	private static final String EXCHANGE_NAME = "test_exchange_fanout";
	
	public static void main(String[] args) throws Exception {
		
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//分发 -- 参数列表(交换机名称,"分发模式字段  --> fanout")
		
		//发送消息
		String msg = "hello subscribe";
		
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		
		System.out.println("send:"+msg);
		
		channel.close();
		connection.close();
		
	}
	
}
