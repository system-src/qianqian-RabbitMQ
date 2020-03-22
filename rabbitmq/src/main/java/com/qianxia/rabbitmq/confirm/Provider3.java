package com.qianxia.rabbitmq.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.qianxia.rabbitmq.utis.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

/**
 * confirm 批量模式
 * @author qianxia
 *
 */
public class Provider3 {

	private static final String QUEUE_NAME = "test_queue_confirm3";

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		//生产者 将 channel 设置 为 confirm 模式 ,(注: 如果已经将队列设置为 tx 模式,次做法会出现异常)
		channel.confirmSelect();
		
		//未确认的消息标识
		final SortedSet<Long> cofirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

		channel.addConfirmListener(new ConfirmListener() {

			//handleNack
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				if (multiple) {
					System.out.println("-------handleNack-----------multiple");
					cofirmSet.headSet(deliveryTag+1).clear();
				}else {
					System.out.println("-------handleNack-----------multiple---false");
					cofirmSet.remove(deliveryTag+1);
				}
			}

			//没有问题的handleAck
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if (multiple) {
					System.out.println("-------handleAck-----------multiple");
					cofirmSet.headSet(deliveryTag+1).clear();
				}else {
					System.out.println("-------handleAck-----------multiple---false");
					cofirmSet.remove(deliveryTag+1);
				}
			}
		});
		String msg = "vvvvvvvvvvvvvv";
		
		while (true) {
			long setNo = channel.getNextPublishSeqNo();
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			cofirmSet.add(setNo);
		}
	}
}
