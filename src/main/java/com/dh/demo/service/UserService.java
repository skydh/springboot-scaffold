package com.dh.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dh.demo.entity.User;
import com.dh.demo.vo.UserOrderVO;

public interface UserService {
	User getById(Integer id);

	List<UserOrderVO> getUserOrder(int userId);

	void batchInsert(List<User> user);

	void exceptionTry();

	Page<User> findByName(String name, Pageable pageable);

	void transactionTry();

}
