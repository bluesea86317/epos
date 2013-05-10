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

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class DeleteItemTypeAction extends Action{

	private ItemTypeService itemTypeService = Env.getBean("itemTypeService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			List<Integer> itemTypeIds = new ArrayList<Integer>();
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				int itemTypeId = jsonObj.getInt("itemTypeId");
				itemTypeIds.add(itemTypeId);
			}
			itemTypeService.deleteItemTypes(itemTypeIds);
			returnObj.put(MSG, DELETE_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, DELETE_FAILURE +e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
		}
		return returnObj;
	}	
}
