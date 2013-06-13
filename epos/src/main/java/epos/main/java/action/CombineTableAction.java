package epos.main.java.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.TableService;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class CombineTableAction extends Action {

	public static Logger log = Logger.getLogger(CombineTableAction.class);
	private TableService tableService = Env.getBean("tableService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Integer> combinedTableNos = new ArrayList<Integer>();
			String[] tableNos = jsonParam.getString("combinedTableNos").split(",");
			for(String tableNo : tableNos){
				combinedTableNos.add(Integer.parseInt(tableNo));
			}
			int newTableNo = jsonParam.getInt("newTableNo");
			tableService.combineTable(combinedTableNos, newTableNo);
			returnObj.put(MSG, "并台成功");
		} catch (Exception e) {
			returnObj.put(MSG, "并台失败, 错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
