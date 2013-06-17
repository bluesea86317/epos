package epos.main.java.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.UserService;
import epos.main.java.vo.User;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class AddUserAction extends Action {

	public static Logger log = Logger.getLogger(AddUserAction.class);
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
				boolean adminFlag = jsonObj.getBoolean("adminFlag");
				User user = new User();
				user.setUserName(userName);
				user.setPassword(password);
				user.setRealName(realName);
				user.setMobile(mobile);
				user.setAdminFlag(adminFlag);
				users.add(user);
			}
			userService.addUsers(users);
			returnObj.put(MSG, ADD_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, ADD_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
