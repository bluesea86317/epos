package epos.main.java.action;

import java.io.IOException;
import java.math.BigDecimal;
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
import epos.main.java.service.ItemService;
import epos.main.java.vo.Item;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class UpdateItemAction extends Action{

	private final static String IMAGE_PATH = "/images/item/";
	private ItemService itemService = Env.getBean("itemService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Item> items = new ArrayList<Item>();
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				Item item = new Item();
				item.setItemId(jsonObj.getInt("itemId"));
				item.setItemName(jsonObj.getString("itemName"));
				item.setItemTypeId(jsonObj.getInt("itemTypeId"));
				item.setPicName(jsonObj.getString("picName"));
				item.setPrice(new BigDecimal(jsonObj.getString("price")));
				item.setImageUrl(IMAGE_PATH + jsonObj.getString("picName"));
				item.setFlavorIds(jsonObj.getString("flavorChoice"));
				items.add(item);
			}
			itemService.updateItems(items);
			returnObj.put(MSG, UPDATE_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, UPDATE_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
		}
		return returnObj;
	}

}
