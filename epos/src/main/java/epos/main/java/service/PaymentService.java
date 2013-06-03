package epos.main.java.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.core.Env;
import epos.main.java.vo.Bill;
import epos.main.java.vo.ItemOrder;
import epos.main.java.vo.ItemOrderVo;
import epos.main.java.vo.Table;

public class PaymentService {

	private TableService tableService;
	
	private ItemOrderService itemOrderService;
	
	private BillService billService;
	
	private EposConfigService eposConfigService = Env.getBean("eposConfigService");
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public String paymentForBill(int tableNo, BigDecimal discountRate) throws Exception{
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

		if(discountRate.compareTo(BigDecimal.ZERO) == -1 || discountRate.compareTo(BigDecimal.ONE) == 1){
			throw new Exception("折扣率错误");
		}
		List<Table> tables = tableService.getTableByBillNo(bill.getBillNo());
		tableService.changeTableStatusToPaid(tables);
		
		List<ItemOrderVo> itemOrders = itemOrderService.queryItemOrderVoByBillNo(bill.getBillNo());
		int excludeItemId = 0;
		String defaultItemIds = eposConfigService.getProperty("defaultItemIds");
		if(StringUtils.isNotBlank(defaultItemIds)){
			String[] itemIds = defaultItemIds.split(",");
			excludeItemId = Integer.parseInt(itemIds[0]);
		}
		
//		判断当前桌台是否有下单点菜, 如果没有, 就将生成的流水单删掉
		boolean flag = true;
		if(itemOrders != null && itemOrders.size() > 0){
			for(ItemOrder itemOrder: itemOrders){
				if(itemOrder.getItemId() != excludeItemId){
					flag = false;
					break;
				}
			}
		}
//		如果顾客什么都没点(甚至连茶位费都没有), 或者只有茶位费, 就认为顾客没有下单点菜, 这时候就将自动生成的流水单删掉
		if(itemOrders == null || flag){
			itemOrderService.deleteItemOrderByBillNo(bill.getBillNo());
			billService.deleteBillByBillNo(bill.getBillNo());
			return null;
		}
		itemOrderService.updatePaymentStatus(bill.getBillNo());
		billService.payForBill(bill.getBillNo());
		billService.changeDiscountPrice(bill.getBillNo(), bill.getTotalPrice().multiply(discountRate));
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
