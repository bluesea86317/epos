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
import epos.main.java.service.OrderService;
import epos.main.java.vo.ItemOrder;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class CancelItemAction extends Action {

	public static Logger log = Logger.getLogger(CancelItemAction.class);
	private OrderService orderService = Env.getBean("orderService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<ItemOrder> itemOrders = new ArrayList<ItemOrder>();
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				ItemOrder itemOrder = new ItemOrder();
				itemOrder.setTableNo(jsonParam.getInt("tableNo"));
				itemOrder.setItemId(jsonObj.getInt("itemId"));
				itemOrder.setItemCount(jsonObj.getInt("itemCount"));
				itemOrder.setFlavorId(jsonObj.getInt("flavorId"));
				itemOrders.add(itemOrder);
			}
			orderService.cancelItem(itemOrders,jsonParam.getInt("tableNo"));
			returnObj.put(MSG, "退菜成功");
		} catch (Exception e) {
			returnObj.put(MSG, "退菜失败, 错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
