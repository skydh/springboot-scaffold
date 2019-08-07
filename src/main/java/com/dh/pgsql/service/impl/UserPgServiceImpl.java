package com.dh.pgsql.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dh.pgsql.dao.UserPgJpaDao;
import com.dh.pgsql.entity.PgUser;
import com.dh.pgsql.service.UserPgService;

/**
 * 我们约定在service层编写业务逻辑代码，但是不能写sql，sql要么使用jpa，要么使用spring jdbc,如此，我们方便管理 sql，利于开发
 * 
 * @author Lenovo
 *
 */
@Service
public class UserPgServiceImpl implements UserPgService {
	@Autowired
	private UserPgJpaDao userJpaDao;

	@Override
	public void save(PgUser user) {
		userJpaDao.save(user);

	}

}
