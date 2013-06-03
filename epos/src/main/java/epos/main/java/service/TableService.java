package epos.main.java.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.core.Env;
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
	
//	private String defaultItemIds;
	
	private EposConfigService eposConfigService = Env.getBean("eposConfigService");
	/**
	 * 开台
	 * @param tableNo
	 * @param customerNum
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void activeTable(int tableNo, int customerNum) throws Exception{
		Table table = getTableByTableNo(tableNo);
		if(table == null){
			throw new Exception("编号为"+tableNo+"的桌台不存在");
		}
		if(table.getTableStatus() != Table.STATUS_FREE){
			throw new Exception("该桌台还有顾客在使用,现在不能开台");
		}
		String billNo = BillService.createBillNo(tableNo);
		
		List<ItemOrder> deaultItemOrders = new ArrayList<ItemOrder>();
		BigDecimal totalPrice = BigDecimal.ZERO;		
		String defaultItemIds = eposConfigService.getProperty("defaultItemIds");
		if(StringUtils.isNotBlank(defaultItemIds)){
			String[] itemIds = defaultItemIds.split(",");
			for(String itemId : itemIds){
			int defaultItemId = Integer.parseInt(itemId);
				if(defaultItemId != 0){
		//			创建默认茶位费消费记录
					Item item = itemService.getItemById(defaultItemId);
					if(item == null){
						throw new Exception("编号为"+defaultItemId+"的菜品不存在，无法开台");
					}
					ItemOrder itemOrder = new ItemOrder();
					itemOrder.setBillNo(billNo);
					itemOrder.setTableNo(tableNo);
//					itemOrder.setCreateTime(new Date());
					itemOrder.setItemId(defaultItemId);
					customerNum = (customerNum == 0) ? table.getSeatingNum() : customerNum;
					itemOrder.setItemCount(customerNum);
		//			价格 = 茶位费单价 * 桌台座位数
					BigDecimal price = item.getPrice().multiply(new BigDecimal(customerNum));
					itemOrder.setPrice(price);
					totalPrice = totalPrice.add(price);
		//			设置菜单初始状态
					itemOrder.setPrintingStatus(ItemOrder.PRINTING_STATUS_YES);
					itemOrder.setProvidingStatus(ItemOrder.PROVIDING_STATUS_YES);
					itemOrder.setPaymentStatus(ItemOrder.PAYMENT_STATUS_NO);
					deaultItemOrders.add(itemOrder);
				}
			}
		}
//		默认开单，记录茶位费等
		Bill bill = new Bill();
		bill.setBillNo(billNo);
		bill.setBillStatus(Bill.BILL_STATUS_NEW);
		bill.setTableNo(tableNo);
		bill.setTotalPrice(totalPrice);
		bill.setDiscountPrice(totalPrice);
//		创建默认的菜单项， 比如茶位费等
		itemOrderService.addItemOrders(deaultItemOrders);
//		创建订单
		billService.addBill(bill);
//		修改桌台状态
		changeTableStatus(tableNo, Table.STATUS_ACTIVED);
	}	
	
	/**
	 * 清台
	 * @param tableNo
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void freeTable(int tableNo) throws Exception{
		Table table = getTableByTableNo(tableNo);
		if(table == null){
			throw new Exception(tableNo + "号桌台不存在, 不能清台");
		}
		Bill bill = billService.queryUnPaidBillByTableNo(tableNo);
		if(bill != null || table.getTableStatus() == Table.STATUS_ACTIVED){
			throw new Exception(tableNo + "号桌台还没有结账,不能清台");
		}
		changeTableStatus(tableNo, Table.STATUS_FREE);
	}
	
	/**
	 * 转台，顾客要求更换桌台
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void changeTable(int fromTableNo, int toTableNo) throws Exception{
		Table fromTable = getTableByTableNo(fromTableNo);
		Table toTable = getTableByTableNo(toTableNo);
		if(fromTable == null){
			throw new Exception(fromTableNo + "号桌台不存在,不能转台");
		}
		if(toTable == null){
			throw new Exception(toTableNo + "号桌台不存在,不能转台");
		}
		if(fromTable.getTableStatus() != Table.STATUS_ACTIVED || billService.queryUnPaidBillByTableNo(fromTableNo) == null){
			throw new Exception(fromTableNo + "号桌台未开台, 不能转台");
		}
		
		if(toTable.getTableStatus() != Table.STATUS_FREE || billService.queryUnPaidBillByTableNo(toTableNo) != null){
			throw new Exception(toTableNo + "号桌台正在使用中,不能转台");
		}
		if(toTable.getTableStatus() == Table.STATUS_ORDERED){
			throw new Exception(toTableNo + "号桌台已经被预定,不能转台");
		}
				
		Bill bill = billService.queryUnPaidBillByTableNo(fromTableNo);
		billService.updateTableNo(toTableNo, bill.getBillNo(), fromTableNo);
		itemOrderService.updateTableNo(toTableNo, bill.getBillNo(), fromTableNo);
		changeTableStatus(fromTableNo, Table.STATUS_FREE);
		changeTableStatus(toTableNo, Table.STATUS_ACTIVED);
	}

	/**
	 * 并台
	 * @param combinedTableNos
	 * @param newTableNo
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void combineTable(List<Integer> combinedTableNos, int newTableNo) throws Exception{
		Table newTable = getTableByTableNo(newTableNo);
		if(newTable == null){
			throw new Exception("编号为"+newTableNo+"的桌台不存在,不能并台");
		}
		if(newTable.getTableStatus() != Table.STATUS_ACTIVED){
			throw new Exception(newTableNo + "号桌台还未开台, 不能并台");
		}
		
		Bill bill = billService.queryUnPaidBillByTableNo(newTableNo);
		List<String> combineBillNos = new ArrayList<String>();
		for(int combinedTableNo : combinedTableNos){
			Table combinedTable = getTableByTableNo(combinedTableNo);
			if(combinedTable == null){
				throw new Exception(combinedTableNo + "号桌台不存在, 不能并台");
			}
			if(combinedTable.getTableStatus() != Table.STATUS_ACTIVED){
				throw new Exception(combinedTableNo + "号桌台还未开台, 不能并台");
			}
			
//			获取要并台的订单
			Bill combineBill = billService.queryUnPaidBillByTableNo(combinedTableNo);
			if(combinedTableNo == newTableNo || bill.getBillNo().equals(combineBill.getBillNo())){
				continue;
			}
			
			combineBillNos.add(combineBill.getBillNo());
			
			List<ItemOrder> itemOrders = itemOrderService.queryItemOrderByBillNoTableNo(combineBill.getBillNo(), combinedTableNo);
			
			for(ItemOrder itemOrder : itemOrders){
				bill.setTotalPrice(bill.getTotalPrice().add(itemOrder.getPrice()));
			}
//			修改被合并桌台的订单号
			itemOrderService.updateBillNo(combinedTableNo,combineBill.getBillNo(),bill.getBillNo());
		}
//		删除被合并桌台的原始订单
		billService.deleteBillByBillNos(combineBillNos);
//		修改并台后的订单价格
		billService.updateTotalPrice(bill.getTotalPrice(), bill.getBillNo());
	}
	
	
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addTables(List<Table> tables) throws Exception{
		for(Table table : tables){
			if(getTableByTableNo(table.getTableNo()) != null){
				throw new Exception("编号为'" + table.getTableNo() +"'的桌台已经存在, 不能再添加同样编号的桌台");
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
				throw new Exception("编号为'" + tableNo +"'的桌台已经在使用, 不允许删除");
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
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<Table> getTableByBillNo(String billNo){
		return tableDao.getTableByBillNo(billNo);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void changeTableStatusToPaid(List<Table> tables){
		tableDao.changeTableStatusToPaid(tables);
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

//	public String getDefaultItemIds() {
//		return defaultItemIds;
//	}
//
//	public void setDefaultItemIds(String defaultItemIds) {
//		this.defaultItemIds = defaultItemIds;
//	}

	public EposConfigService getEposConfigService() {
		return eposConfigService;
	}

	public void setEposConfigService(EposConfigService eposConfigService) {
		this.eposConfigService = eposConfigService;
	}
}
