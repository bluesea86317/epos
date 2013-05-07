package epos.main.java.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.ActionForward;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.TableService;

public class ActiveTableAction extends Action {
	
	TableService tableService = Env.getBean("tableService");
	
	@Override
	public String excute(HttpServletRequest request,
			HttpServletResponse response, Return returnObj)
			throws IOException {
		try {
			tableService.testActiveTble();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
