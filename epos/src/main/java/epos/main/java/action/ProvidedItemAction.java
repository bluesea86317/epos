package epos.main.java.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.OrderService;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class ProvidedItemAction extends Action {

	public static Logger log = Logger.getLogger(ProvidedItemAction.class);
	private OrderService orderService = Env.getBean("orderService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			int tableNo = jsonParam.getInt("tableNo");
			int itemId = jsonParam.getInt("itemId");			
			orderService.provideItem(tableNo, itemId);
			returnObj.put(MSG, "上菜成功");
		} catch (Exception e) {
			returnObj.put(MSG, "上菜失败, 错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
