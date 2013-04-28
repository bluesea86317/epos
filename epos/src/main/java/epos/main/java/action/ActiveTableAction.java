package epos.main.java.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import epos.main.java.core.Action;
import epos.main.java.core.ActionForward;
import epos.main.java.service.TableService;
import epos.main.java.transaction.Env;

public class ActiveTableAction extends Action {
	
	TableService tableService = Env.getBean("tableService");
	
	@Override
	public String excute(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
		try {
			tableService.activeTable();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
