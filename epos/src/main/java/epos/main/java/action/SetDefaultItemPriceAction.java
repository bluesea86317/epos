package epos.main.java.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.EposConfigService;
import epos.main.java.service.ItemService;
import epos.main.java.vo.Item;

@ActionAuthFilterConfig(mustBeAdmin=true, needAuthorize=true)
public class SetDefaultItemPriceAction extends Action {

	public static Logger log = Logger.getLogger(SetDefaultItemPriceAction.class);
	private EposConfigService eposConfigService = Env.getBean("eposConfigService");
	private ItemService itemService = Env.getBean("itemService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Item> items = new ArrayList<Item>();
			Item item = itemService.getItemById(Integer.parseInt(eposConfigService.getProperty("defaultItemIds")));
			if(null != item){
				item.setPrice(new BigDecimal(jsonParam.getString("price")));
				items.add(item);				
			}
			itemService.updateItems(items);
			returnObj.put(MSG, UPDATE_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, UPDATE_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}
}
