package epos.main.java.core;

import java.util.HashMap;
import java.util.Map;

public class ActionForward {
	
	private Map<String,String> forwardMap = new HashMap<String,String>();
	
	public String findForward(String name){
		return forwardMap.get(name);
	}
	
	public void putForwardMap(String name, String path){
		forwardMap.put(name, path);
	}	
}
