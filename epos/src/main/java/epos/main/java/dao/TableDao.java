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
		
		
	}
	
	public void nonActiveTable() throws SQLException{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
	}
}
