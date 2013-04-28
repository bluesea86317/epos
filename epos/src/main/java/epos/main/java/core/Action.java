package epos.main.java.core;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {
	
	public abstract String excute(HttpServletRequest request, HttpServletResponse response) throws IOException;	
	
	
}
