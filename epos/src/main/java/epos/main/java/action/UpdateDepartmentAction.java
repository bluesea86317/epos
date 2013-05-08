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
import epos.main.java.vo.Department;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=true)
public class UpdateDepartmentAction extends Action {

	private DepartmentService departmentService = Env.getBean("departmentService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try{
			JSONArray jsonArray = jsonParam.getJSONArray("data");
			List<Department> departments = new ArrayList<Department>();
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				int departmentId = jsonObj.getInt("departmentId");
				String departmentName = jsonObj.getString("departmentName");
				String printerInfo = jsonObj.getString("printerInfo");
				Department department = new Department();
				department.setDepartmentId(departmentId);
				department.setDepartmentName(departmentName);
				department.setPrinterInfo(printerInfo);
				departments.add(department);
			}
			departmentService.updateDepartments(departments);
			returnObj.put("msg", "修改部门成功");
		}catch(Exception e){
			returnObj.put("msg", "修改部门失败，错误原因：" + e.getMessage());
			returnObj.put("resultCode", Return.PROCESS_RESULT_FAILURE);
		}		
		return returnObj;
	}

}
