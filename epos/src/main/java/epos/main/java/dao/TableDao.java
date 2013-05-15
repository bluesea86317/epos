package epos.main.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.Table;


public class TableDao extends SqlMapClientDaoSupport {
	
	public void addTables(List<Table> tables){
		DBUtils.excuteBatchInsert(getSqlMapClientTemplate(), "Table.addTable", tables);
	}
	
	public void deleteTables(List<Integer> tableNOs){
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "Table.deleteTable", tableNOs);
	}
	
	public void updateTables(List<Table> tables){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Table.addTable", tables);
	}
	
	@SuppressWarnings("unchecked")
	public List<Table> listTables(){
		return (List<Table>)getSqlMapClientTemplate().queryForList("Table.listTables");
	}
	
	@SuppressWarnings("unchecked")
	public List<Table> listTablesByStatus(Map<String,Object> param){
		return (List<Table>)getSqlMapClientTemplate().queryForList("Table.listTables", param);
	}
	
	public Table getTableByTableNo(int tableNo){
		return (Table)getSqlMapClientTemplate().queryForObject("Table.getTableByTableNo", tableNo);
	}
}
