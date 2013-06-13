package epos.main.java.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.ItemService;
import epos.main.java.vo.Item;

public class ListItemAction extends Action {

	public static Logger log = Logger.getLogger(ListItemAction.class);
	private ItemService itemService = Env.getBean("itemService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Item> items = new ArrayList<Item>();
			JSONArray jsonArray = new JSONArray();
			int itemTypeId = jsonParam.getInt("itemTypeId");
//			通过菜品类型查询菜品, 如果输入的菜品类型为0, 则展示所有菜品
			if(0 == itemTypeId){
				items = itemService.listAllItems();
			}else{
				items = itemService.listItemsByItemType(itemTypeId);
			}
			jsonArray.addAll(items);
			returnObj.put(DATA, jsonArray.toString());
			returnObj.put(MSG, QUERY_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, QUERY_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE,Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
