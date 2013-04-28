package epos.main.java.transaction;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.transaction.TransactionConfig;
import com.ibatis.sqlmap.engine.transaction.TransactionManager;
import com.ibatis.sqlmap.engine.transaction.jdbc.JdbcTransactionConfig;

import epos.main.java.core.DataSourceFactory;
import epos.main.java.exception.DataSourceInitException;

public class Env {
	
	public static Map<String,String> serviceBeanMap = new LinkedHashMap<String, String>();
	
	public static ThreadLocal<SqlMapClient> sqlMapClientLocal = new ThreadLocal<SqlMapClient>();
//	动态代理类, 对service层的对象进行代理, 为service层的方法做AOP事务管理
	private static TransactionHandlerProxy transactionHandlerProxy;
	
	private static Reader reader;
	
//	初始化ibatis的sqlMapClient, 并存放在ThreadLocal中
	private static void init(){
		try {
			if(sqlMapClientLocal.get() == null){
				reader = Resources
				.getResourceAsReader("sqlMapConfig.xml");
				sqlMapClientLocal.set(SqlMapClientBuilder.buildSqlMapClient(reader));
				TransactionConfig config = new JdbcTransactionConfig();
				config.setDataSource(DataSourceFactory.getDataSource());
				SqlMapClientImpl sqlMapClientImpl = (SqlMapClientImpl)sqlMapClientLocal.get();
				sqlMapClientImpl.getDelegate().setTxManager(new TransactionManager(config));
				reader.close();
			}
		} catch (DataSourceInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过配置中存放的bean, 对service层采用动态代理机制进行初始化, 便于对service层中的方法做AOP拦截, 来进行事务管理 
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		Object bean;
		try {
			if(sqlMapClientLocal.get() == null){
				init();
			}
			bean = Class.forName(beanName).newInstance();
			transactionHandlerProxy = new TransactionHandlerProxy(bean, sqlMapClientLocal.get());
			return (T)transactionHandlerProxy.getInstance(bean);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void initServiceBeanMap() throws IOException{
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> resouces = cld.getResources("epos/main/java/service/");
		
		while(resouces.hasMoreElements()){
			URL res = resouces.nextElement();
			System.out.println(res.toString());
		}
	}
}
