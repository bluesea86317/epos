package epos.main.java.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.core.Env;
import epos.main.java.dao.FlavorDao;
import epos.main.java.dao.ItemDao;
import epos.main.java.vo.Flavor;
import epos.main.java.vo.Item;

public class ItemService {

	private ItemDao itemDao;
	private FlavorDao flavorDao;
	private EposConfigService eposConfigService = Env.getBean("eposConfigService");
	
	public void addItems(List<Item> items){
		getItemDao().addItems(items);
	}
	
	public void deleteItems(List<Integer> itemIds){
		getItemDao().deleteItems(itemIds);
	}
	
	public void updateItems(List<Item> items){
		getItemDao().updateItems(items);
	}
	
	public List<Item> listAllItems(){
		Map<String,Object> param = new HashMap<String,Object>();
		int excludeItemId = Integer.parseInt(eposConfigService.getProperty("defaultItemIds"));
		param.put("excludeItemId", excludeItemId);
		List<Item> items = getItemDao().listItems(param);
//		设置菜品的口味类型
		setFlavorsToItem(items);
		return items;
	}
	
	/**
	 * 通过分类查找菜品
	 * @param itemTypeId
	 * @return
	 */
	public List<Item> listItemsByItemType(int itemTypeId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("itemTypeId", itemTypeId);
		int excludeItemId = Integer.parseInt(eposConfigService.getProperty("defaultItemIds"));
		param.put("excludeItemId", excludeItemId);
		List<Item> items = getItemDao().listItemsBySearch(param);
//		设置菜品的口味类型
		setFlavorsToItem(items);
		return items;
	}
	
	/**
	 * 通过名称模糊搜索菜品
	 * @param itemName
	 * @return
	 */
	public List<Item> listItemsByItemName(String itemName){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("itemName", "%"+itemName+"%");
		int excludeItemId = Integer.parseInt(eposConfigService.getProperty("defaultItemIds"));
		param.put("excludeItemId", excludeItemId);
		List<Item> items = getItemDao().listItemsBySearch(param);
//		设置菜品的口味类型
		setFlavorsToItem(items);
		return items;
	}

	/**
	 * 设置菜品口味对象
	 * @param items
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	private void setFlavorsToItem(List<Item> items){
		for(Item item : items){
			if(StringUtils.isNotBlank(item.getFlavorIds())){
				String[] itemIdstr = item.getFlavorIds().split(",");
				Integer[] itemIds = new Integer[itemIdstr.length];
				for(int i = 0; i < itemIdstr.length; i++){
					itemIds[i] = Integer.parseInt(itemIdstr[i]);
				}
				List<Flavor> flavors = flavorDao.listFlavorsByIds(itemIds);
				item.setFlavors(flavors);
			}
		}
	}
	
	/**
	 * 更新菜品库存状态
	 * @param items
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void updateItemReserveStatus(List<Item> items) {
		itemDao.updateItemReserveStatus(items);
	}
	
	public Item getItemById(int itemId){
		return itemDao.getItemById(itemId);
	}
	
	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public FlavorDao getFlavorDao() {
		return flavorDao;
	}

	public void setFlavorDao(FlavorDao flavorDao) {
		this.flavorDao = flavorDao;
	}

	
	
}
