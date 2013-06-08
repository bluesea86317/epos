package epos.main.java.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.UserService;

public class UserLoginAction extends Action {

	private UserService userService = Env.getBean("userService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			
			String userName = jsonParam.getString("userName");
			String password = jsonParam.getString("password");
			if(userService.validateUserNameAndPassword(userName, password)){
				returnObj.put(MSG, "登录成功");
			}else{
				throw new Exception("用户名或者密码错误");
			}
		} catch (Exception e) {
			returnObj.put(MSG, "登录失败,错误信息" + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			e.printStackTrace();
		}
		return returnObj;
	}

}
