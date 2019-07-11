package com.dh.kafka;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

public class SimpleKafkaConsumer {

	private static final String brokeList = "192.168.147.131:9092";
	private static final String topic = "topic-demo111";
	private static final String groupId = "group.demo";
	private static final AtomicBoolean isRunning = new AtomicBoolean(true);

	public static Properties init() {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokeList);

		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		// props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, value)
		props.put("group.id", groupId);
		props.put("client.id", "consumer.client.id.demo111");
		return props;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Properties props = init();

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		// consumer.position(partition)
		// consumer.committed(partition)
		Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
		consumer.subscribe(Collections.singletonList(topic), new ConsumerRebalanceListener() {
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
				consumer.commitSync(offsets);
				offsets.clear();
			}

			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

			}
		});
		consumer.subscribe(Pattern.compile("topic-.*"));
		consumer.assign(Collections.singletonList(new TopicPartition(topic, 0)));
		List<PartitionInfo> list = consumer.partitionsFor(topic);
		consumer.unsubscribe();
		consumer.commitAsync();
		consumer.commitSync();
		// consumer.position(partition)
		// consumer.offsetsForTimes(timestampsToSearch)
		Set<TopicPartition> set1 = consumer.assignment();
		// consumer.seek(partition, offset);
		// Set<TopicPartition> set = consumer.paused();
		try {
			while (isRunning.get()) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
				for (TopicPartition tp : records.partitions()) {
					List<ConsumerRecord<String, String>> pRecords = records.records(tp);
					for (ConsumerRecord<String, String> record : pRecords)
						System.out.printf(record.value());
					long lastOffset = pRecords.get(pRecords.size() - 1).offset();
					consumer.commitSync(Collections.singletonMap(tp, new OffsetAndMetadata(lastOffset + 1)));
					consumer.commitAsync(new OffsetCommitCallback() {
						@Override
						public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
							// 这里操作

						}
					});
				}

			}
		} finally {
			consumer.close();
		}
	}
}