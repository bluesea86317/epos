package epos.main.java.core;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Env {
	public static Logger log = Logger.getLogger(Env.class);
	
	public final static String CONFIG_FILE = "spring-context.xml";
	
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
				log.info("spring context loaded. " + timespan + " millis");
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
