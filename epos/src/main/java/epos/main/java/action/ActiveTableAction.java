package epos.main.java.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.service.TableService;

public class ActiveTableAction extends Action {
	
	TableService tableService = Env.getBean("tableService");
	
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response,JSONObject jsonParam, JSONObject returnObj)
			throws IOException {
		try {
			tableService.testActiveTble();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
