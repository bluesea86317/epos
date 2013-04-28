package epos.main.java.core;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DDosSecurity implements Runnable{
	
	/**
	 * 用于统计某一ip单位时间内的发起的请求次数
	 */
	@SuppressWarnings("serial")
	public static Map<String, Integer> ip_count = Collections.synchronizedMap(new LinkedHashMap<String, Integer>(){
		public Integer put(String key, Integer value) {
			if(this.get(key) != null){
				value = this.get(key) + value;
			}
			super.put(key, value);
			return value;
		}
	});
	
	/**
	 * 用于存放发起恶意请求的ip地址和存放时间点, servlet拦截器会按照当前记录的时间点对该ip做一段时间的拒绝访问的拦截
	 */
	public static Map<String, Long> ip_filter = Collections.synchronizedMap(new LinkedHashMap<String, Long>());
	
	/**
	 * 请求上限阈值, 单位时间内超过90次就认为是恶意攻击
	 */
	private static int max_request_count = 90;
	
	/**
	 * 判断非法请求的时间间隔, 暂定30秒, 如果30秒内请求超过限制的阈值就认为是非法的恶意请求
	 */
	private static long per_time = 30000;
	
	static{
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new DDosSecurity(), per_time, per_time,  TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 记录请求的ip
	 * @param ip
	 */
	public static void record(String ip){
		ip_count.put(ip, 1);
	}
	
	/**
	 * 查询请求的ip是否超过阈值
	 * @param ip
	 * @return
	 */
	public static Long query(String ip){
		return ip_filter.get(ip);
	}
	
	public static void clear(){
		ip_count.clear();
		ip_filter.clear();
	}
	
	public void run() {
		while(!ip_count.isEmpty()){
			Iterator<String> itr = ip_count.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				int count = ip_count.get(key);				
				if(count > max_request_count){
					ip_filter.put(key,System.currentTimeMillis());
					System.out.println("有异常请求的主机, ip是: " + key + ", 30秒内请求次数达到: " + count + "次");
				}
				ip_count.remove(key);
			}
		}
	}
}
