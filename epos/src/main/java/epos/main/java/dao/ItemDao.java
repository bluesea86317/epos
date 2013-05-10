package epos.main.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.Item;

public class ItemDao extends SqlMapClientDaoSupport {

	public void addItems(List<Item> items){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "Item.addItem", items);		
	}
	
	public void deleteItems(List<Integer> itemIds){
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "Item.deleteItem", itemIds);
	}
	
	public void updateItems(List<Item> items){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Item.updateItem", items);
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> listItems(){
		return (List<Item>)getSqlMapClientTemplate().queryForList("Item.listItems");
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> listItemsBySearch(Map<String,Object> param){
		return (List<Item>)getSqlMapClientTemplate().queryForList("Item.listItems",param);
	}
}
