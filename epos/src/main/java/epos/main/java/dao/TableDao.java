package epos.main.java.dao;

import java.sql.SQLException;

import epos.main.java.core.JDBCDaoSupport;

public class TableDao extends JDBCDaoSupport {
	
	public static TableDao getInstance(){
		return new TableDao();
	}
	
	public void activeService() throws SQLException{
		getJdbcDaoTemplate().update("", "");
	}
}
