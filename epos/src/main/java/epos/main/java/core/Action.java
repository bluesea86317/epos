package epos.main.java.core;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public abstract class Action {
	
//	json返回值中的几个通用参数名
	public final static String RESULT_CODE = "resultCode";
	public final static String MSG = "msg";
	public final static String DATA = "data";
	
//	返回值中msg字段固定的内容
	public final static String ADD_SUCCESS = "新增成功";
	public final static String DELETE_SUCCESS = "删除成功";
	public final static String UPDATE_SUCCESS = "更新成功";
	public final static String QUERY_SUCCESS = "查询成功";
	
	public final static String ADD_FAILURE = "新增失败, 错误信息: ";
	public final static String DELETE_FAILURE = "删除失败, 错误信息: ";
	public final static String UPDATE_FAILURE = "更新失败, 错误信息: ";
	public final static String QUERY_FAILURE = "查询失败, 错误信息: ";
	
	public abstract JSONObject excute(HttpServletRequest request, HttpServletResponse response,JSONObject jsonParam, JSONObject returnObj) throws IOException;	
	
}
