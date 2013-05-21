package epos.main.java.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.TableService;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class ChangeTableAction extends Action {

	TableService tableService = Env.getBean("tableService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			int fromTableNo = jsonParam.getInt("fromTableNo");
			int toTableNo = jsonParam.getInt("toTableNo");
			tableService.changeTble(fromTableNo, toTableNo);
			returnObj.put(MSG, "转台成功");
		} catch (Exception e) {
			returnObj.put(MSG, "转台失败，错误信息： " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			e.printStackTrace();
		}
		
		return returnObj;
	}

}
