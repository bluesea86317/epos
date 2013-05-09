package epos.main.java.action;

import java.io.IOException;
import java.util.ArrayList;
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

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=true)
public class UpdateFlavorAction extends Action {

	private FlavorService flavorService = Env.getBean("flavorService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			JSONArray jsonArray = jsonParam.getJSONArray(DATA);
			List<Flavor> flavors = new ArrayList<Flavor>();
			for(Object obj : jsonArray){
				JSONObject jsonObj =  JSONObject.fromObject(obj);
				int flavorId = jsonObj.getInt("flavorId");
				String flavorType = jsonObj.getString("flavorType");
				Flavor flavor = new Flavor();
				flavor.setFlavorType(flavorType);
				flavor.setFlavorId(flavorId);
				flavors.add(flavor);
			}
			flavorService.updateFlavors(flavors);
			returnObj.put(MSG, ADD_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, ADD_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
		}
		return returnObj;
	}

}
