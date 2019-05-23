package com.dh.common.utils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

public class RedisLockUtils {
	/**
	 * 加锁
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 * @param timeUnit
	 * @return
	 */
	public boolean setLock(StringRedisTemplate redisTemplate,String key, String value, int expireTime, TimeUnit timeUnit) {
		return redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
	}

	/**
	 * 解锁
	 * 
	 * @param key
	 * @param value
	 * @return
	 * 
	 */
	public boolean releaseLock(StringRedisTemplate redisTemplate,String key, String value) {

		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptText(script);
		redisScript.setResultType(Long.class);
		return redisTemplate.execute(redisScript, Collections.singletonList(key), value) == 1 ? true : false;

	}	

}
