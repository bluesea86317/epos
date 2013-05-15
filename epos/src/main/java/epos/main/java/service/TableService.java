package epos.main.java.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.dao.TableDao;
import epos.main.java.vo.Table;

public class TableService {

	private TableDao tableDao;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void activeTable() throws SQLException{
//		getTableDao().activeService();
	}	
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void nonActiveTable() throws SQLException{
//		getTableDao().nonActiveTable();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void testActiveTble() throws SQLException{
		
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addTables(List<Table> tables) throws Exception{
		for(Table table : tables){
			if(getTableByTableNo(table.getTableNo()) != null){
				throw new Exception("编号为:'" + table.getTableNo() +"'的餐台已经存在, 不能再添加同样编号的餐台");
			}
		}
		tableDao.addTables(tables);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateTables(List<Table> tables){
		tableDao.updateTables(tables);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void deleteTables(List<Integer> tableNOs) throws Exception{
		for(int tableNo : tableNOs){
			if(getTableByTableNo(tableNo) != null && getTableByTableNo(tableNo).getTableStatus() != Table.STATUS_FREE){
				throw new Exception("编号为:'" + tableNo +"'的餐台已经在使用, 不允许删除");
			}
		}
		tableDao.deleteTables(tableNOs);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<Table> listTables(){
		return tableDao.listTables();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<Table> listTablesByStatus(int tableStatus){
		Map<String,Object> param = new HashMap<String,Object>();
		if(tableStatus != -1){
			param.put("tableStatus", tableStatus);
		}
		return tableDao.listTablesByStatus(param);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public Table getTableByTableNo(int tableNo){
		return tableDao.getTableByTableNo(tableNo);
	}
	
	public TableDao getTableDao() {
		return tableDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}
}
