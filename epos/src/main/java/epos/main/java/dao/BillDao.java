package epos.main.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.utils.DBUtils;
import epos.main.java.vo.Bill;

public class BillDao extends SqlMapClientDaoSupport {
	
	public void addBill(Bill bill){
		getSqlMapClientTemplate().insert("Bill.addBill", bill);
	}
	
	public void deleteBillByBillNo(String billNo){		
		getSqlMapClientTemplate().delete("Bill.deleteBillByBillNo", billNo);
	}
	
	public void updateTotalPrice(Map<String, Object> param){
		getSqlMapClientTemplate().update("Bill.updateTotalPrice", param);
	}
	
	public void updateTableNo(Map<String, Object> param){
		getSqlMapClientTemplate().update("Bill.updateTableNo", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> queryUnPaidBillByTableNo(int tableNo){
		return (List<Bill>)getSqlMapClientTemplate().queryForList("Bill.queryUnPaidBillByTableNo", tableNo);
	}

	public void deleteBillByBillNos(List<String> billNos) {
		DBUtils.excuteBatchDelete(getSqlMapClientTemplate(), "Bill.deleteBillByBillNo", billNos);		
	}
	
	public void payForBill(String billNo){
		getSqlMapClientTemplate().update("Bill.payForBill", billNo);
	}

	public Bill getBillByNo(String billNo) {		
		return (Bill)getSqlMapClientTemplate().queryForObject("Bill.getBillByNo", billNo);
	}
	
}
