package epos.main.java.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.FlavorService;
import epos.main.java.vo.Flavor;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=false)
public class ListFlavorAction extends Action{

	private FlavorService flavorService = Env.getBean("flavorService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			List<Flavor> flavors = flavorService.listFlavors();
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(flavors);
			returnObj.put(DATA, jsonArray.toString());
			returnObj.put(MSG, QUERY_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, QUERY_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
		}
		return returnObj;
	}

}
