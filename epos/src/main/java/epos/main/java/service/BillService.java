package epos.main.java.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.dao.BillDao;
import epos.main.java.vo.Bill;

public class BillService {

	private BillDao billDao;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addBill(Bill bill){
		billDao.addBill(bill);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void deleteBillByBillNo(String billNo){
		billDao.deleteBillByBillNo(billNo);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void deleteBillByBillNos(List<String> billNos){		
		billDao.deleteBillByBillNos(billNos);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateTotalPrice(BigDecimal totalPrice, String billNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("totalPrice", totalPrice);
		param.put("billNo", billNo);
		billDao.updateTotalPrice(param);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateCustomerNum(int customerNum, String billNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("customerNum", customerNum);
		param.put("billNo", billNo);
		billDao.updateCustomerNum(param);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateTableNo(int toTableNo, String billNo, int fromTableNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("toTableNo", toTableNo);
		param.put("billNo", billNo);
		param.put("fromTableNo", fromTableNo);
		billDao.updateTableNo(param);
	}
	
	public Bill queryUnPaidBillByTableNo(int tableNo) throws Exception{
		List<Bill> bills = billDao.queryUnPaidBillByTableNo(tableNo);
		if(bills != null && bills.size() > 0){
			if(bills.size() > 1){
				throw new Exception("数据异常，" + tableNo + "号桌台存在两笔以上的菜单未结账");
			}else{
				return bills.get(0);
			}
		}else{
			return null;
		}
		
	}

	public void changeDiscountPrice(String billNo,BigDecimal discountPrice){
		billDao.changeDiscountPrice(billNo, discountPrice);
	}
	
	public void payForBill(String billNo){
		billDao.payForBill(billNo);
	}
	
	public Bill getBillByNo(String billNo){
		return billDao.getBillByNo(billNo);
	}
	
	/**
	 * 创建流水单号, 年月日+桌台号+时分秒
	 * @param tableNo
	 * @return
	 */
	public String createBillNo(int tableNo){
		String billNo = createBillNoByTime(tableNo, new Date());
		if(getBillByNo(billNo) != null){
			billNo = createBillNoByTime(tableNo, DateUtils.addSeconds(new Date(), 10));
		}
		return billNo;
	}
	
	public static String createBillNoByTime(int tableNo, Date date){
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = sdf.format(date);
		sb.append(dateStr.substring(0, 8));
		String tableNoStr = String.valueOf(tableNo);
		if(tableNo < 10){
			tableNoStr = "00"+tableNoStr;
		}else if(tableNo < 100){
			tableNoStr = "0"+tableNoStr;
		}
		sb.append(tableNoStr);
		sb.append(dateStr.substring(8, 14));
		return sb.toString();
	}
	
	public BillDao getBillDao() {
		return billDao;
	}

	public void setBillDao(BillDao billDao) {
		this.billDao = billDao;
	}
	
}
