package com.dh.common.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否校验
 * 
 * @author Lenovo
 *
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid {
	/**
	 * true 表示多例 flase表示单例
	 * 
	 * @return
	 */
	boolean type() default false;

}