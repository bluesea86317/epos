package epos.main.java.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.service.DepartmentService;
import epos.main.java.utils.ParamUtils;
import epos.main.java.vo.Department;

public class AddDepartmentAction extends Action {

	private DepartmentService departmentService = Env.getBean("departmentService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response,JSONObject jsonParam, JSONObject returnObj)
			throws IOException {
		JSONArray jsonArray = jsonParam.getJSONArray("data");
		for(Object obj : jsonArray){
			JSONObject jsonObj =  JSONObject.fromObject(obj);
			String departmentName = jsonObj.getString("departmentName");
			String printerInfo = jsonObj.getString("printerInfo");
			Department department = new Department();
			department.setDepartmentName(departmentName);
			department.setPrinterInfo(printerInfo);
			departmentService.addDepartment(department);
		}
		returnObj.put("msg", "新增部门成功");
		return returnObj;
	}

}
