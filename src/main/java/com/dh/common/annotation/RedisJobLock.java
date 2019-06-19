package com.dh.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisJobLock {

	/***
	 * 过期时间
	 * 
	 * @return
	 */
	int expire() default 5;

	/***
	 * 超时时间单位
	 * 
	 * @return
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

}
