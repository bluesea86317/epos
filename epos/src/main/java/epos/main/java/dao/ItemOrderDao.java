package epos.main.java.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.ItemOrder;
import epos.main.java.vo.ItemOrderVo;

public class ItemOrderDao extends SqlMapClientDaoSupport {
	
	private final static String IFCANORDERKEY = "ifCanOrder";
	
	/**
	 * 点菜
	 * @param itemOrders
	 */
	public void addItemOrders(List<ItemOrder> itemOrders){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "ItemOrder.addItemOrder", itemOrders);
	}
	
	public void addItemOrderForPrint(List<ItemOrder> itemOrders){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "ItemOrder.addItemOrderForPrint", itemOrders);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemOrderVo> queryItemOrderVoForPrint(){
		return (List<ItemOrderVo>)getSqlMapClientTemplate().queryForList("ItemOrder.queryItemOrderVoForPrint");
	}
	
	public ItemOrder queryItemOrderByItemIdBillNoTableNo(Map<String,Object> param){
		return (ItemOrder)getSqlMapClientTemplate().queryForObject("ItemOrder.queryItemOrder",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemOrderVo> queryItemOrderVo(Map<String,Object> param){
		return (List<ItemOrderVo>)getSqlMapClientTemplate().queryForList("ItemOrder.queryItemOrderVo",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemOrder> queryItemOrder(Map<String,Object> param){
		return (List<ItemOrder>)getSqlMapClientTemplate().queryForList("ItemOrder.queryItemOrder",param);
	}
	
	public void updatePrintingStatus(List<ItemOrderVo> itemOrderVos){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "ItemOrder.updatePrintingStatus", itemOrderVos);
	}
	
	
	public void updatePaymentStatus(String billNo){
		getSqlMapClientTemplate().update("ItemOrder.updatePaymentStatus", billNo);
	}
	
	public void updateProvidingStatus(int itemOrderId){
		getSqlMapClientTemplate().update("ItemOrder.updateProvidingStatus", itemOrderId);
	}
	
	public void updateBillNo(Map<String,Object> param){
		getSqlMapClientTemplate().update("ItemOrder.updateBillNo", param);
	}
	
	public void updateTableNo(int toTableNo, String billNo, int fromTable) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("toTableNo", toTableNo);
		param.put("billNo", billNo);
		param.put("fromTable", fromTable);
		getSqlMapClientTemplate().update("ItemOrder.updateTableNo",param);		
	}
	
	public void deleteItemOrderByItemIdAndBillNo(List<ItemOrder> itemOrders){
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "ItemOrder.deleteItemOrderByItemIdAndBillNo", itemOrders);
	}
	
	public void updateItemOrderPriceAndCount(List<ItemOrder> itemOrders){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "ItemOrder.updateItemOrderPriceAndCount", itemOrders);
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
