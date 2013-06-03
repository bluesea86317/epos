package epos.main.java.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;

import epos.main.java.core.Action;
import epos.main.java.core.Env;
import epos.main.java.core.Return;
import epos.main.java.service.StatisticsService;

public class QuerySumDiscountedPriceAction extends Action {

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
			BigDecimal totalMoney = statisticsService.querySumDiscountedPrice(beginDate, endDate);			
			returnObj.put("totalMoney", totalMoney);
			returnObj.put(MSG, QUERY_SUCCESS);
		} catch (Exception e) {
			returnObj.put(MSG, QUERY_FAILURE + e.getMessage());
			returnObj.put(RESULT_CODE, Return.PROCESS_RESULT_FAILURE);
			e.printStackTrace();
		}
		return returnObj;
	}
}
