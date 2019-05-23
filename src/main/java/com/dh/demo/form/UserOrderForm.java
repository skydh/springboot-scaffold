package com.dh.demo.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserOrderForm {

	private int userId;
	@NotNull(message = "用户不能为空")
	@Length(min = 6, max = 10, message = "长度必须大于6，小于10")
	private String userName;
	private String address;
	private String orderName;

}
