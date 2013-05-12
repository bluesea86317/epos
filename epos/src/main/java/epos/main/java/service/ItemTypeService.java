package epos.main.java.service;

import java.util.List;

import epos.main.java.dao.ItemTypeDao;
import epos.main.java.vo.Item;
import epos.main.java.vo.ItemType;

public class ItemTypeService {
	
	private ItemTypeDao itemTypeDao;
	
	private ItemService itemService;
	
	public void addItemType(ItemType itemType){
		getItemTypeDao().addItemType(itemType);
	}
	
	public void addItemTypes(List<ItemType> itemTypes){
		getItemTypeDao().addItemTypes(itemTypes);
	}
	
	/**
	 * 删除菜品类型, 如果该菜品类型已经指定了某些菜品, 则不允许删除
	 * @param itemTypeId
	 * @throws Exception
	 */
	public void deleteItemType(int itemTypeId) throws Exception{
		List<Item> items = itemService.listItemsByItemType(itemTypeId);
		if(items != null && items.size() > 0){
			throw new Exception("类型名为'"+ getItemTypeDao().getItemType(itemTypeId).getItemType()+"'已经指定了菜品,不允许删除该菜品类型");
		}
		getItemTypeDao().deleteItemType(itemTypeId);
	}
	
	/**
	 * 删除菜品类型, 如果该菜品类型已经指定了某些菜品, 则不允许删除 
	 * @param itemTypeIds
	 * @throws Exception
	 */
	public void deleteItemTypes(List<Integer> itemTypeIds) throws Exception{
		for(int itemTypeId : itemTypeIds){
			List<Item> items = itemService.listItemsByItemType(itemTypeId);
			if(items != null && items.size() > 0){
				throw new Exception("类型名为'"+ getItemTypeDao().getItemType(itemTypeId).getItemType()+"'已经指定了菜品,不允许删除该菜品类型");
			}
		}
		getItemTypeDao().deleteItemTypes(itemTypeIds);
	}
	
	public void updateItemType(ItemType itemType){
		getItemTypeDao().updateItemType(itemType);
	}
	
	public void updateItemTypes(List<ItemType> itemTypes){
		getItemTypeDao().updateItemTypes(itemTypes);
	}
	
	public List<ItemType> listItemTypesByDepartmentId(int departmentId){
		return getItemTypeDao().listItemTypesByDepartmentId(departmentId);
	}
	
	public List<ItemType> listItemTypes(){
		return getItemTypeDao().listItemTypes();
	}

	public ItemType getItemType(int itemTypeId){
		return getItemTypeDao().getItemType(itemTypeId);
	}
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public ItemTypeDao getItemTypeDao() {
		return itemTypeDao;
	}

	public void setItemTypeDao(ItemTypeDao itemTypeDao) {
		this.itemTypeDao = itemTypeDao;
	}

	
}
