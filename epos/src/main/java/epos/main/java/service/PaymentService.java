package epos.main.java.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.vo.Bill;
import epos.main.java.vo.Table;

public class PaymentService {

	private TableService tableService;
	
	private ItemOrderService itemOrderService;
	
	private BillService billService;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public String paymentForBill(int tableNo) throws Exception{
		Table table = tableService.getTableByTableNo(tableNo);
		if(table == null){
			throw new Exception("编号为"+tableNo+"的桌台不存在");
		}
		if(table.getTableStatus() != Table.STATUS_ACTIVED){
			throw new Exception("该桌台还未开台或者已经买单");
		}
		Bill bill = billService.queryUnPaidBillByTableNo(tableNo);
		if(bill == null){
			throw new Exception("该桌台已经买单");
		}
		List<Table> tables = tableService.getTableByBillNo(bill.getBillNo());
		itemOrderService.updatePaymentStatus(bill.getBillNo());
		billService.payForBill(bill.getBillNo());
		tableService.changeTableStatusToPaid(tables);
		return bill.getBillNo();
	}

	public TableService getTableService() {
		return tableService;
	}

	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}

	public ItemOrderService getItemOrderService() {
		return itemOrderService;
	}

	public void setItemOrderService(ItemOrderService itemOrderService) {
		this.itemOrderService = itemOrderService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}
}
