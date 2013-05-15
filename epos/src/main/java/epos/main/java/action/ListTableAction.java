package epos.main.java.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.TableService;
import epos.main.java.vo.Table;

public class ListTableAction extends Action {

	private TableService tableService = Env.getBean("tableService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {			
			int tableStatus =  jsonParam.getInt("tableStatus");			
			List<Table> tables = tableService.listTablesByStatus(tableStatus);
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(tables);
			returnObj.put(DATA, jsonArray.toString());
			returnObj.put(MSG, QUERY_SUCCESS);
		} catch (Exception e) {
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			returnObj.put(MSG, QUERY_FAILURE + e.getMessage());
			e.printStackTrace();
		}
		return returnObj;
	}

}
