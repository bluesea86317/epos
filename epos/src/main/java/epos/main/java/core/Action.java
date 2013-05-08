package epos.main.java.core;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public abstract class Action {
	
	public abstract JSONObject excute(HttpServletRequest request, HttpServletResponse response,JSONObject jsonParam, JSONObject returnObj) throws IOException;	
	
	
}
