package com.dh.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 非法参数枚举
 * @author Lenovo
 *
 */
public enum IllegalEnum {
	ILLEGAL_1("script"),
	ILLEGAL_2("src"),
	ILLEGAL_3("mid"),
	ILLEGAL_4("master"),
	ILLEGAL_5("truncate"),
	ILLEGAL_6("insert"),
	ILLEGAL_7("select"),
	ILLEGAL_8("delete"),
	ILLEGAL_9("update"),
	ILLEGAL_10("declare"),
	ILLEGAL_11("iframe"),
	ILLEGAL_12("onreadystatechange"),
	ILLEGAL_13("alert"),
	ILLEGAL_14("atestu"),
	ILLEGAL_15("xss"),
	ILLEGAL_16(";"),
	ILLEGAL_17("'"),
	ILLEGAL_18("\""),
	ILLEGAL_19("<"),
	ILLEGAL_20(">"),
	ILLEGAL_21("+"),
	ILLEGAL_22("\\"),
	ILLEGAL_23("svg"),
	ILLEGAL_24("confirm"),
	ILLEGAL_25("prompt"),
	ILLEGAL_26( "onload"),
	ILLEGAL_27("onmouseover"),
	ILLEGAL_28("onfocus"),
	ILLEGAL_29("onerror" );
	
	private String key;

	IllegalEnum(String key) {
		this.key = key;

	}

	public String getKey() {
		return key;
	}
	
	public  static List<String> getEnumList() {
		 List<String> list=new ArrayList<String>();
		for (IllegalEnum illegalEnum : IllegalEnum.values()) {
			list.add(illegalEnum.getKey());	
		}
		return list;
	}
}
