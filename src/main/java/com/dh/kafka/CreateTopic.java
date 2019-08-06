package com.dh.kafka;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

/**
 * 创建主题试试 使用KafkaAdminClient来创建主题。
 * 
 * @author Lenovo
 *
 */
public class CreateTopic {
	public static void main(String[] args) {
		String brokerList = "192.168.147.132:9092";
		String topic = "topic-admin";
		Properties props = new Properties();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
		props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
		AdminClient client = AdminClient.create(props);
		NewTopic newTopic = new NewTopic(topic, 4, (short) 1);
		CreateTopicsResult result = client.createTopics(Collections.singleton(newTopic));
		try {
			result.all().get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.close();
	}
}
