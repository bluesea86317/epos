package epos.main.java.utils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.http.HttpServletRequest;


public class ParamUtils {
	
	
	public static String getString(HttpServletRequest request, String paramName, String defaultValue){
		String value = request.getParameter(paramName);
		if(StringUtils.isNullOrEmpty(value)){
			return defaultValue;			
		}
		return value;
	}
	
	public static int getInt(HttpServletRequest request, String paramName, int defaultValue){
		int value = 0;
		try {
			value = Integer.parseInt(request.getParameter(paramName));
			return value;
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static String upperCaseMethodName(String fieldName){		
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	
	public static String lowerCaseMethodName(String fieldName){		
		return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
	}
	
	/**
	 * 将配置文件中获取到的参数转换成其他类型的参数，int,boolean,long等
	 * @param value
	 * @param type
	 * @return
	 */
	public static Object convertString(String value, Class<?> type){
			if(String.class.getName().equals(type.getName())){
				return value;
			}
			if(Integer.class.getName().equals(type.getName()) || int.class.getName().equals(type.getName())){
				return Integer.parseInt(value);
			}
			if(Long.class.getName().equals(type.getName()) || long.class.getName().equals(type.getName())){
				return Long.parseLong(value);
			}
			if(Short.class.getName().equals(type.getName()) || short.class.getName().equals(type.getName())){
				return Short.parseShort(value);
			}
			if(Boolean.class.getName().equals(type.getName()) || boolean.class.getName().equals(type.getName())){
				return Boolean.parseBoolean(value);
			}
			if(Byte.class.getName().equals(type.getName()) || byte.class.getName().equals(type.getName())){
				return Byte.parseByte(value);
			}
			if(Float.class.getName().equals(type.getName()) || float.class.getName().equals(type.getName())){
				return Float.parseFloat(value);
			}			
			if(BigDecimal.class.getName().equals(type.getName())){
				return new BigDecimal(value);
			}
			return value;
	}
	
	
	public static Object getResultByMethodParam(String paramClassName, ResultSet rs, String columnName){
		try {
			if(String.class.getName().equals(paramClassName)){
				return rs.getString(columnName);
			}else if((int.class.getName().equals(paramClassName))){
				return rs.getInt(columnName);
			}else{
				return rs.getObject(columnName);
			}		
		} catch (SQLException e) {			
//			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
}
