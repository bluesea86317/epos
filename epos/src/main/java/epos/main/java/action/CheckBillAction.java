package epos.main.java.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.BillService;
import epos.main.java.service.ItemOrderService;
import epos.main.java.service.PaymentService;
import epos.main.java.vo.ItemOrderVo;

/**
 * 买单接口，顾客提出买单，服务员对桌台进行买单状态的修改
 * @author stephen_chen
 *
 */
@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class CheckBillAction extends Action {

	private PaymentService paymentService = Env.getBean("paymentService");
	private ItemOrderService itemOrderService = Env.getBean("itemOrderService");
	private BillService billService = Env.getBean("billService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			int tableNo = jsonParam.getInt("tableNo");
			BigDecimal discountRate = new BigDecimal(jsonParam.getString("discountRate"));
			String billNo = paymentService.checkBill(tableNo,discountRate);
			JSONArray jsonArray = new JSONArray();			
			List<ItemOrderVo> itemOrders = itemOrderService.queryItemOrderVoByBillNo(billNo);			
			jsonArray.addAll(itemOrders);				
			returnObj.put(DATA, jsonArray);
			returnObj.put("billNo", billNo);
			returnObj.put("totalPrice", billService.getBillByNo(billNo).getTotalPrice());
			returnObj.put("discountPrice", billService.getBillByNo(billNo).getDiscountPrice());
			returnObj.put(MSG, "操作成功");
		} catch (Exception e) {
			returnObj.put(MSG, "操作失败, 错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			e.printStackTrace();
		}
		return returnObj;
	}

}
