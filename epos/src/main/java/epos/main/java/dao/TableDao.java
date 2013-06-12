package epos.main.java.dao;

import java.util.HashMap;
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
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Table.updateTable", tables);
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
	
	public void changeTableStatus(int tableNo, int tableStatus){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("tableNo", tableNo);
		param.put("tableStatus", tableStatus);
		getSqlMapClientTemplate().update("Table.changeTableStatus", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<Table> getTableByBillNo(String billNo){
		return (List<Table>)getSqlMapClientTemplate().queryForList("Table.getTableByBillNo", billNo);
	}
	
	public void changeTableStatusToPaid(List<Table> tables){
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Table.changeTableStatusToPaid", tables);
	}	

	public void freeTables(List<Integer> tableNos) {
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Table.changeTableStatusToFree", tableNos);		
	}

	public void changeTableStatusToChecked(List<Table> tables) {
		DBUtils.excuteBatchUpdate(getSqlMapClientTemplate(), "Table.changeTableStatusToChecked", tables);
	}
}
