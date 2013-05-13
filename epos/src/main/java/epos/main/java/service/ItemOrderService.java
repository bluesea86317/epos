package epos.main.java.service;


import epos.main.java.dao.ItemOrderDao;

public class ItemOrderService {

	private ItemOrderDao itemOrderDao;
	
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
