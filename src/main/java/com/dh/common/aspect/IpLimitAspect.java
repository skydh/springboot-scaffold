package com.dh.common.aspect;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.dh.common.annotation.IpLimit;
import com.dh.common.exception.BusinessException;
import com.dh.common.utils.NetworkUtil;

/**
 * ip限制
 * 
 * @author Lenovo
 *
 */
@Aspect
@Component
@Order(2)
public class IpLimitAspect {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Pointcut("@annotation(com.pingan.haofang.pauct.common.annotation.IpLimit)")
	public void IpLimit() {
	}

	@Before("IpLimit()")
	public void logAnnotation(final JoinPoint joinPoint) {
		int times = 0;
		long timeUnit = 0L;
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		IpLimit ipLimit = method.getAnnotation(IpLimit.class);
		if (ipLimit != null) {
			times = ipLimit.times();
			timeUnit = ipLimit.timeUnit();
		}
		Object[] args = joinPoint.getArgs();
		if (args == null) {
			return;
		}
		HttpServletRequest httpServletRequest = null;
		for (int i = 0; i < args.length; i++) {
			if (HttpServletRequest.class.isInstance(args[i])) {
				httpServletRequest = (HttpServletRequest) args[i];

			}
		}
		try {
			String ip = NetworkUtil.getIpAddress(httpServletRequest);
			String count = redisTemplate.opsForValue().get(ip);
			int iCount = count == null ? 0 : Integer.parseInt(count);
			if (iCount < times) {
				redisTemplate.opsForValue().set(ip, String.valueOf(iCount + 1), timeUnit, TimeUnit.SECONDS);
			} else {
				throw new BusinessException("ip limit");
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}