package com.dh.common.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义上下文
 * 
 * @author Lenovo
 *
 */
public class WebApplicationContext {

	private final static ThreadLocal<Map<String, Object>> resources = ThreadLocal.withInitial(() -> {
		return new HashMap<>();
	});

	public static Object getByKey(String key) {
		return resources.get().get(key);
	}

	public static void setData(String key, Object obj) {
		resources.get().put(key, obj);
	}

}
