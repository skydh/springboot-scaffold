package com.dh.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dh.common.exception.BusinessException;
import com.dh.demo.dao.UserDao;
import com.dh.demo.dao.UserJpaDao;
import com.dh.demo.entity.User;
import com.dh.demo.service.UserService;
import com.dh.demo.vo.UserOrderVO;

/**
 * 我们约定在service层编写业务逻辑代码，但是不能写sql，sql要么使用jpa，要么使用spring jdbc,如此，我们方便管理 sql，利于开发
 * 
 * @author Lenovo
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserJpaDao userJpaDao;
	@Autowired
	private UserDao userDao;

	@Override
	public User getById(Integer id) {

		return userJpaDao.findById(id);
	}

	@Override
	public List<UserOrderVO> getUserOrder(int userId) {

		return userDao.getListDataByUserId(userId);
	}

	@Override
	public void batchInsert(List<User> user) {

		userDao.batchInsert(user);

	}

	@Override
	public void exceptionTry() {
		throw new BusinessException("common.property.type.convert.error");

	}

	@Override
	public Page<User> findByName(String name, Pageable pageable) {
		
		// System.out.println(userJpaDao.findUserByName(name,
		// pageable).getClass());
		return userJpaDao.findUserByName(name, pageable);
		// return userJpaDao.findByName(name, pageable);
	}

	@Resource
	@Qualifier("mysqlJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

//	@Autowired
//	@Qualifier("pgJdbcTemplate")
//	protected JdbcTemplate jdbcTemplate2;

	@Override
	@Transactional(value = "transactionManager")
	public void transactionTry() {
		

		User user = new User();

		
		userJpaDao.save(user);
		
	}

}
