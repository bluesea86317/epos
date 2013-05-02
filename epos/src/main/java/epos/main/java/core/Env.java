package epos.main.java.core;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.transaction.TransactionConfig;
import com.ibatis.sqlmap.engine.transaction.TransactionManager;
import com.ibatis.sqlmap.engine.transaction.jdbc.JdbcTransactionConfig;

import epos.main.java.exception.DataSourceInitException;

public class Env {
	
	public final static String CONFIG_FILE = "spring-context.xml";
	
//	public static Map<String,String> serviceBeanMap = new LinkedHashMap<String, String>();
//	
//	public static ThreadLocal<SqlMapClient> sqlMapClientLocal = new ThreadLocal<SqlMapClient>();
////	动态代理类, 对service层的对象进行代理, 为service层的方法做AOP事务管理
//	private static TransactionHandlerProxy transactionHandlerProxy;
//	
//	private static Reader reader;
//	
////	初始化ibatis的sqlMapClient, 并存放在ThreadLocal中
//	private static void init(){
//		try {
//			if(sqlMapClientLocal.get() == null){
//				reader = Resources
//				.getResourceAsReader("ibatis-sqlMap-Config.xml");
//				sqlMapClientLocal.set(SqlMapClientBuilder.buildSqlMapClient(reader));
//				TransactionConfig config = new JdbcTransactionConfig();
//				config.setDataSource(DataSourceFactory.getDataSource());
//				config.setForceCommit(false);
//				
//				SqlMapClientImpl sqlMapClientImpl = (SqlMapClientImpl)sqlMapClientLocal.get();
//				sqlMapClientImpl.getDelegate().setTxManager(new TransactionManager(config));
//				
//				reader.close();
//			}
//		} catch (DataSourceInitException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 通过配置中存放的bean, 对service层采用动态代理机制进行初始化, 便于对service层中的方法做AOP拦截, 来进行事务管理 
//	 * @param beanName
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static <T> T getBean(String beanName){
//		Object bean;
//		try {
////			if(sqlMapClientLocal.get() == null){
//				init();
////			}
//			String beanType = serviceBeanMap.get(beanName);
//			bean = Class.forName(beanType).newInstance();
//			transactionHandlerProxy = new TransactionHandlerProxy(bean, sqlMapClientLocal.get());
//			return (T)transactionHandlerProxy.getInstance(bean);
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/**
	 * 用作LazyLoad，替代double check
	 */
	private static final class InternalResource implements Serializable {

		private static final long serialVersionUID = 1L;

		public static class Spring implements Serializable {
			private static final long serialVersionUID = 1L;

			public static final ClassPathXmlApplicationContext context;

			/**
			 * 初始化SpringContext
			 */
			static {
				long start = System.currentTimeMillis();
				context = new ClassPathXmlApplicationContext(CONFIG_FILE);
				long timespan = System.currentTimeMillis() - start;

//				if (LOG.isInfoEnabled()) LOG.info("spring context loaded. " + timespan + " millis");
			}
		}
	}
	
	/**
	 * 获取SpringBean
	 * 
	 * @param name
	 * @return
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public final static <T> T getBean(String name) throws BeansException {
		return (T) InternalResource.Spring.context.getBean(name);
	}
	
	
}
