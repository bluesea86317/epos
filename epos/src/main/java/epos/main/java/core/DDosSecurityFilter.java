package epos.main.java.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DDosSecurityFilter implements Filter {

	private long resist_period;
	
	public void destroy() {
		DDosSecurity.clear();
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String ip = request.getRemoteAddr();
//		如果ip拦截的filter里面有当前请求的ip,且还未到阻挡的时间, 就自动跳转到首页, 否则在ip请求次数的统计中加1.
		if(DDosSecurity.query(ip) != null && Math.abs(System.currentTimeMillis() - DDosSecurity.query(ip)) < resist_period){
			response.getWriter().print("发现异常请求, 暂时拒绝提供任何服务!");
		}else{
//			每请求一次都记录一次
			DDosSecurity.record(ip);
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {		
		this.resist_period = Long.parseLong(config.getInitParameter("resist_period"));
	}
	
}
