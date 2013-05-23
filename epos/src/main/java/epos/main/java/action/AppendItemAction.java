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
import epos.main.java.service.ItemOrderService;
import epos.main.java.service.OrderService;
import epos.main.java.service.UserService;
import epos.main.java.vo.ItemOrder;

public class AppendItemAction extends Action {

	private OrderService orderService = Env.getBean("orderService");
	private ItemOrderService itemOrderService = Env.getBean("itemOrderService");
	private UserService userSerivce = Env.getBean("userService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			try {
				if(!itemOrderService.queryIfCanOrder()){
					String userName = jsonParam.getString("userName");
					String password = jsonParam.getString("password");
					if(!userSerivce.validateUserNameAndPassword(userName, password)){
						throw new Exception("用户名或者密码错误");
					}
				}
			} catch (Exception e) {
				throw new Exception("用户名或者密码错误");
			}
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
			orderService.orderItem(itemOrders,jsonParam.getInt("tableNo"));
			returnObj.put(MSG, "加菜成功");
		} catch (Exception e) {
			returnObj.put(MSG, "加菜失败, 错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			e.printStackTrace();
		}
		return returnObj;
	}

}
