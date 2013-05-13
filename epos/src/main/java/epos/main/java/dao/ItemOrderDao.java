package epos.main.java.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ItemOrderDao extends SqlMapClientDaoSupport {
	
	private final static String IFCANORDERKEY = "ifCanOrder";
	
	public void setIfCanOrder(String value){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("key", IFCANORDERKEY);
		param.put("value", value);
		if(StringUtils.isNotBlank(queryIfCanOrder())){
			updateIfCanOrder(IFCANORDERKEY, value);
		}else{
			getSqlMapClientTemplate().insert("ItemOrder.addSysParam",param);
		}
	}
	
	public String queryIfCanOrder(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("key", IFCANORDERKEY);
		return (String)getSqlMapClientTemplate().queryForObject("ItemOrder.querySysParam",param);		
	}
	
	public void updateIfCanOrder(String key, String value){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("key", key);
		param.put("value", value);
		getSqlMapClientTemplate().update("ItemOrder.updateSysParam",param);
	}
}
