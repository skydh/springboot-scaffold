package com.dh.kafka;

/**
 * 创建主题试试
 * 
 * @author Lenovo
 *
 */
public class CreateTopic {

	public static void main(String[] args) {

		String[] opts = new String[] { "--zookeeper", ":/2181/kafka", "--create", "--replication-factor", "1",
				"--topic", "topic-create-api" };
		kafka.admin.TopicCommand.main(opts);
	}

}
