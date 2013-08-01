package epos.main.java.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.BillService;
import epos.main.java.service.ItemOrderService;
import epos.main.java.vo.Bill;
import epos.main.java.vo.ItemOrderVo;

public class QueryItemOrderByTableAction extends Action {

	public static Logger log = Logger.getLogger(QueryItemOrderByTableAction.class);
	private ItemOrderService itemOrderService = Env.getBean("itemOrderService");
	private BillService billService = Env.getBean("billService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			int tableNo = jsonParam.getInt("tableNo");
			boolean listAll = true;
			try {
				listAll = jsonParam.getBoolean("listAll");				
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			Bill bill = billService.queryUnPaidBillByTableNo(tableNo);
			if(bill == null){
				throw new Exception("该桌台还未点菜,或者已经买单");
			}
			List<ItemOrderVo> itemOrderVos = new ArrayList<ItemOrderVo>();
			if(listAll){
				itemOrderVos = itemOrderService.queryItemOrderVoByBillNo(bill.getBillNo());
			}else{
				itemOrderVos = itemOrderService.queryItemOrderVoByBillNoTableNo(bill.getBillNo(), tableNo);
			}
			JSONArray jsonArray = new JSONArray();
			returnObj.put(MSG, QUERY_SUCCESS);
			returnObj.put("totalPrice", bill.getTotalPrice());
			returnObj.put("discountPrice", bill.getDiscountPrice());
			jsonArray.addAll(itemOrderVos);			
			returnObj.put(DATA, jsonArray);
		} catch (Exception e) {
			returnObj.put(MSG, QUERY_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
