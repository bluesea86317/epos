package epos.main.java.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


public class TableDao extends SqlMapClientDaoSupport {
	
	public static TableDao getInstance(){
		return new TableDao();
	}
	
	public void activeService() throws SQLException{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("testId", 1);
		paramMap.put("testName", "chenwen");
//		getJdbcDaoTemplate().update("Table.testUpdate", paramMap);
		getSqlMapClientTemplate().update("Table.testUpdate", paramMap);
		System.out.println("call activeService");
		throw new SQLException("Test");
		
	}
	
	public void nonActiveTable() throws SQLException{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("testId", 2);
		paramMap.put("testName", "test");
		getSqlMapClientTemplate().update("Table.testUpdate", "");
		System.out.println("call nonActiveTable");
	}
}
