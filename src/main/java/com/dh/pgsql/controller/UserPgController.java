package com.dh.pgsql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dh.pgsql.entity.PgUser;
import com.dh.pgsql.service.UserPgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "UserPgController", description = "pgsql jpa")
@Controller
@RequestMapping(value = "/hello111")
@RestControllerAdvice
public class UserPgController {

	@Autowired
	private UserPgService service;

	@ApiOperation(value = "save")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public PgUser getById(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "age", required = true) String age) {
		PgUser user = new PgUser();
		user.setName(userName);
		user.setAge(Integer.parseInt(age));
		user.setId(Integer.parseInt(id));
		service.save(user);
		return user;
	}

}
