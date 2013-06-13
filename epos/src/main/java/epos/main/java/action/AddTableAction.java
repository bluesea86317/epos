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
import epos.main.java.service.TableService;
import epos.main.java.vo.Table;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class AddTableAction extends Action{

	public static Logger log = Logger.getLogger(AddTableAction.class);
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
			tableService.addTables(tables);
			returnObj.put(MSG, ADD_SUCCESS);
		} catch (Exception e) {
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			returnObj.put(MSG, ADD_FAILURE + e.getMessage());
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
