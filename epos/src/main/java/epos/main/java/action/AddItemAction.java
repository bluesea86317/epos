package epos.main.java.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.ItemService;
import epos.main.java.vo.Item;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class AddItemAction extends Action {

	public static Logger log = Logger.getLogger(AddItemAction.class);
	
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
				item.setItemName(jsonObj.getString("itemName"));
				item.setItemTypeId(jsonObj.getInt("itemTypeId"));
				item.setPicName(jsonObj.getString("picName"));
				item.setSmallPicName(jsonObj.getString("smallPicName"));
				item.setPrice(new BigDecimal(jsonObj.getString("price")));
				item.setItemReserveStatus(jsonObj.getInt("itemReserveStatus"));
				if(StringUtils.isNotBlank(jsonObj.getString("picName"))){
					item.setImageUrl(IMAGE_PATH + jsonObj.getString("picName"));					
				}
				if(StringUtils.isNotBlank(jsonObj.getString("smallPicName"))){
					item.setSmallImageUrl(IMAGE_PATH + jsonObj.getString("smallPicName"));					
				}
				item.setFlavorIds(jsonObj.getString("flavorChoice"));
				items.add(item);
			}
			itemService.addItems(items);
			returnObj.put(MSG, ADD_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, ADD_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
