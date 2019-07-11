package com.dh.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProduceInterceptor implements ProducerInterceptor<String, String> {
	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
	}
	@Override
	public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
		return record;
	}
	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		// TODO Auto-generated method stub

	}
	@Override
	public void close() {
		// TODO Auto-generated method stub
	}
}
