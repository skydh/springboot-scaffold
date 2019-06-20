package com.dh.common.aspect;

import java.lang.reflect.Method;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.dh.common.annotation.RedisJobLock;
import com.dh.common.exception.RedisJobException;
import com.dh.common.utils.RedisLockUtils;

/**
 * 集群分布式单节点加锁
 * @author Lenovo
 *
 */
@Aspect
@Component
public class JobLockAspect {

	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Pointcut("@annotation(com.dh.common.annotation.RedisJobLock)")
	public void redisJobAspect() {

	}

	@Around("redisJobAspect()")
	public Object doAround(ProceedingJoinPoint joinPoint) {
		Object result = null;
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		RedisJobLock lock = method.getAnnotation(RedisJobLock.class);
		String lockKey = getKey(method);
		String value = getValue();
		try {
			if (RedisLockUtils.setLock(redisTemplate,lockKey, value, lock.expire(), lock.timeUnit())) {
				try {
					result = joinPoint.proceed();
				} catch (Throwable e) {
					throw new RuntimeException("runtime.error");
				}
			} else {

				throw new RedisJobException("lock.error");
			}
		} finally {
			if (!RedisLockUtils.releaseLock(redisTemplate,lockKey, value)) {
				throw new RedisJobException("unlock.error");
			}
		}
		return result;
	}

	/**
	 * key值采用方法的全限定名+参数来限定。
	 * 
	 * @param joinPoint
	 * @return
	 */
	public String getKey(Method method) {
		return method.toString().replace(" ", ":");
	}

	/**
	 * 获取当前sessionId
	 * 
	 * @param joinPoint
	 * @return
	 */
	public String getValue() {
		return UUID.randomUUID().toString();
	}


}
