package com.qianxia.rabbitmq.confirm;

import java.io.IOException;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * confirm 批量模式
 * @author qianxia
 *
 */
public class Provider2 {
	
	private static final String QUEUE_NAME = "test_queue_confirm2";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		//生产者 将 channel 设置 为 confirm 模式 ,(注: 如果已经将队列设置为 tx 模式,次做法会出现异常)
		channel.confirmSelect();
		String msg = "hello confirm message batch !";
		
		//批量发送
		for (int i = 0; i < 10; i++) {
			channel.basicPublish("", QUEUE_NAME,null, msg.getBytes());
		}

		//确认
		if (!channel.waitForConfirms()) {
			System.out.println("message send failed !");
		}else {
			System.out.println("message send ok !");
		}
		
		channel.close();
		connection.close();
		
	}
}
