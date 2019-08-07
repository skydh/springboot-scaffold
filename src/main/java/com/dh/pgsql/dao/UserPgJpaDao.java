package com.dh.pgsql.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dh.pgsql.entity.PgUser;

;

/**
 * 基础jpa查询，基本单表查询，基本单表插入都使用其开发，开发效率很高。
 * 
 * @author Lenovo
 */
public interface UserPgJpaDao extends JpaRepository<PgUser, Serializable> {

	

}