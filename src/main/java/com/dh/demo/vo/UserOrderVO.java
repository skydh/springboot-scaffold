package com.dh.demo.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserOrderVO {

	private int userId;
	@NotNull(message = "用户不能为空")
	@Length(min = 6, max = 10, message = "长度必须大于6，小于10")
	private String userName;
	@NotNull(message = "asd不能为空")
	@Length(min = 6, max = 10, message = "aq1asd长度必须大于6，小于10")
	private String address;
	private String orderName;

}
