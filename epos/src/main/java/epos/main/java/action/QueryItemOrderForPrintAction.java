package epos.main.java.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.ItemOrderService;
import epos.main.java.vo.ItemOrderVo;

public class QueryItemOrderForPrintAction extends Action {

	private ItemOrderService itemOrderService = Env.getBean("itemOrderService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {			
			List<ItemOrderVo> itemOrderVos = itemOrderService.queryItemOrderVoForPrint();
			itemOrderService.updatePrintingStatus(itemOrderVos);
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(itemOrderVos);
			returnObj.put(DATA, jsonArray);
			returnObj.put(MSG, QUERY_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, QUERY_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			e.printStackTrace();
		}
		return returnObj;
	}

}
