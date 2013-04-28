package epos.main.java.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import epos.main.java.core.Return;

@SuppressWarnings("serial")
public class NonActionForRequstException extends Exception {
	
	public NonActionForRequstException(String message){
		super(message);
	}
	
	public void outPrint(HttpServletResponse response){
		try {
			response.getWriter().print(new Return(Return.PROCESS_RESULT_FAILURE,super.getMessage()).toJason());
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
