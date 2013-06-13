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
import epos.main.java.service.ItemService;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class DeleteItemAction extends Action {

	public static Logger log = Logger.getLogger(DeleteItemAction.class);
	private ItemService itemService = Env.getBean("itemService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Integer> itemIds = new ArrayList<Integer>();
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				itemIds.add(jsonObj.getInt("itemId"));
			}
			itemService.deleteItems(itemIds);
			returnObj.put(MSG, DELETE_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, DELETE_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
