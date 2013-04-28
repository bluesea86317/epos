package epos.main.java.service;

import java.sql.SQLException;

import epos.main.java.annotation.MethodTransactionConfig;
import epos.main.java.annotation.ServiceBeanInfo;
import epos.main.java.dao.TableDao;

@ServiceBeanInfo(typaAlias = "tableService")
public class TableService {

	private TableDao tableDao = TableDao.getInstance();
	
	@MethodTransactionConfig(needControl = true)
	public void activeTable() throws SQLException{
		tableDao.activeService();
	}	
	
}
