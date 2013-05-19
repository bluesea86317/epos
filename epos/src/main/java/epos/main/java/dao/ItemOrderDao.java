package epos.main.java.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.ItemOrder;

public class ItemOrderDao extends SqlMapClientDaoSupport {
	
	private final static String IFCANORDERKEY = "ifCanOrder";
	
	/**
	 * 点菜
	 * @param itemOrders
	 */
	public void addItemOrders(List<ItemOrder> itemOrders){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "ItemOrder.addItemOrder", itemOrders);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemOrder> queryItemOrderForPrint(){
		return (List<ItemOrder>)getSqlMapClientTemplate().queryForList("ItemOrder.queryItemOrderForPrint");
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemOrder> queryItemOrder(Map<String,Object> param){
		return (List<ItemOrder>)getSqlMapClientTemplate().queryForList("ItemOrder.queryItemOrder",param);
	}
	
	public void updatePrintingStatus(List<Integer> itemOrderIds){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "ItemOrder.updatePrintingStatus", itemOrderIds);
	}
	
	
	public void updatePaymentStatus(Map<String,Object> param){
		getSqlMapClientTemplate().update("ItemOrder.updatePaymentStatus", param);
	}
	
	public void updateProvidingStatus(Map<String,Object> param){
		getSqlMapClientTemplate().update("ItemOrder.updateProvidingStatus", param);
	}
	
	public void updateBillNo(Map<String,Object> param){
		getSqlMapClientTemplate().update("ItemOrder.updateBillNo", param);
	}
	
	public void updateTableNo(int tableNo, String billNo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("tableNo", tableNo);
		param.put("billNo", billNo);
		getSqlMapClientTemplate().update("ItemOrder.updateTableNo",param);		
	}
	
	
	
	
	public void setIfCanOrder(String value){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("key", IFCANORDERKEY);
		param.put("value", value);
		if(StringUtils.isNotBlank(queryIfCanOrder())){
			updateIfCanOrder(IFCANORDERKEY, value);
		}else{
			getSqlMapClientTemplate().insert("ItemOrder.addSysParam",param);
		}
	}
	
	public String queryIfCanOrder(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("key", IFCANORDERKEY);
		return (String)getSqlMapClientTemplate().queryForObject("ItemOrder.querySysParam",param);		
	}
	
	public void updateIfCanOrder(String key, String value){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("key", key);
		param.put("value", value);
		getSqlMapClientTemplate().update("ItemOrder.updateSysParam",param);
	}

}
