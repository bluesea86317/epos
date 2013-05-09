package epos.main.java.dao;

import java.util.List;

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
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "Flavor.deleteFlavors", flavorIds);
	}
	
	public void updateFlavor(Flavor flavor){
		getSqlMapClientTemplate().update("Flavor.updateFlavor", flavor);
	}
	
	public void updateFlavors(List<Flavor> flavors){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Flavor.updateFlavors", flavors);
	}
	
	@SuppressWarnings("unchecked")
	public List<Flavor> listFlavors(){
		return (List<Flavor>)getSqlMapClientTemplate().queryForList("Flavor.listFlavors");
	}
}
