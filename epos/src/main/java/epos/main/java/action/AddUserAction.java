package epos.main.java.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.service.UserService;
import epos.main.java.vo.User;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class AddUserAction extends Action {

	private UserService userService = Env.getBean("userService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			List<User> users = new ArrayList<User>();
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				String userName = jsonObj.getString("userName");
				String password = jsonObj.getString("password");
				String realName = jsonObj.getString("realName");
				String mobile = jsonObj.getString("mobile");
				boolean isAdmin = jsonObj.getBoolean("isAdmin");
				User user = new User();
				user.setUserName(userName);
				user.setPassword(password);
				user.setRealName(realName);
				user.setMobile(mobile);
				user.setAdmin(isAdmin);
				users.add(user);
			}
			userService.addUsers(users);
			returnObj.put(MSG, ADD_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, ADD_SUCCESS);
			returnObj.put(RESULT_CODE, ADD_FAILURE + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
