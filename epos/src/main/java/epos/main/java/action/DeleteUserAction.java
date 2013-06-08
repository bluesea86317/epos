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
import epos.main.java.core.Return;
import epos.main.java.service.UserService;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class DeleteUserAction extends Action {

	private UserService userService = Env.getBean("userService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Integer> userIds = new ArrayList<Integer>();
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				int userId = jsonObj.getInt("userId");
				userIds.add(userId);
			}
			userService.deleteUsers(userIds);
			returnObj.put(MSG, DELETE_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, DELETE_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			e.printStackTrace();
		}
		return returnObj;
	}

}
