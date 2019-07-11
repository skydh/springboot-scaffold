package com.dh.redis;

import java.util.Collections;
import java.util.Map;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStreamCommands;

public class RedisStream {

	public static void main(String[] args) throws Exception {

		RedisClient client = RedisClient.create("redis://192.168.147.129");
		StatefulRedisConnection<String, String> connection = client.connect();
		RedisStreamCommands<String, String> streamCommands = connection.sync();
		Map<String, String> body = Collections.singletonMap("aaa", "vaaaalue");
		streamCommands.xadd("a", body);

	}

}