package epos.main.java.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.vo.ItemSumCountVo;

public class StatisticsDao extends SqlMapClientDaoSupport {

	@SuppressWarnings("unchecked")
	public List<ItemSumCountVo> queryItemSumCountByTime(int excludeItemId, Date beginDate, Date endDate){
		Map<String, Object> param = new HashMap<String,Object>();		
		param.put("itemId", excludeItemId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);				
		return (List<ItemSumCountVo>)getSqlMapClientTemplate().queryForList("Statistics.queryItemSumCountByTime", param);
	}
	
	public int querySumConsumedTable(Date beginDate, Date endDate){
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		return (Integer)getSqlMapClientTemplate().queryForObject("Statistics.querySumConsumedTable",param);
	}
	
	public BigDecimal querySumDiscountedPrice(Date beginDate, Date endDate){
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		return (BigDecimal)getSqlMapClientTemplate().queryForObject("Statistics.querySumDiscountedPrice",param);
	}
}
