package epos.main.java.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.StatisticsService;
import epos.main.java.vo.ItemSumCountVo;

@ActionAuthFilterConfig(needAuthorize=true, mustBeAdmin=true)
public class QueryItemSumCountAction extends Action {

	public static Logger log = Logger.getLogger(QueryItemSumCountAction.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
	private StatisticsService statisticsService = Env.getBean("statisticsService");
	@Override
	public JSONObject excute(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonParam,
			JSONObject returnObj) throws IOException {
		try {
			String beginDateStr = jsonParam.getString("beginDate");
			String endDateStr = jsonParam.getString("endDate");
			Date beginDate = sdf.parse(beginDateStr);
			Date endDate = DateUtils.addDays(sdf.parse(endDateStr), 1);
			List<ItemSumCountVo> itemSumCountVos = statisticsService.queryItemSumCountByTime(beginDate, endDate);
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(itemSumCountVos);
			returnObj.put(DATA, jsonArray.toString());
			returnObj.put(MSG, QUERY_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, QUERY_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			log.error(e.getMessage());
		}
		return returnObj;
	}

}
