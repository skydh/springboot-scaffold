package com.dh.common.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.dh.common.annotation.IllegalRequest;
import com.dh.common.enums.IllegalEnum;
import com.dh.common.exception.IllegalException;
import com.dh.common.utils.IllegalInterface;

/**
 * 参数校验
 * 
 * @author Lenovo
 *
 */
@Aspect
@Component
public class IllegalRequestParamAspect {

	@Pointcut("@annotation(com.dh.common.annotation.IllegalRequest)")
	public void IllegalRequest() {
	}

	@Before("IllegalRequest()")
	public void logAnnotation(final JoinPoint joinPoint) {

		String[] extra = null;
		int[] cursor = null;
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		IllegalRequest illegalRequest = method.getAnnotation(IllegalRequest.class);
		if (illegalRequest != null) {
			extra = illegalRequest.Illegal();
			cursor = illegalRequest.cursor();
		}
		List<String> list = IllegalEnum.getEnumList();
		Collections.addAll(list, extra);

		Object[] args = joinPoint.getArgs();
		if (args == null) {
			return;
		}
		for (int temp : cursor) {
			doDistinguish(args[temp], list);
		}

	}

	/**
	 * 参数中符合json格式接受，目前，认定为直接参数为string,list,map
	 * 
	 * @param obj
	 */

	public void doDistinguish(Object obj, List<String> list) {
		if (String.class.isInstance(obj)) {
			doValid((String) obj, list);
		} else if (List.class.isInstance(obj)) {
			doList(obj, list);
		} else if (Map.class.isInstance(obj)) {
			doMap(obj, list);
		} else if (IllegalInterface.class.isInstance(obj)) {
			doObject(obj, list);
		}
	}

	/**
	 * list处理
	 * 
	 * @param obj
	 * @param list
	 */
	public void doList(Object obj, List<String> list) {
		for (Object param : (List<?>) obj) {
			doDistinguish(param, list);
		}
	}

	/**
	 * map 处理
	 * 
	 * @param obj
	 * @param list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doMap(Object obj, List<String> list) {
		Map map = (Map) obj;
		Set<Map.Entry> set = map.entrySet();
		for (Map.Entry entry : set) {
			doDistinguish(entry.getKey(), list);
			doDistinguish(entry.getValue(), list);
		}
	}

	/**
	 * obj处理
	 * 
	 * @param obj
	 * @param list
	 */
	public void doObject(Object obj, List<String> list) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				doDistinguish(field.get(obj), list);
			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * 这里做校验
	 * 
	 * @param str
	 * @param list
	 */
	public void doValid(String str, List<String> list) {
		str = str.toLowerCase(); // sql不区分大小写
		for (String param : list) {
			if (str.contains(param)) {

				throw new IllegalException(param);
			}
		}
	}

}
