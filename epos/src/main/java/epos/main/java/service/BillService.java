package epos.main.java.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import epos.main.java.dao.BillDao;
import epos.main.java.vo.Bill;

public class BillService {

	private BillDao billDao;
	
	public void addBill(Bill bill){
		billDao.addBill(bill);
	}
	
	
	public void deleteBillByBillNo(String billNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("billNo", billNo);
		billDao.deleteBill(param);
	}
	
	public void updateTotalPrice(BigDecimal totalPrice, String billNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("totalPrice", totalPrice);
		param.put("billNo", billNo);
		billDao.updateTotalPrice(param);
	}
	
	public void updateTableNo(int tableNo, String billNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableNo", tableNo);
		param.put("billNo", billNo);
		billDao.updateTableNo(param);
	}
	
	public Bill queryUnPaidBillByTableNo(int tableNo) throws Exception{
		List<Bill> bills = billDao.queryUnPaidBillByTableNo(tableNo);
		if(bills != null){
			if(bills.size() > 1){
				throw new Exception("该餐台还有没结账的菜单");
			}else{
				return bills.get(0);
			}
		}else{
			return null;
		}
		
	}

	public BillDao getBillDao() {
		return billDao;
	}

	public void setBillDao(BillDao billDao) {
		this.billDao = billDao;
	}
}
