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
import epos.main.java.vo.Table;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class UpdateTableAction extends Action {
	
	private TableService tableService = Env.getBean("tableService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Table> tables = new ArrayList<Table>();
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				Table table = new Table();
				table.setTableNo(jsonObj.getInt("tableNo"));
				table.setTableName(jsonObj.getString("tableName"));
				table.setSeatingNum(jsonObj.getInt("seatingNum"));
				tables.add(table);
			}
			tableService.updateTables(tables);
			returnObj.put(MSG, UPDATE_SUCCESS);
		} catch (Exception e) {
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			returnObj.put(MSG, UPDATE_FAILURE + e.getMessage());
			e.printStackTrace();
		}
		return returnObj;
	}

}
