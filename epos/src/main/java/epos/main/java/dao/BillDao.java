package epos.main.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import epos.main.java.vo.Bill;

public class BillDao extends SqlMapClientDaoSupport {
	
	public void addBill(Bill bill){
		getSqlMapClientTemplate().insert("Bill.addBill", bill);
	}
	
	public void deleteBill(Map<String, Object> param){		
		getSqlMapClientTemplate().delete("Bill.deleteBill", param);
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
	
}
