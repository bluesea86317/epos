package epos.main.java.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.dao.ItemOrderDao;
import epos.main.java.utils.DBUtils;
import epos.main.java.vo.ItemOrder;

public class ItemOrderService {

	private ItemOrderDao itemOrderDao;	
	
	private BillService billService;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addItemOrders(List<ItemOrder> itemOrders){
		itemOrderDao.addItemOrders(itemOrders);
	}	

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addItemOrderForPrint(List<ItemOrder> itemOrders){
		itemOrderDao.addItemOrderForPrint(itemOrders);
	}
	
	public List<ItemOrder> queryItemOrderForPrint(){
		return itemOrderDao.queryItemOrderForPrint();
	}
	
	public ItemOrder queryItemOrderByItemIdBillNoTableNo(int itemId, String billNo, int tableNo){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("itemId", itemId);
		param.put("billNo", billNo);
		param.put("tableNo", tableNo);
		return itemOrderDao.queryItemOrderByItemIdBillNoTableNo(param);
	}
	
	public List<ItemOrder> queryItemOrderByBillNoTableNo(String billNo, int tableNo){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("billNo", billNo);
		param.put("tableNo", tableNo);
		return itemOrderDao.queryItemOrder(param);
	}
	
	public List<ItemOrder> queryItemOrder(Map<String,Object> param){
		return itemOrderDao.queryItemOrder(param);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updatePrintingStatus(List<Integer> itemOrderIds){
		itemOrderDao.updatePrintingStatus(itemOrderIds);
	}	
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updatePaymentStatus(Map<String,Object> param){
		itemOrderDao.updatePaymentStatus(param);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateProvidingStatus(Map<String,Object> param){
		itemOrderDao.updateProvidingStatus(param);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateBillNo(int tableNo, String billNo, String newBillNo){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("billNo", billNo);
		param.put("tableNo", tableNo);
		param.put("newBillNo", newBillNo);
		itemOrderDao.updateBillNo(param);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateTableNo(int tableNo, String billNo){
		itemOrderDao.updateTableNo(tableNo, billNo);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void deleteItemOrderByItemIdAndBillNo(List<ItemOrder> itemOrders){
		itemOrderDao.deleteItemOrderByItemIdAndBillNo(itemOrders);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateItemOrderPriceAndCount(List<ItemOrder> itemOrders){
		itemOrderDao.updateItemOrderPriceAndCount(itemOrders);
	}
	
	
	
	public void setIfCanOrder(boolean ifCanOrder){
		String value = ifCanOrder ? "1" : "0";
		getItemOrderDao().setIfCanOrder(value);
	}
	
	public boolean queryIfCanOrder(){
		String value = getItemOrderDao().queryIfCanOrder();
		return ("1").equals(value) ? true : false;
	}

	public ItemOrderDao getItemOrderDao() {
		return itemOrderDao;
	}

	public void setItemOrderDao(ItemOrderDao itemOrderDao) {
		this.itemOrderDao = itemOrderDao;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}
}
