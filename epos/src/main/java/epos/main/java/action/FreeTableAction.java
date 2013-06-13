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

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class FreeTableAction extends Action {

	public static Logger log = Logger.getLogger(FreeTableAction.class);
	private TableService tableService = Env.getBean("tableService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Integer> tableNos = new ArrayList<Integer>();
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			for(Object object : jsonArray){
				JSONObject jsonObject = JSONObject.fromObject(object);
				tableNos.add(jsonObject.getInt("tableNo"));
			}
			tableService.freaaTables(tableNos);
			returnObj.put(MSG, "清台成功");
		} catch (Exception e) {
			returnObj.put(MSG, "清台失败, 错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
