package com.dh.demo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dh.demo.entity.User;
import com.dh.demo.vo.UserOrderVO;

/**
 * 
 * 基于spring jdbc 的写法，涉及到基本连表查询，批量插入等基本操作。 主要针对复杂查询。
 * 
 * @author Lenovo
 *
 */
@Repository
public class UserDao {
	@Resource
	@Qualifier("mysqlJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 多表复杂查询
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("all")
	public List<UserOrderVO> getListDataByUserId(int userId) {
		List<UserOrderVO> listVO = jdbcTemplate.query(
				"select a.id as userId from user a inner join orders b on a.id=b.user_id where a.id=? and b.name=?",
				new BeanPropertyRowMapper(UserOrderVO.class), userId, "asas");

		return listVO;

	}

	/**
	 * 批量插入。其余批量操作可以类似处理
	 * 
	 */
	public void batchInsert(List<User> list) {
		jdbcTemplate.batchUpdate("insert into user (id,name,address) values (?, ?, ?)",
				new BatchPreparedStatementSetter() {
					// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, list.get(i).getId());
						ps.setString(2, list.get(i).getName());
						ps.setString(3, list.get(i).getAddress());
					}
					/**
					 * 这里设置循环次数
					 */
					@Override
					public int getBatchSize() {
						return list.size();
					}

				});
	}

	// 更新
	public void update(User user) {
		String sql = "update user set name = ? where id = ?";
		Object args[] = new Object[] { user.getName(), user.getId() };
		int temp = jdbcTemplate.update(sql, args);
		if (temp > 0) {
			System.out.println("更新成功");
		} else {
			System.out.println("更新失败");
		}

	}

}
