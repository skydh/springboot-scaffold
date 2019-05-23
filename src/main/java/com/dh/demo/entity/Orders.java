package com.dh.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Orders {
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "name")
	private String name;
}
