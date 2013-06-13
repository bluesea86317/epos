package epos.main.java.action;

import java.io.IOException;
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

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=true)
public class ListUserAction extends Action {

	public static Logger log = Logger.getLogger(ListUserAction.class);
	private UserService userService = Env.getBean("userService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response,JSONObject jsonParam, JSONObject returnObj) throws IOException {
		try {
			List<User> userList = userService.listUser();
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(userList);		
			returnObj.put(DATA, jsonArray.toString());
			returnObj.put(MSG,QUERY_SUCCESS);			
		} catch (Exception e) {
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			returnObj.put(MSG,QUERY_FAILURE + e.getMessage());
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
