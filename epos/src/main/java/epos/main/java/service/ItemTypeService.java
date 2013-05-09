package epos.main.java.service;

import java.util.List;

import epos.main.java.dao.ItemTypeDao;
import epos.main.java.utils.DBUtils;
import epos.main.java.vo.ItemType;

public class ItemTypeService {
	
	private ItemTypeDao itemTypeDao;
	
	public void addItemType(ItemType itemType){
		itemTypeDao.addItemType(itemType);
	}
	
	public void addItemTypes(List<ItemType> itemTypes){
		itemTypeDao.addItemTypes(itemTypes);
	}
	
	public void deleteItemType(int itemTypeId){
		itemTypeDao.deleteItemType(itemTypeId);
	}
	
	public void deleteItemTypes(List<Integer> itemTypeIds){
		itemTypeDao.deleteItemTypes(itemTypeIds);
	}
	
	public void updateItemType(ItemType itemType){
		itemTypeDao.updateItemType(itemType);
	}
	
	public void updateItemTypes(List<ItemType> itemTypes){
		itemTypeDao.updateItemTypes(itemTypes);
	}
	
	public List<ItemType> listItemTypes(){
		return itemTypeDao.listItemTypes();
	}
}
