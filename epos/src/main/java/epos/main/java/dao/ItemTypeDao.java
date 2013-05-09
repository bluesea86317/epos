package epos.main.java.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.ItemType;

public class ItemTypeDao extends SqlMapClientDaoSupport {

	public void addItemType(ItemType itemType){
		getSqlMapClientTemplate().insert("ItemType.addItemType", itemType);
	}
	
	public void addItemTypes(List<ItemType> itemTypes){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "ItemType.addItemType", itemTypes);
	}
	
	public void deleteItemType(int itemTypeId){
		getSqlMapClientTemplate().delete("ItemType.deleteItemType", itemTypeId);
	}
	
	public void deleteItemTypes(List<Integer> itemTypeIds){
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "ItemType.deleteItemType", itemTypeIds);
	}
	
	public void updateItemType(ItemType itemType){
		getSqlMapClientTemplate().update("ItemType.updateItemType", itemType);
	}
	
	public void updateItemTypes(List<ItemType> itemTypes){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "ItemType.updateItemType", itemTypes);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemType> listItemTypes(){
		return (List<ItemType>)getSqlMapClientTemplate().queryForList("ItemType.listItemTypes");
	}
}
