package com.dh.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * 
 * @author Lenovo
 *
 */
public class SimpleKafkaProducer {
	private static String brokeList = "192.168.147.132:9092";
	private static String topic = "topic-demo111";

	public static Properties init() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokeList);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "produce.client.id.demo111");
		return props;
	}

	public static void main(String[] args) {
		Properties props = init();
		// broker地址

		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello, kafka");
		try {
			producer.send(record, new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception != null)
						exception.printStackTrace();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Message sent successfully");
		producer.close();
	}
}