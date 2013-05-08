package epos.main.java.utils;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class DBUtils {

	@SuppressWarnings("unchecked")
	public static void excuteBatchInsert(SqlMapClientTemplate sqlMapClientTemplate,  final String statementName, final List list){
		if(list != null){
			sqlMapClientTemplate.execute(new SqlMapClientCallback() {
	            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	               executor.startBatch();
	               for (int i = 0, n = list.size(); i < n; i++) {
	                   executor.insert(statementName, list.get(i));
	               }
	               executor.executeBatch();
	               return null;
	            }
	        });
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void excuteBatchDelete(SqlMapClientTemplate sqlMapClientTemplate,  final String statementName, final List list){
		if(list != null){
			sqlMapClientTemplate.execute(new SqlMapClientCallback() {
	            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	               executor.startBatch();
	               for (int i = 0, n = list.size(); i < n; i++) {
	                   executor.delete(statementName, list.get(i));
	               }
	               executor.executeBatch();
	               return null;
	            }
	        });
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void excuteBatchUpdate(SqlMapClientTemplate sqlMapClientTemplate,  final String statementName, final List list){
		if(list != null){
			sqlMapClientTemplate.execute(new SqlMapClientCallback() {
	            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	               executor.startBatch();
	               for (int i = 0, n = list.size(); i < n; i++) {
	                   executor.delete(statementName, list.get(i));
	               }
	               executor.executeBatch();
	               return null;
	            }
	        });
		}
	}
}
