package epos.main.java.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.ItemOrderService;

public class SetIfCanOrderAction extends Action {

	public static Logger log = Logger.getLogger(SetIfCanOrderAction.class);
	private ItemOrderService itemOrderService = Env.getBean("itemOrderService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			boolean ifCanOrder = jsonParam.getBoolean("ifCanOrder");
			itemOrderService.setIfCanOrder(ifCanOrder);
			returnObj.put(MSG, "设置成功");
		} catch (Exception e) {
			returnObj.put(MSG, "设置失败, 失败原因:" + e.getMessage());
			returnObj.put(RESULT_CODE,Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
