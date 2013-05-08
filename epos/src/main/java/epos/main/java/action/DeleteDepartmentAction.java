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
import epos.main.java.service.DepartmentService;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=true)
public class DeleteDepartmentAction extends Action {

	private DepartmentService departmentService = Env.getBean("departmentService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			JSONArray jsonArray = jsonParam.getJSONArray("data");
			List<Integer> departmentIds = new ArrayList<Integer>();
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				departmentIds.add(jsonObj.getInt("departmentId"));
			}
			departmentService.deleteDepartments(departmentIds);
			returnObj.put("msg", "删除成功！");
		} catch (Exception e) {
			returnObj.put("resultCode", Return.PROCESS_RESULT_FAILURE);
			returnObj.put("msg", "删除失败，错误原因：" + e.getMessage());
		}
		return returnObj;
	}

}
