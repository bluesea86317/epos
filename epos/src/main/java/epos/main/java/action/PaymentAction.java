package epos.main.java.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.PaymentService;

/**
 * 付款接口，收银员根据相应这块对桌台进行收款结账
 * @author stephen_chen
 *
 */
@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class PaymentAction extends Action {

	public static Logger log = Logger.getLogger(PaymentAction.class);
	private PaymentService paymentService = Env.getBean("paymentService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			int tableNo = jsonParam.getInt("tableNo");
			
			String billNo = paymentService.paymentForBill(tableNo);
			if(!StringUtils.isBlank(billNo)){			
				returnObj.put(MSG, "结账成功");
			}else{
				returnObj.put(MSG, "顾客未点菜下单,此桌自动转入结账状态");
			}
		} catch (Exception e) {
			returnObj.put(MSG, "结账失败, 错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
