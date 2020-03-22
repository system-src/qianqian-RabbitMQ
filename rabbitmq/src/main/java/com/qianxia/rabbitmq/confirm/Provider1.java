package com.qianxia.rabbitmq.confirm;

import java.io.IOException;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * confirm 普通模式
 * @author qianxia
 *
 */
public class Provider1 {
	
	private static final String QUEUE_NAME = "test_queue_confirm";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		//生产者 将 channel 设置 为 confirm 模式 ,(注: 如果已经将队列设置为 tx 模式,次做法会出现异常)
		channel.confirmSelect();
		String msg = "hello confirm message !";
		channel.basicPublish("", QUEUE_NAME,null, msg.getBytes());
		
		if (!channel.waitForConfirms()) {
			System.out.println("message send failed !");
		}else {
			System.out.println("message send ok !");
		}
		
		channel.close();
		connection.close();
		
	}
}
