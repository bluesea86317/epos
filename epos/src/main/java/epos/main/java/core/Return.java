package epos.main.java.core;

import net.sf.json.JSONObject;

public class Return {
	
	public final static int PROCESS_RESULT_SUCCESS = 1;
	
	public final static int PROCESS_RESULT_FAILURE = 0;
	
	private int resultCode;
	
	private String msg;

	public Return(int resultCode, String msg) {
		this.resultCode = resultCode;
		this.msg = msg;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String toJason(){
		JSONObject jsonObject = JSONObject.fromObject( this ); 
		return jsonObject.toString();
	}
}
