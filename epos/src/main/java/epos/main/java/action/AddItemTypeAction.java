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
import epos.main.java.service.ItemTypeService;
import epos.main.java.vo.ItemType;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class AddItemTypeAction extends Action {

	private ItemTypeService itemTypeService = Env.getBean("itemTypeService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			List<ItemType> itemTypes = new ArrayList<ItemType>();
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				String itemType = jsonObj.getString("itemType");
				int departmentId = jsonObj.getInt("departmentId");
				ItemType type = new ItemType();
				type.setItemType(itemType);
				type.setDepartmentId(departmentId);
				itemTypes.add(type);
			}
			itemTypeService.addItemTypes(itemTypes);
			returnObj.put(MSG, ADD_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, ADD_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
		}
		return returnObj;
	}

}
