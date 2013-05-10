package epos.main.java.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.Flavor;

public class FlavorDao extends SqlMapClientDaoSupport {

	public void addFlavor(Flavor flavor){
		getSqlMapClientTemplate().insert("Flavor.addFlavor", flavor);
	}
	
	public void addFlavors(List<Flavor> flavors){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "Flavor.addFlavor", flavors);
	}
	
	public void deleteFlavor(int flavorId){
		getSqlMapClientTemplate().delete("Flavor.deleteFlavor", flavorId);
	}
	
	public void deleteFlavors(List<Integer> flavorIds){
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "Flavor.deleteFlavor", flavorIds);
	}
	
	public void updateFlavor(Flavor flavor){
		getSqlMapClientTemplate().update("Flavor.updateFlavor", flavor);
	}
	
	public void updateFlavors(List<Flavor> flavors){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Flavor.updateFlavor", flavors);
	}
	
	@SuppressWarnings("unchecked")
	public List<Flavor> listFlavors(){
		return (List<Flavor>)getSqlMapClientTemplate().queryForList("Flavor.listFlavors");
	}
	
	@SuppressWarnings("unchecked")
	public List<Flavor> listFlavorsByIds(String flavorIds){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("flavorIds", flavorIds);
		return (List<Flavor>)getSqlMapClientTemplate().queryForList("Flavor.listFlavors", param);
	}
}
