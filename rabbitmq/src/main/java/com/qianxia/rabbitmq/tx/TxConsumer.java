package com.qianxia.rabbitmq.tx;

import java.io.IOException;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 
 * @author qianxia
 *
 */
public class TxConsumer {
	
	private static final String QUEUE_NAME = "test_queue_tx";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body, "utf-8");
				System.out.println("txconsumer msg:"+msg);
			}
		});
	}
}
