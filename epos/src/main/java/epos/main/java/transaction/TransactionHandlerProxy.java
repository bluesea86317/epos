package epos.main.java.transaction;

import java.lang.reflect.Method;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.ibatis.sqlmap.client.SqlMapClient;

import epos.main.java.annotation.MethodTransactionConfig;

public class TransactionHandlerProxy implements MethodInterceptor {
	
	private Object target;
	
	private SqlMapClient sqlMapClient;
	
	public TransactionHandlerProxy(Object target,SqlMapClient sqlMapClient){		
			this.target = target;
			this.sqlMapClient = sqlMapClient;
	}
	
//	AOP注入核心, 为service层方法做事务控制
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy arg3) throws Throwable {
		Object object = new Object();
//		通过方法上加的注解来决定该方法适合要做事务控制
		MethodTransactionConfig annotation = method.getAnnotation(MethodTransactionConfig.class);
		if(annotation == null || !annotation.needControl()){
			object = method.invoke(this.target, args);
		}else{	
			try {
				sqlMapClient.startTransaction();			
				object = method.invoke(this.target, args);
				sqlMapClient.commitTransaction();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				sqlMapClient.endTransaction();
			}		
		}
		return object;
	}

//	public static SqlMapClient getSqlMapClient() {
//		return sqlMapClient;
//	}
//
//	public static void setSqlMapClient(SqlMapClient sqlMapClient) {
//		TransactionHandlerProxy.sqlMapClient = sqlMapClient;
//	}

	public Object getInstance(Object target){
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}
}
