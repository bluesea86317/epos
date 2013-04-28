package epos.main.java.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibatis.sqlmap.client.SqlMapClient;


public class JDBCDaoTemplate{
	
	private SqlMapClient sqlMapClient;
	
	public JDBCDaoTemplate(){
	}
	
	public JDBCDaoTemplate(SqlMapClient sqlMapClient){	
		this.sqlMapClient = sqlMapClient;
	}
	
	/**
	 * Sql statement of insert
	 * @param sqlMapConfig
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public int insert(String sqlMapConfig, Object param) throws SQLException{
//		返回自增主键
		int the_first_autocrement_primaryKey = 0;		
		Object object = sqlMapClient.insert(sqlMapConfig, param);
		if(object != null){
			the_first_autocrement_primaryKey = (Integer)object;
		}		
		sqlMapClient.commitTransaction();		
		return the_first_autocrement_primaryKey;
		
	}
	
	/**
	 * Sql statement of update
	 * @param sqlMapConfig
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public int update(String sqlMapConfig, Object param) throws SQLException{
		int updateCount = 0;
		updateCount = sqlMapClient.update(sqlMapConfig, param);
		sqlMapClient.commitTransaction();
		return updateCount;
	}
	
	@SuppressWarnings("rawtypes")
	public List queryForList(String sqlMapConfig, Object param) throws SQLException{
		List resultList = new ArrayList<Object>();		
		resultList = sqlMapClient.queryForList(sqlMapConfig, param);				
		return resultList;
	}
	
	@SuppressWarnings("rawtypes")
	public List queryForList(String sqlMapConfig) throws SQLException{
		return queryForList(sqlMapConfig, null);
	}
	
	public Object queryForObject(String sqlMapConfig, Object param) throws SQLException{		
		Object object = sqlMapClient.queryForObject(sqlMapConfig, param);
		sqlMapClient.commitTransaction();		
		return object;
	}
	
	public Object queryForObject(String sqlMapConfig) throws SQLException{
		return queryForObject(sqlMapConfig, null);
	}
	
	public int delete(String sqlMapConfig, Object param) throws SQLException{
		int deleteCount = 0;		
		deleteCount = sqlMapClient.delete(sqlMapConfig, param);		
		return deleteCount;		
	}
	
}
