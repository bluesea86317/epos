
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
import epos.main.java.service.DepartmentService;
import epos.main.java.vo.Department;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class ListDepartmentAction extends Action {

	private DepartmentService departmentService = Env.getBean("departmentService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Department> departments = departmentService.listDepartment();
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(departments);
			returnObj.put(DATA, jsonArray.toString());
			returnObj.put(MSG, QUERY_SUCCESS);
		} catch (Exception e) {
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);		
			returnObj.put(MSG, QUERY_FAILURE + e.getMessage());
		}
		return returnObj;
	}

}
