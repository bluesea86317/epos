package epos.main.java.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.BillService;
import epos.main.java.service.EposConfigService;
import epos.main.java.service.ItemOrderService;
import epos.main.java.service.PaymentService;
import epos.main.java.vo.Bill;
import epos.main.java.vo.ItemOrderVo;

/**
 * 买单接口，顾客提出买单，服务员对桌台进行买单状态的修改
 * @author stephen_chen
 *
 */
@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class CheckBillAction extends Action {

	public static Logger log = Logger.getLogger(CheckBillAction.class);
	private PaymentService paymentService = Env.getBean("paymentService");
	private ItemOrderService itemOrderService = Env.getBean("itemOrderService");
	private BillService billService = Env.getBean("billService");
	private EposConfigService eposConfigService = Env.getBean("eposConfigService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			int tableNo = jsonParam.getInt("tableNo");
			BigDecimal discountRate = new BigDecimal(jsonParam.getString("discountRate"));
			String billNo = paymentService.checkBill(tableNo,discountRate);
			Bill bill = billService.getBillByNo(billNo);
			JSONArray jsonArray = new JSONArray();			
			List<ItemOrderVo> itemOrders = itemOrderService.queryItemOrderVoByBillNo(billNo);
//			遍历餐单列表，以桌台为单位，把所有菜单进行分组
			Map<Integer, List<ItemOrderVo>> tableItemOrderMap = new HashMap<Integer,List<ItemOrderVo>>();
			Map<Integer, Integer> customerNumMap = new HashMap<Integer,Integer>();
			Map<Integer, BigDecimal> tableTotalPriceMap = new HashMap<Integer,BigDecimal>();
			for(ItemOrderVo itemOrderVo : itemOrders){
				if(null != tableItemOrderMap.get(itemOrderVo.getTableNo())){
					tableItemOrderMap.get(itemOrderVo.getTableNo()).add(itemOrderVo);
					tableTotalPriceMap.put(itemOrderVo.getTableNo(),tableTotalPriceMap.get(itemOrderVo.getTableNo()).add(itemOrderVo.getPrice()));
				}else{
					List<ItemOrderVo> tableItemOrders = new ArrayList<ItemOrderVo>();
					tableItemOrders.add(itemOrderVo);
					tableItemOrderMap.put(itemOrderVo.getTableNo(), tableItemOrders);
					
					tableTotalPriceMap.put(itemOrderVo.getTableNo(), itemOrderVo.getPrice());
				}
//				从当前餐台的茶位费中提取该桌台的总人数
				if(itemOrderVo.getItemId() == Integer.parseInt(eposConfigService.getProperty("defaultItemIds"))){
					customerNumMap.put(itemOrderVo.getTableNo(), itemOrderVo.getItemCount());
				}
			}
			
//			在遍历桌台菜品组成的map，讲菜品列表，顾客数，总价格设置到returnObj
			Iterator<Integer> itr = tableItemOrderMap.keySet().iterator();
			while(itr.hasNext()){
				JSONObject tableItemOrderJsonObject = new JSONObject();
				JSONArray tableItemOrderJsonArray = new JSONArray();
				int tn = itr.next();
				tableItemOrderJsonArray.addAll(tableItemOrderMap.get(tn));
				tableItemOrderJsonObject.put("itemOrders", tableItemOrderJsonArray);
				tableItemOrderJsonObject.put("customerNum", customerNumMap.get(tn));
				tableItemOrderJsonObject.put("tableTotalPrice", tableTotalPriceMap.get(tn));
				tableItemOrderJsonObject.put("tableNo", tn);
				jsonArray.add(tableItemOrderJsonObject);
			}				
			returnObj.put(DATA, jsonArray);
			returnObj.put("billNo", billNo);
			returnObj.put("totalPrice", bill.getTotalPrice());
			returnObj.put("discountPrice", bill.getDiscountPrice());
			returnObj.put("totalCustomerNum", bill.getCustomerNum());
			returnObj.put(MSG, "操作成功");
		} catch (Exception e) {
			returnObj.put(MSG, "操作失败, 错误信息: " + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}
}
