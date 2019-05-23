package com.dh.demo.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dh.demo.entity.User;

/**
 * 基础jpa查询，基本单表查询，基本单表插入都使用其开发，开发效率很高。
 * 
 * @author Lenovo
 */
public interface UserJpaDao extends JpaRepository<User, Serializable> {

	User findById(Integer id);

	User findByName(String name);

	/**
	 * 根据name,id 找这个人
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	User findByNameAndId(String name, Integer id);

	@Query(value = "select * from User where address=:address", nativeQuery = true)
	List<User> findUserByAddress(@Param("address") String address);

	@Transactional
	@Modifying
	@Query(value = "update User set address= :address where name= :name")
	void updateData(@Param("name") String name, @Param("address") String address);

	Page<User> findByName(String name, Pageable pageable);

	@Query(value = "select  u from User u where u.name=:name")
	Page<User> findUserByName(@Param("name") String name, Pageable pageable);

}