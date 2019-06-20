package com.dh.kafka;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
/**
 * 
 * @author Lenovo
 *
 */
public class SimpleKafkaProducer {
	public static void main(String[] args) {
		Properties props = new Properties();
		// broker地址
		props.put("bootstrap.servers", "192.168.147.131:9092");
		// 指定消息key序列化方式
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// 指定消息本身的序列化方式
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		ProducerRecord<String, String> record = new ProducerRecord<>("topic-demo111", "hello, kafka");
		try {
			producer.send(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Message sent successfully");
		producer.close();
	}
}