package com.dh.common.pagination;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PageHelper {
	@Resource
	@Qualifier("mysqlJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 多表复杂查询
	 * 
	 * @param <T>
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("all")
	public <T> Page<T> getListDataByUserId(String sql, Pageable pageable, Class<T> classes, Object... args) {
		String pageSql = getPageSql(sql, pageable);
		System.out.println(pageSql);
		List<T> listVO = jdbcTemplate.query(pageSql, new BeanPropertyRowMapper(classes), args);
		String conutSql = getCountSql(sql);
		System.out.println(conutSql);
		int total = jdbcTemplate.queryForObject(conutSql, Integer.class, args);
		return new PageImpl<T>(listVO, pageable, total);
	}

	/**
	 * 获取count sql
	 * 
	 * @param sql
	 * @return
	 */
	public String getCountSql(String sql) {
		StringBuilder countSql = new StringBuilder();
		countSql.append("select count(*) from  ");
		String[] mSql = sql.split("from");
		countSql.append(mSql[1]);
		return countSql.toString();

	}

	/**
	 * 获取分页sql
	 * 
	 * @param sql
	 * @param pageable
	 * @return
	 */
	public String getPageSql(String sql, Pageable pageable) {
		StringBuilder pageSql = new StringBuilder();
		pageSql.append(sql);
		Sort sort = pageable.getSort();
		Stream<Order> streams = sort.get();
		List<Order> listOrder = streams.collect(Collectors.toList());
		if (listOrder.size() > 0) {
			pageSql.append("  order by  ");
		}
		for (Order order : listOrder) {
			pageSql.append(order.getProperty() + "  " + order.getDirection() + "  ,");
		}
		pageSql.deleteCharAt(pageSql.length() - 1);
		pageSql.append("  limit  " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());

		return pageSql.toString();

	}

}
