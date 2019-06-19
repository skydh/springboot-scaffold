package com.dh.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否进行非法参数校验
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IllegalRequest {

	/**
	 * 其余非法参数
	 * 
	 * @return
	 */
	String[] Illegal() default { "" };

	/**
	 * 校验参数游标
	 * 
	 * @return
	 */
	int[] cursor() default { 0 };

}