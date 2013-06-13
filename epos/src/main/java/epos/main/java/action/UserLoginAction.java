package epos.main.java.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.UserService;
import epos.main.java.vo.User;

public class UserLoginAction extends Action {

	public static Logger log = Logger.getLogger(UserLoginAction.class);
	private UserService userService = Env.getBean("userService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			
			String userName = jsonParam.getString("userName");
			String password = jsonParam.getString("password");
			User user = userService.getUserByNameAndPassword(userName, password);
			if(user != null){
				returnObj.put(MSG, "登录成功");
				returnObj.put("isAdmin",user.isAdmin());
			}else{
				throw new Exception("用户名或者密码错误");
			}
		} catch (Exception e) {
			returnObj.put(MSG, "登录失败,错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
