package epos.main.java.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import epos.main.java.core.Return;

@SuppressWarnings("serial")
public class UnauthorizedException extends Exception {

	public UnauthorizedException(String msg) {
		super(msg);
	}
	
	public void outPrint(HttpServletResponse response){
		try {
			response.getWriter().print(new Return(Return.PROCESS_RESULT_FAILURE,super.getMessage()).toJsonString());
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
