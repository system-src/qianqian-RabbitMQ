package com.qianxia.rabbitmq.tx;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 	rabbitMQ 事务
 * @author qianxia
 *
 */
public class TxProvider {

	private static final String QUEUE_NAME = "test_queue_tx";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		String msg = "hello tx message !";
		
		try {
			channel.txSelect();
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			//int num = 1/0; 回滚测试 
			channel.txCommit();
		} catch (Exception e) {
			channel.txRollback();
			System.out.println("send message txRollback ! ");
		}	finally {
			channel.close();
			connection.close(); 
		}
		
	}
}
