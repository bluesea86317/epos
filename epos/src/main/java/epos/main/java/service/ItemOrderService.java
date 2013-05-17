package epos.main.java.service;


import java.util.List;
import java.util.Map;

import epos.main.java.dao.ItemOrderDao;
import epos.main.java.vo.ItemOrder;

public class ItemOrderService {

	private ItemOrderDao itemOrderDao;	
	
	public void addItemOrders(List<ItemOrder> itemOrders){
		itemOrderDao.addItemOrders(itemOrders);
	}	

	public List<ItemOrder> queryItemOrderForPrint(){
		return itemOrderDao.queryItemOrderForPrint();
	}
	
	public List<ItemOrder> queryItemOrder(Map<String,Object> param){
		return itemOrderDao.queryItemOrder(param);
	}
	
	public void updatePrintingStatus(List<Integer> itemOrderIds){
		itemOrderDao.updatePrintingStatus(itemOrderIds);
	}	
	
	public void updatePaymentStatus(Map<String,Object> param){
		itemOrderDao.updatePaymentStatus(param);
	}
	
	public void updateProvidingStatus(Map<String,Object> param){
		itemOrderDao.updateProvidingStatus(param);
	}
	
	public void updateBillNo(Map<String,Object> param){
		itemOrderDao.updateBillNo(param);
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
}
