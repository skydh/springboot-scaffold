package com.dh.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IpLimit {

	/***
	 * 次数
	 * 
	 * @return
	 */
	int times() default 3;

	/***
	 * 过期时间/秒
	 * 
	 * @return
	 */
	long timeUnit() default 3600L;

}
