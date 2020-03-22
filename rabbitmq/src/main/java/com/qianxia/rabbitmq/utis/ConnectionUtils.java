package com.qianxia.rabbitmq.utis;

import java.io.IOException;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @author qianxia
 *
 */
public class ConnectionUtils {
	
	/** 获取MQ连接 
	 * @throws Exception 
	 * @throws IOException */
	public static Connection getConnection() throws IOException, Exception {
		//定义一个连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置服务器地址
		factory.setHost("127.0.0.1");
		//设置端口号
		factory.setPort(5672);
		//设置VirtualHost
		factory.setVirtualHost("/qianxia");
		//设置用户名
		factory.setUsername("qianxia");
		//设置密码
		factory.setPassword("654321");
		return factory.newConnection();
	}
}
