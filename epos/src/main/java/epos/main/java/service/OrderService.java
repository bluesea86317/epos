package epos.main.java.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import epos.main.java.vo.Bill;
import epos.main.java.vo.Item;
import epos.main.java.vo.ItemOrder;
import epos.main.java.vo.Table;

public class OrderService {

	private ItemOrderService itemOrderService;
	
	private BillService billService;
	
	private ItemService itemService;
	
	private TableService tableService;	
	
	/**
	 * 点菜
	 * @param appendItemOrders
	 * @param tableNo
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void orderItem(List<ItemOrder> itemOrders, int tableNo) throws Exception{
		Table table = getTableService().getTableByTableNo(tableNo);
		if(table == null){
			throw new Exception("编号为"+tableNo+"的桌台不存在");
		}
		if(table.getTableStatus() != Table.STATUS_ACTIVED){
			throw new Exception("该桌台还未开台, 不能点菜");
		}
		Bill bill = getBillService().queryUnPaidBillByTableNo(tableNo);
		if(bill.getBillStatus() == Bill.BILL_STATUS_PAID){
			throw new Exception("该桌台已经结账,不能点菜");
		}
		List<ItemOrder> addItemOrders = new ArrayList<ItemOrder>();
		List<ItemOrder> updateItemOrders = new ArrayList<ItemOrder>();
		List<ItemOrder> addItemOrdersForPrint = new ArrayList<ItemOrder>();
		for(ItemOrder appendItemOrder : itemOrders){
			Item item = getItemService().getItemById(appendItemOrder.getItemId());
			if(item == null){
				throw new Exception("编号为"+appendItemOrder.getItemId()+"的菜品不存在，无法点菜");
			}
			if(item.getItemReserveStatus() == Item.ITEM_RESERVE_STATUS_LACK){
				throw new Exception("对不起，菜品'"+ item.getItemName() +"'已估清，请选择其他菜品");
			}
			if(appendItemOrder.getItemCount() < 1){
				throw new Exception(item.getItemName() + "这道菜不能小于1份");
			}
			BigDecimal price = item.getPrice().multiply(new BigDecimal(appendItemOrder.getItemCount()));
			appendItemOrder.setPrice(price);
			appendItemOrder.setBillNo(bill.getBillNo());
			appendItemOrder.setPrintingStatus(ItemOrder.PRINTING_STATUS_NO);
			appendItemOrder.setProvidingStatus(ItemOrder.PROVIDING_STATUS_NO);
			appendItemOrder.setPaymentStatus(ItemOrder.PAYMENT_STATUS_NO);
			appendItemOrder.setOrderType(ItemOrder.ORDER_TYPE_BOOK);
			addItemOrdersForPrint.add(appendItemOrder);
			ItemOrder itemOrder = getItemOrderService().queryItemOrderByItemIdBillNoTableNo(appendItemOrder.getItemId(), bill.getBillNo(), tableNo,appendItemOrder.getFlavorId());
//			如果当前所点菜品该桌还未点过, 就是新点的菜品, 否则就是加菜
			if(itemOrder == null){
				bill.setTotalPrice(bill.getTotalPrice().add(price));				
				addItemOrders.add(appendItemOrder);
			}else{
				itemOrder.setPrice(itemOrder.getPrice().add(price));
				itemOrder.setItemCount(itemOrder.getItemCount() + appendItemOrder.getItemCount());
				bill.setTotalPrice(bill.getTotalPrice().add(price));
				updateItemOrders.add(itemOrder);				
			}
		}
		getItemOrderService().updateItemOrderPriceAndCount(updateItemOrders);
		getItemOrderService().addItemOrders(addItemOrders);
		getItemOrderService().addItemOrderForPrint(addItemOrdersForPrint);
		getBillService().updateTotalPrice(bill.getTotalPrice(), bill.getBillNo());
	}

	/**
	 * 退菜
	 * @param cancelItemOrders
	 * @param tableNo
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackForClassName={"java.lang.Exception"})
	public void cancelItem(List<ItemOrder> cancelItemOrders, int tableNo) throws Exception{
		Table table = getTableService().getTableByTableNo(tableNo);
		if(table == null){
			throw new Exception("编号为"+tableNo+"的桌台不存在");
		}
		if(table.getTableStatus() != Table.STATUS_ACTIVED){
			throw new Exception("该桌台还未开台, 不能退菜");
		}
		Bill bill = getBillService().queryUnPaidBillByTableNo(tableNo);
		if(bill == null){
			throw new Exception("该桌台还未点餐, 或者已经结账");
		}
		List<ItemOrder> removeItemOrders = new ArrayList<ItemOrder>();
		List<ItemOrder> updateItemOrders = new ArrayList<ItemOrder>();
		List<ItemOrder> cancelItemOrdersForPrint = new ArrayList<ItemOrder>();
		for(ItemOrder cancelItemOrder : cancelItemOrders){
			Item item = getItemService().getItemById(cancelItemOrder.getItemId());
			if(item == null){
				throw new Exception("编号为"+cancelItemOrder.getItemId()+"的菜品不存在，无法退菜");
			}
			ItemOrder itemOrder = getItemOrderService().queryItemOrderByItemIdBillNoTableNo(cancelItemOrder.getItemId(), bill.getBillNo(), tableNo, cancelItemOrder.getFlavorId());
			if(itemOrder == null){
				throw new Exception(tableNo + "号桌台没有点'" + item.getItemName() + "'这道菜");
			}
//			如果要退的菜品份数比已点的菜品份数要多, 就提示错误, 不能退
			if(cancelItemOrder.getItemCount() > itemOrder.getItemCount()){
				throw new Exception(tableNo + "号桌台的'" + item.getItemName() + "'只点了" + itemOrder.getItemCount() + "份, 不能退订" + cancelItemOrder.getItemCount() +"份");
			}else if(cancelItemOrder.getItemCount() == itemOrder.getItemCount()){
//				如果要退的菜品刚好等于已点的菜品份数, 就删除当前所点的该菜品
				removeItemOrders.add(itemOrder);
			}else{
//				如果要退的菜品份数小于已点的菜品份数, 就更新已点菜品份数
				int itemCount = itemOrder.getItemCount() - cancelItemOrder.getItemCount();
				itemOrder.setItemCount(itemCount);
				itemOrder.setPrice(item.getPrice().multiply(new BigDecimal(itemCount)));
				updateItemOrders.add(itemOrder);
			}
//			同时对订单总价格进行调整, 总价格-所退菜品的价格*所退的菜品份数
			bill.setTotalPrice(bill.getTotalPrice().subtract(item.getPrice().multiply(new BigDecimal(cancelItemOrder.getItemCount()))));
//			将要退的菜品也记录在打印队列
			itemOrder.setOrderType(ItemOrder.ORDER_TYPE_CANCEL);
			itemOrder.setPrintingStatus(ItemOrder.PRINTING_STATUS_NO);
			cancelItemOrdersForPrint.add(itemOrder);
		}
//		删除已点菜, 或者就该已点的多份菜
		getItemOrderService().deleteItemOrders(removeItemOrders);
		getItemOrderService().updateItemOrderPriceAndCount(updateItemOrders);
		getItemOrderService().addItemOrderForPrint(cancelItemOrdersForPrint);
//		修改订单总价格
		getBillService().updateTotalPrice(bill.getTotalPrice(), bill.getBillNo());
	}
	
	/**
	 * 上菜
	 * @param tableNo
	 * @param itemId
	 * @throws Exception 
	 */
	public void provideItem(int tableNo, int itemId, int flavorId) throws Exception{
		Bill bill = billService.queryUnPaidBillByTableNo(tableNo);
		if(bill == null){
			throw new Exception("该桌台还未点餐, 或者已经结账");
		}
		ItemOrder itemOrder = itemOrderService.queryItemOrderByItemIdBillNoTableNo(itemId, bill.getBillNo(), tableNo, flavorId);
		if(itemOrder == null){
			throw new Exception("该桌台并没有点此菜品");
		}
		itemOrderService.updateProvidingStatus(itemOrder.getItemOrderId());
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

	public TableService getTableService() {
		return tableService;
	}

	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	} 
	
}
