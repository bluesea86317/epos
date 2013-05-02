package epos.main.java.service;

import java.sql.SQLException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.annotation.MethodTransactionConfig;
import epos.main.java.dao.TableDao;

public class TableService {

	private TableDao tableDao = TableDao.getInstance();
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void activeTable() throws SQLException{
		getTableDao().activeService();
	}	
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void nonActiveTable() throws SQLException{
		getTableDao().nonActiveTable();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void testActiveTble() throws SQLException{
		getTableDao().activeService();
		getTableDao().nonActiveTable();
	}

	public TableDao getTableDao() {
		return tableDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}
}
