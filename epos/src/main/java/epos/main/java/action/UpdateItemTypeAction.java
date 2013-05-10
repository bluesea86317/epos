package epos.main.java.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.ItemTypeService;
import epos.main.java.vo.ItemType;

public class UpdateItemTypeAction extends Action {

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
				int itemTypeId = jsonObj.getInt("itemTypeId");
				String itemType = jsonObj.getString("itemType");
				int departmentId = jsonObj.getInt("departmentId");
				ItemType type = new ItemType();
				type.setItemTypeId(itemTypeId);
				type.setItemType(itemType);
				type.setDepartmentId(departmentId);
				itemTypes.add(type);
			}
			itemTypeService.updateItemTypes(itemTypes);
			returnObj.put(MSG, UPDATE_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, UPDATE_FAILURE + e.getMessage());
			returnObj.put("resultCode", Return.PROCESS_RESULT_FAILURE);
		}
		return returnObj;
	}

}
