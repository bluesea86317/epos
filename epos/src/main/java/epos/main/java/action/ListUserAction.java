package epos.main.java.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.UserService;
import epos.main.java.vo.User;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=true)
public class ListUserAction extends Action {

	private UserService userService = Env.getBean("userService");
	
	@Override
	public String excute(HttpServletRequest request,
			HttpServletResponse response, Return returnObj) throws IOException {
		List<User> userList = userService.listUser();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(userList);
		JSONObject returnJsonObj = returnObj.toJsonObj();
		returnJsonObj.put("data", jsonArray.toString());
		return returnJsonObj.toString();
	}

}
