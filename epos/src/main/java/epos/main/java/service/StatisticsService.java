package epos.main.java.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import epos.main.java.core.Env;
import epos.main.java.dao.StatisticsDao;
import epos.main.java.vo.ItemSumCountVo;

public class StatisticsService {
	
	private StatisticsDao statisticsDao;

	private EposConfigService eposConfigService = Env.getBean("eposConfigService");
	
	public StatisticsDao getStatisticsDao() {
		return statisticsDao;
	}

	public void setStatisticsDao(StatisticsDao statisticsDao) {
		this.statisticsDao = statisticsDao;
	}
	
	/**
	 * 查询一段时间内所点菜品的总数
	 * @param excludeItemId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<ItemSumCountVo> queryItemSumCountByTime(Date beginDate, Date endDate){
		int excludeItemId = 0;
		String defaultItemIds = eposConfigService.getProperty("defaultItemIds");
		if(StringUtils.isNotBlank(defaultItemIds)){
			String[] itemIds = defaultItemIds.split(",");
			excludeItemId = Integer.parseInt(itemIds[0]);
		}
		return statisticsDao.queryItemSumCountByTime(excludeItemId,beginDate,endDate);
	}
	
	/**
	 * 查询一段时间内有消费的桌台总数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public int querySumConsumedTable(Date beginDate, Date endDate){
		return statisticsDao.querySumConsumedTable(beginDate,endDate);
	}
	
	/**
	 * 查询一段时间内的营业总额
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public BigDecimal querySumDiscountedPrice(Date beginDate, Date endDate){
		return statisticsDao.querySumDiscountedPrice(beginDate,endDate);
	}

}
