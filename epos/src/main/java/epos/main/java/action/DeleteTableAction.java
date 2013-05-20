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
import epos.main.java.service.TableService;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class DeleteTableAction extends Action {

	private TableService tableService = Env.getBean("tableService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {		
		try {
			List<Integer> tableNOs = new ArrayList<Integer>();
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				int tableNo = jsonObj.getInt("tableNo");
				tableNOs.add(tableNo);
			}
			tableService.deleteTables(tableNOs);
			returnObj.put(MSG, DELETE_SUCCESS);
		} catch (Exception e) {
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			returnObj.put(MSG, DELETE_FAILURE + e.getMessage());
			e.printStackTrace();
		}
		return returnObj;	
	}
}
