package epos.main.java.utils;

public final class StringUtils {

	public static boolean isNullOrEmpty(String value){
		if(null == value || "".equals(value)) return true;
		return false;
	}

}
