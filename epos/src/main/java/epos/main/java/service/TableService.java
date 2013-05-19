package epos.main.java.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.dao.TableDao;
import epos.main.java.vo.Bill;
import epos.main.java.vo.Item;
import epos.main.java.vo.ItemOrder;
import epos.main.java.vo.Table;

public class TableService {

	private TableDao tableDao;
	
	private ItemOrderService itemOrderService;
	
	private BillService billService;
	
	private ItemService itemService;
	
	/**
	 * 开台
	 * @param tableNo
	 * @param customerNum
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void activeTable(int tableNo, int customerNum) throws Exception{
		Table table = getTableByTableNo(tableNo);
		if(table.getTableStatus() != Table.STATUS_FREE){
			throw new Exception("该桌台还有顾客在使用,现在不能开台");
		}
		String billNo = BillService.createBillNo(tableNo);
		
		List<ItemOrder> deaultItemOrders = new ArrayList<ItemOrder>();
		int defaultItemId = 0;
		if(defaultItemId != 0){
//			创建默认茶位费消费记录
			Item item = itemService.getItemById(defaultItemId);
			ItemOrder itemOrder = new ItemOrder();
			itemOrder.setBillNo(billNo);
			itemOrder.setTableNo(tableNo);
			itemOrder.setCreateTime(new Date());
			itemOrder.setItemId(defaultItemId);
			customerNum = (customerNum == 0) ? table.getSeatingNum() : customerNum;
			itemOrder.setItemCount(customerNum);
//			价格 = 茶位费单价 * 桌台座位数
			itemOrder.setPrice(item.getPrice().multiply(new BigDecimal(table.getSeatingNum())));
//			设置菜单初始状态
			itemOrder.setPrintingStatus(ItemOrder.PRINTING_STATUS_NO);
			itemOrder.setProvidingStatus(ItemOrder.PROVIDING_STATUS_NO);
			itemOrder.setPaymentStatus(ItemOrder.PAYMENT_STATUS_NO);
			deaultItemOrders.add(itemOrder);
		}
		
//		默认开单，记录茶位费等
		Bill bill = new Bill();
		bill.setBillNo(billNo);
		bill.setBillStatus(Bill.BILL_STATUS_NEW);
		bill.setTableNo(tableNo);
		
//		创建默认的菜单项， 比如茶位费等
		itemOrderService.addItemOrders(deaultItemOrders);
//		创建订单
		billService.addBill(bill);
//		修改餐台状态
		changeTableStatus(tableNo, Table.STATUS_ACTIVED);
	}	
	
	/**
	 * 清台
	 * @param tableNo
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void nonActiveTable(int tableNo) throws Exception{
		Bill bill = billService.queryUnPaidBillByTableNo(tableNo);
		if(bill != null){
			throw new Exception("该餐台还没有结账,不能清台");
		}
		changeTableStatus(tableNo, Table.STATUS_FREE);
	}
	
	/**
	 * 转台，顾客要求更换桌台
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void changeTble(int fromTableNo, int toTableNo) throws Exception{
		Table table = getTableByTableNo(toTableNo);
		if(billService.queryUnPaidBillByTableNo(toTableNo) != null){
			throw new Exception(toTableNo + "号餐台还没有结账,不能转台");
		}
		if(table.getTableStatus() == Table.STATUS_ORDERED){
			throw new Exception(toTableNo + "号餐台已经被预定,不能转台");
		}
		if(table.getTableStatus() != Table.STATUS_FREE){
			throw new Exception(toTableNo + "号餐台还在使用中,不能转台");
		}
		
		Bill bill = billService.queryUnPaidBillByTableNo(fromTableNo);
		billService.updateTableNo(toTableNo, bill.getBillNo());
		itemOrderService.updateTableNo(toTableNo, bill.getBillNo());
		changeTableStatus(fromTableNo, Table.STATUS_FREE);
	}

	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void combineTable(List<Table> combinedTables, int newTableNo){
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addTables(List<Table> tables) throws Exception{
		for(Table table : tables){
			if(getTableByTableNo(table.getTableNo()) != null){
				throw new Exception("编号为:'" + table.getTableNo() +"'的餐台已经存在, 不能再添加同样编号的餐台");
			}
		}
		tableDao.addTables(tables);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateTables(List<Table> tables){
		tableDao.updateTables(tables);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void deleteTables(List<Integer> tableNOs) throws Exception{
		for(int tableNo : tableNOs){
			if(getTableByTableNo(tableNo) != null && getTableByTableNo(tableNo).getTableStatus() != Table.STATUS_FREE){
				throw new Exception("编号为:'" + tableNo +"'的餐台已经在使用, 不允许删除");
			}
		}
		tableDao.deleteTables(tableNOs);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<Table> listTables(){
		return tableDao.listTables();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<Table> listTablesByStatus(int tableStatus){
		Map<String,Object> param = new HashMap<String,Object>();
		if(tableStatus != -1){
			param.put("tableStatus", tableStatus);
		}
		return tableDao.listTablesByStatus(param);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public Table getTableByTableNo(int tableNo){
		return tableDao.getTableByTableNo(tableNo);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void changeTableStatus(int tableNo, int tableStatus){
		tableDao.changeTableStatus(tableNo, tableStatus);
	}
	
	public TableDao getTableDao() {
		return tableDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
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

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
}
