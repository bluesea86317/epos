package epos.main.java.transaction;

import java.lang.reflect.Method;
import java.sql.SQLException;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 动态代理类,为JDBCDaoSupport中的sql操作增加事务管理
 * @author wen_chen
 *
 */
public class TransactionStartInvocationHandler implements MethodInterceptor {

	private SqlMapClient sqlMapClient;
	
	private Object target;
	
	public TransactionStartInvocationHandler(Object target, SqlMapClient sqlMapClient){
		this.target = target;
		this.sqlMapClient = sqlMapClient;
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object object = new Object();
		try{
			
			sqlMapClient.startTransaction();
			object = method.invoke(this.target, args);
			sqlMapClient.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			sqlMapClient.endTransaction();
		}
		return object;
	}
	
	public Object getInstance(Object target){
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}

}
